package client;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Start extends JCustomWindow {

	private JPanel contentPane;
	private JTextField txtID;
	/**
	 * Create the frame.
	 */
	public Start() {
		super("Chạy ứng dụng");

		setBounds(100, 100, 456, 207);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		txtID = new JTextField();
		txtID.setBounds(122, 62, 202, 24);
		contentPane.add(txtID);
		txtID.setColumns(10);

		Button btnStart = new Button("Start");
		btnStart.setText("Chạy");
		btnStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					butStart_Click(e);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnStart.setFont(new Font("Tahoma", Font.BOLD, 14));
		btnStart.setBounds(334, 62, 76, 24);
		contentPane.add(btnStart);

		JLabel lb = new JLabel("Nhập tên");
		lb.setFont(new Font("Tahoma", Font.BOLD, 14));
		lb.setBounds(36, 62, 76, 24);
		contentPane.add(lb);
	}

	private void butStart_Click(ActionEvent e) throws IOException {
		 //Program.outToServer.writeUTF("STARTID");
         //Program.outToServer.flush();
   
         Program.outToServer.writeUTF(txtID.getText()); 
         Program.outToServer.flush();
         String s= null;
         try {
 			s = Program.inFromServer.readUTF();
 		} catch (IOException e1) {
 			e1.printStackTrace();
 		}
         JOptionPane.showMessageDialog(null, s);
	}

}
