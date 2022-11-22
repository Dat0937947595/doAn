package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.awt.Component;

public class Program {
	public static DataOutputStream outToServer;
	public static DataInputStream inFromServer;

	public static Socket client;
	public static Socket server;
	
	public static Client clientWindow;

	public static void main(String[] args) {
		clientWindow = Instance(Client.class);
	}

	public static void SendMessage(String message) throws IOException {
		if (outToServer != null) {
			outToServer.writeUTF(message);
			outToServer.flush();
		}
	}
	
	public static <T extends Component> T Instance(Class<T> type){
		T obj = null;
		try {
			obj = (T)type.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		obj.setVisible(true);
		return obj;
	}
}