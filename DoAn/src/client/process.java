package client;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

public class process extends JCustomWindow {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Create the frame.
	 */
	public process() {
		super("Liệt kê tiến trình");

		setBounds(100, 100, 513, 428);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		Button btnKill = new Button("Dừng");
		btnKill.setBounds(38, 36, 85, 25);
		btnKill.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					buttonKill_Click(e);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		contentPane.setLayout(null);

		btnKill.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPane.add(btnKill);

		Button btnXem = new Button("Xem");
		btnXem.setBounds(159, 36, 77, 25);
		btnXem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					buttonXem_Click(e);
				} catch (IOException | InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnXem.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPane.add(btnXem);

		Button btnStart = new Button("Chạy");
		btnStart.setBounds(272, 36, 77, 25);
		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					buttonStart_Click(e);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnStart.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPane.add(btnStart);

		Button btnNewButton = new Button("Xóa");
		btnNewButton.setBounds(385, 36, 77, 25);
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 14));
		contentPane.add(btnNewButton);

		this.table = new JTable();
		this.table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "T\u00EAn ti\u1EBFn tr\u00ECnh", "ID" }));
		this.table.setRowHeight(25);
		this.table.setFont(new Font("Tahoma", Font.PLAIN, 16));

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(42, 102, 420, 244);
		contentPane.add(scrollPane);
	}

	private void buttonKill_Click(ActionEvent e) throws IOException {
		Program.SendMessage("KILL");
		Program.Instance(Kill.class);
	}

	private void buttonXem_Click(ActionEvent e) throws IOException, InterruptedException {
		Program.SendMessage("XEM");

		String name = null;
		String ID = null;

		while (true) {
			name = Program.inFromServer.readUTF();
			if (name.equals("Done"))
				break;
			ID = Program.inFromServer.readUTF();
			String list[] = { name, ID };
			System.out.println(list[0] + " " + list[1]);
			DefaultTableModel model = (DefaultTableModel) this.table.getModel();
			model.addRow(list);
		}

	}

	private void buttonStart_Click(ActionEvent e) throws IOException {
		Program.SendMessage("START");
		Program.Instance(Start.class);

	}

}
