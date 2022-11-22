package client;

import java.awt.EventQueue;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import java.awt.AWTException;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFileChooser;

public class Pic extends JFrame {

	private JPanel contentPane;
	private BufferedImage img1;
	private JLabel lbLabel;
	


	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					Pic frame = new Pic();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public Pic() {
		setTitle("Pic");
		setBounds(100, 100, 557, 379);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JButton btnNewButton = new JButton("Chụp màn hình");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					butTake_Click(e);
				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		btnNewButton.setBounds(375, 32, 132, 81);
		contentPane.add(btnNewButton);
		
		JButton btnSave = new JButton("Save");
		
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					button1_Click(e);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btnSave.setBounds(375, 167, 132, 62);
		contentPane.add(btnSave);
		
		this.img1=new BufferedImage(240, 240, BufferedImage.TYPE_INT_ARGB);
		
		this.lbLabel = new JLabel("");
		this.lbLabel.setBounds(10, 11, 366, 320);
		
        lbLabel.setHorizontalTextPosition(JLabel.CENTER);
        lbLabel.setVerticalTextPosition(SwingConstants.TOP);
        lbLabel.setPreferredSize(new Dimension(50, 75));
		contentPane.add(lbLabel);
		
						
	}
	public void lam() throws IOException, AWTException
    {
     
		 try {
             String s = "TAKE";
             Program.outToServer.writeUTF(s);
             Program.outToServer.flush();
             int len = Program.inFromServer.readInt();
             System.out.println(len);
             if(len > 0){
                 byte[] receiveByte = new byte[len];
                 Program.inFromServer.read(receiveByte);
                 ByteArrayInputStream streamByte = new ByteArrayInputStream(receiveByte);
                 BufferedImage receivedImage = ImageIO.read(streamByte);
                 this.img1=receivedImage;
              
                 Image dimg = this.img1.getScaledInstance(lbLabel.getWidth(), lbLabel.getHeight(),Image.SCALE_SMOOTH);
                 ImageIcon finalImage = new ImageIcon(dimg);
                 lbLabel.setIcon(finalImage);
                         
             }
         }
         catch (IOException ex){
             ex.printStackTrace();
         }
	}
	
	 private void butTake_Click(ActionEvent e) throws IOException, AWTException
     {
		 lam();
     }

    private void button1_Click(ActionEvent e) throws IOException
    {
    	JFrame parentFrame = new JFrame();
    	 
    	JFileChooser fileChooser = new JFileChooser();	 
    	int userSelection = fileChooser.showSaveDialog(parentFrame);
    	 
    	if (userSelection == JFileChooser.APPROVE_OPTION) {
    		File selectedFile = fileChooser.getSelectedFile();
        	String test = selectedFile.getAbsolutePath();
        	try {
                ImageIO.write(this.img1, "png", new File(test));
            }catch (IOException ex) {
                System.out.println("Failed to save image!");
            }
        }else {
        	System.out.println("No file choosen!");
        	}
    }
    	
    	
    
    @SuppressWarnings("unused")
	private void pic_closing(ActionEvent e) throws IOException
    {
        String s = "QUIT";
        Program.outToServer.writeUTF(s);
        Program.outToServer.flush();
    }
}
