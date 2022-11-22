package client;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Client extends JFrame {

	public static JPanel contentPane;
	private JTextField txtIP;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				Program.Instance(Client.class);
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Client() {
		super("Client");
		setBounds(300, 300, 750, 502);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		txtIP = new JHintedTextField("Nhập IP");
		txtIP.setFont(new Font("Tahoma", Font.PLAIN, 21));
		txtIP.setBounds(31, 34, 498, 55);
		contentPane.add(txtIP);
		txtIP.setColumns(10);

		Button btnConnect = new Button("Kết nối");
		btnConnect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					butConnect_Click(e);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		btnConnect.setFont(new Font("Tahoma", Font.PLAIN, 21));
		btnConnect.setBounds(539, 34, 190, 55);
		getContentPane().add(btnConnect);

		var btnApp = new JClientButton("Ứng dụng đang chạy", "APPLICATION", () -> Program.Instance(listApp.class));

		btnApp.setFont(new Font("Tahoma", Font.PLAIN, 21));
		btnApp.setBounds(269, 123, 298, 96);
		getContentPane().add(btnApp);

		var btnPic = new JClientButton("Chụp màn hình", "TAKEPIC", () -> Program.Instance(Pic.class));

		btnPic.setFont(new Font("Tahoma", Font.PLAIN, 21));
		btnPic.setBounds(269, 233, 179, 88);
		getContentPane().add(btnPic);

		var btnShutDown = new JClientButton("Tắt máy", "SHUTDOWN", () -> Program.client = null);

		btnShutDown.setFont(new Font("Tahoma", Font.PLAIN, 21));
		btnShutDown.setBounds(450, 233, 117, 88);
		getContentPane().add(btnShutDown);

		var btnExit = new Button("Thoát");
		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					butExit_Click(e);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		btnExit.setFont(new Font("Tahoma", Font.PLAIN, 21));
		btnExit.setBounds(577, 331, 149, 96);
		getContentPane().add(btnExit);

		var btnKey = new JClientButton("Keystroke", "KEYLOG",()->Program.Instance(keylog.class));
		btnKey.setText("Bắt phím");

		btnKey.setFont(new Font("Tahoma", Font.PLAIN, 21));
		btnKey.setBounds(577, 123, 144, 198);
		getContentPane().add(btnKey);

		var btnProcess = new JClientButton("<html>Tiến trình<br>đang chạy</html>", "PROCESS", () -> Program.Instance(process.class));

		btnProcess.setFont(new Font("Tahoma", Font.PLAIN, 21));
		btnProcess.setBounds(31, 123, 230, 198);
		contentPane.add(btnProcess);

	}

	private void butConnect_Click(ActionEvent e) throws IOException {
		try {
			Program.client = new Socket(txtIP.getText().toString(), 4424);
			JOptionPane.showMessageDialog(this, "Kết nối đến server thành công");
			Program.inFromServer = new DataInputStream(new BufferedInputStream(Program.client.getInputStream()));
			Program.outToServer = new DataOutputStream(Program.client.getOutputStream());

		} catch (Exception ex) {
			JOptionPane.showMessageDialog(this, "Lỗi kết nối đến Server");
			Program.client = null;
		}
	}

	private void butExit_Click(ActionEvent e) throws IOException {
		if (Program.client != null) {
			Program.SendMessage("QUIT");
		}
		System.exit(0);

	}
}

class JClientButton extends Button {
	private final String serverMessage;
	private final Runnable postSend;

	public JClientButton(String text, String serverMessage, Runnable postSend) {
		super(text);
		this.serverMessage = serverMessage;
		this.postSend = postSend;
		addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					button_Click(e);
				} catch (IOException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}

			}
		});
	}

	private void button_Click(ActionEvent e) throws IOException {
		if (Program.client == null) {
			JOptionPane.showMessageDialog(Client.contentPane, "Chưa kết nối đến server");
			return;
		}

		Program.SendMessage(serverMessage);

		postSend.run();
	}
}
class Button extends JButton {
	public Button(String text)
	{
		super(text);
		setFocusPainted(false);
	}
}