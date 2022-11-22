package server;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.*;

import java.awt.AWTException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.Color;
import java.util.logging.*;

public class Server extends JFrame {

	private JPanel contentPane;
	public String s;
	private Thread th;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Server frame = new Server();
					frame.setVisible(true);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Server() throws NativeHookException {
		setTitle("Server");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 402, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(192, 192, 192));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnNewButton = new JButton("Mở server");

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					button1_Click(e);
				}
				catch (IOException | AWTException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		});

		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 31));
		btnNewButton.setBounds(81, 70, 227, 94);
		contentPane.add(btnNewButton);
	}

	public void shutdown() throws IOException {
		Runtime.getRuntime().exec("shutdown.exe -s -t 30");
		System.exit(0);
	}

	public void takepic() throws IOException, AWTException {
		String ss = "";

		while (true) {
			try {
				ss = App.inFromServer.readUTF();
			}
			catch (Exception ex) {
				ss = "QUIT";
			}
			switch (ss) {
			case "TAKE":
				Rectangle capture = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
				BufferedImage img = new Robot().createScreenCapture(capture);
				ByteArrayOutputStream imgByte = new ByteArrayOutputStream();
				ImageIO.write(img, "png", imgByte);

				System.out.println("Screenshot saved");
				byte[] sendByte = imgByte.toByteArray();
				App.outToServer.writeInt(sendByte.length);
				App.outToServer.write(sendByte);
				App.outToServer.flush();
				break;
			case "QUIT": {
				return;
			}
			}
		}
	}

	public void application() throws IOException, InterruptedException, Exception {
		String ss = "";

		while (true) {
			try {
				ss = App.inFromServer.readUTF();
			}
			catch (Exception ex) {
				ss = "QUIT";
			}
			switch (ss) {
			case "XEM": {
				Process process = new ProcessBuilder("powershell", "\"gps| ? {$_.mainwindowtitle.length -ne 0} | Format-Table -HideTableHeaders  name, ID").start();
				Scanner sc = new Scanner(process.getInputStream());
				if (sc.hasNextLine())
					sc.nextLine();
				while (sc.hasNextLine()) {
					String info = sc.nextLine();
					if (info.length() == 0)
						continue;
					int pLastName = info.indexOf(" ");
					int pFirstID = info.lastIndexOf(" ") + 1;
					String name = info.substring(0, pLastName);
					String ID = info.substring(pFirstID);

					try {
						App.outToServer.writeUTF(name);
						App.outToServer.flush();
					}
					catch (IOException e) {
						e.printStackTrace();
					}

					try {
						App.outToServer.writeUTF(ID);
						App.outToServer.flush();
					}
					catch (IOException e1) {
						e1.printStackTrace();
					}

					System.out.print(name + " ");
					System.out.println(ID);
				}
				process.waitFor();
				App.outToServer.writeUTF("Done");
				App.outToServer.flush();
				sc.close();
				break;
			}

			case "KILL": {
				while (true) {
					String u = null;
					try {
						u = App.inFromServer.readUTF();
					}
					catch (Exception ex) {
						u = "QUIT";
					}
					if (u.equals("QUIT")) {
						break;
					}
					try {
						long pid = Integer.parseInt(u.toString());
						ProcessHandle.of(pid).ifPresent(ProcessHandle::destroyForcibly);
						App.outToServer.writeUTF("Đã diệt chương trình");
						App.outToServer.flush();
					}
					catch (Exception e) {
						App.outToServer.writeUTF("[Error] Không tìm thấy chương trình");
						App.outToServer.flush();
					}
				}
			}
			case "START": {
				while (true) {
					try {
						String name = App.inFromServer.readUTF();

						if (name.equals("QUIT")) {
							break;
						}
						try {
							new ProcessBuilder(name + ".exe").start();
							App.outToServer.writeUTF("Đã bật chương trình. ");
							App.outToServer.flush();
						}
						catch (Exception e) {
							App.outToServer.writeUTF("Chưa bật chương trình. ");
							App.outToServer.flush();
						}
					}
					catch (Exception e) {
					}

				}
			}
			case "QUIT":
				break;

			}

		}
	}

	public void process() throws IOException, InterruptedException {
		String ss = "";
		while (true) {
			try {
				ss = App.inFromServer.readUTF();
			}
			catch (Exception ex) {
				ss = "QUIT";
			}
			switch (ss) {
			case "XEM": {
				Process process = new ProcessBuilder("powershell", "\"gps| Format-Table -HideTableHeaders  name, ID").start();
				Scanner sc = new Scanner(process.getInputStream());
				if (sc.hasNextLine())
					sc.nextLine();
				while (sc.hasNextLine()) {
					String info = sc.nextLine();
					if (info.length() == 0)
						continue;
					int pLastName = info.indexOf(" ");
					int pFirstID = info.lastIndexOf(" ") + 1;
					String name = info.substring(0, pLastName);
					String ID = info.substring(pFirstID);

					try {
						App.outToServer.writeUTF(name);
						App.outToServer.flush();
					}
					catch (IOException e) {
						e.printStackTrace();
					}

					try {
						App.outToServer.writeUTF(ID);
						App.outToServer.flush();
					}
					catch (IOException e1) {
						e1.printStackTrace();
					}

					System.out.print(name + " ");
					System.out.println(ID);
				}
				process.waitFor();
				App.outToServer.writeUTF("Done");
				App.outToServer.flush();

			}

			case "KILL": {
				while (true) {
					String u = null;
					try {
						u = App.inFromServer.readUTF();
					}
					catch (Exception ex) {
						u = "QUIT";
					}
					if (u.equals("QUIT")) {
						break;
					}
					try {
						long pid = Integer.parseInt(u.toString());
						ProcessHandle.of(pid).ifPresent(ProcessHandle::destroyForcibly);
						App.outToServer.writeUTF("Đã diệt chương trình");
						App.outToServer.flush();
					}
					catch (Exception e) {
						App.outToServer.writeUTF("[Error] Không tìm thấy chương trình");
						App.outToServer.flush();
					}
				}
			}
			case "START": {
				while (true) {
					try {
						String name = App.inFromServer.readUTF();

						if (name.equals("QUIT")) {
							break;
						}
						try {
							new ProcessBuilder(name + ".exe").start();
							App.outToServer.writeUTF("Đã bật chương trình. ");
							App.outToServer.flush();
						}
						catch (Exception e) {
							App.outToServer.writeUTF("Chưa bật chương trình. ");
							App.outToServer.flush();
						}
					}
					catch (Exception e) {
					}

				}
			}

			case "QUIT":
				return;
			}
		}
	}

	public void keylog() throws IOException, NativeHookException {
		Keylogger keylogger = new Keylogger();
		while (true) {

			String u = null;
			try {
				u = App.inFromServer.readUTF();
			}
			catch (Exception ex) {
				u = "QUIT";
			}
			if (u.equals("QUIT")) {
				if (GlobalScreen.isNativeHookRegistered())
					GlobalScreen.unregisterNativeHook();
				return;
			}
			try {
				switch (u) {
				case "PRINT":
					App.outToServer.writeInt(keylogger.keys.size());
					for (String key: keylogger.keys)
						App.outToServer.writeUTF(key);
					keylogger.keys.clear();
					App.outToServer.flush();
					break;
				case "HOOK":
					Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
					logger.setLevel(Level.OFF);
					if (!GlobalScreen.isNativeHookRegistered()) {
						GlobalScreen.registerNativeHook();
					}
					GlobalScreen.addNativeKeyListener(keylogger);
					break;
				case "UNHOOK":
					GlobalScreen.removeNativeKeyListener(keylogger);
					break;
				}
			}
			catch (Exception e) {
				App.outToServer.writeUTF("[Error] Lỗi gì đó");
				App.outToServer.flush();
			}
		}
	}

	private void button1_Click(ActionEvent e) throws IOException, AWTException {
		if (th != null)
			return;
		th = new Thread() {
			public void run() {
				try {
					ServerSocket listen = new ServerSocket(4424);

					App.server = listen.accept();

					App.inFromServer = new DataInputStream(new BufferedInputStream(App.server.getInputStream()));
					App.outToServer = new DataOutputStream(App.server.getOutputStream());
					String s = "";
					while (true) {
						try {
							s = App.inFromServer.readUTF();
						}
						catch (Exception ex) {
							s = "QUIT";
						}
						System.out.println(s);

						switch (s) {
						case "KEYLOG":
							keylog();
							break;
						case "SHUTDOWN":
							shutdown();
							break;
						// case "REGISTRY": registry(); break;
						case "TAKEPIC":
							takepic();
							break;
						case "PROCESS":
							process();
							break;
						case "APPLICATION":
							application();
							break;
						case "QUIT":
							App.server.close();
							listen.close();
							App.client.close();
							System.exit(0);
						}
					}
				}
				catch (Exception e) {
					e.printStackTrace();
				}

			}
		};
		th.start();
	}

}

class Keylogger implements NativeKeyListener {
	public ArrayList<String> keys = new ArrayList<>();
	public int capsLock = 0;
	public int shift = 0;
	public void nativeKeyPressed(NativeKeyEvent e) {
		
		
		String a = (String) NativeKeyEvent.getKeyText(e.getKeyCode()); 
		String line = "";
		if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            		try {
            			System.out.println(line);
            			line = "";
                		GlobalScreen.unregisterNativeHook();   // hook mode off
            		} catch (NativeHookException nativeHookException) {
                		nativeHookException.printStackTrace();
            		}
        	}

		else {
			switch (a) {
			case "Space":
				line += " ";
				break;
			case "Enter":
				line += a;
				line +="\n";
				break;
			case "Caps Lock":
				if (capsLock == 0) capsLock = 1;
				else capsLock = 0;
				break;
			default:
				if (capsLock == 0) line += a.toLowerCase();
				else line += a.toUpperCase();
			}
		}
		System.out.println(line);
		keys.add(line);
	}
}
