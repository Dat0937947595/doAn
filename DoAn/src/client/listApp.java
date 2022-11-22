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

public class listApp extends JCustomWindow {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Create the frame.
	 */
	public listApp() {
		super("Liệt kê ứng dụng");
		setBounds(100, 100, 661, 443);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		Button btnKill = new Button("Dừng");
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
		btnKill.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnKill.setBounds(58, 47, 89, 32);
		contentPane.add(btnKill);

		Button btnXem = new Button("Xem");
		btnXem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					buttonXem_Click(e);
				} catch (InterruptedException | IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnXem.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnXem.setBounds(205, 47, 89, 32);
		contentPane.add(btnXem);

		Button btnStart = new Button("Chạy");
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
		btnStart.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnStart.setBounds(352, 47, 89, 32);
		contentPane.add(btnStart);

		Button btnXoa = new Button("Xóa");
		btnXoa.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnXoa.setBounds(499, 47, 89, 32);
		contentPane.add(btnXoa);

		this.table = new JTable();

		this.table.setModel(
				new DefaultTableModel(new Object[][] {}, new String[] { "T\u00EAn \u1EE9ng d\u1EE5ng", "ID" }));
		this.table.setRowHeight(25);

		this.table.setFont(new Font("Tahoma", Font.PLAIN, 16));

		this.table.getTableHeader().setFont(this.table.getFont());

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(42, 102, 571, 244);
		contentPane.add(scrollPane);
	}

	private void buttonKill_Click(ActionEvent e) throws IOException {
		String temp = "KILL";
		Program.SendMessage(temp);

		Kill viewkill = new Kill();
		viewkill.setVisible(true);
	}

	private void buttonXem_Click(ActionEvent e) throws IOException, InterruptedException {

		String temp = "XEM";
		Program.SendMessage(temp);

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
		String temp = "START";
		Program.SendMessage(temp);

		Start viewstart = new Start();
		viewstart.setVisible(true);

	}
}
