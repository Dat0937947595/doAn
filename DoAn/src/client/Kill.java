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
import javax.swing.SwingConstants;

public class Kill extends JCustomWindow {

	private JPanel contentPane;
	private JTextField txtID;

	/**
	 * Create the frame.
	 */
	public Kill() {
		super("Ngắt");

		setTitle("Dừng tiến trình");
		setBounds(100, 100, 450, 197);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		Button btnKill = new Button("Dừng");
		btnKill.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					butKill_Click(e);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnKill.setBounds(318, 63, 95, 31);
		btnKill.setFont(new Font("Tahoma", Font.BOLD, 18));
		contentPane.add(btnKill);

		JLabel lbId = new JLabel("Nhập ID:");
		lbId.setHorizontalAlignment(SwingConstants.CENTER);
		lbId.setFont(new Font("Tahoma", Font.BOLD, 18));
		lbId.setBounds(10, 64, 95, 30);
		contentPane.add(lbId);

		txtID = new JTextField();
		txtID.setBounds(106, 63, 202, 31);
		contentPane.add(txtID);
		txtID.setColumns(10);
	}

	private void butKill_Click(ActionEvent e) throws IOException {
		//Program.outToServer.writeUTF("KILLID");
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
