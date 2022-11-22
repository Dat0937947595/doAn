package client;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class keylog extends JCustomWindow {

	private JPanel contentPane;
	private JTextArea textArea;


	/**
	 * Create the frame.
	 */
	public keylog() {
		super("Keylogger");

		setBounds(100, 100, 555, 386);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		Button btnXoa = new Button("Xóa");
		btnXoa.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
			}
		});
		btnXoa.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnXoa.setBounds(412, 22, 100, 32);
		contentPane.add(btnXoa);

		Button btnHook = new Button("Hook");
		btnHook.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Program.SendMessage("HOOK");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnHook.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnHook.setBounds(28, 22, 100, 32);
		contentPane.add(btnHook);

		Button btnUnhook = new Button("Unhook");
		btnUnhook.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Program.SendMessage("UNHOOK");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnUnhook.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnUnhook.setBounds(156, 22, 100, 32);
		contentPane.add(btnUnhook);

		Button btnInPhim = new Button("In Phím");
		btnInPhim.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Program.SendMessage("PRINT");
					
					int num = Program.inFromServer.readInt();
					for (int i = 0; i < num; i++)
						textArea.append(Program.inFromServer.readUTF());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});
		btnInPhim.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnInPhim.setBounds(284, 22, 100, 32);
		contentPane.add(btnInPhim);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(28, 81, 484, 258);
		contentPane.add(scrollPane);

		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setEditable(false);
	}

}
