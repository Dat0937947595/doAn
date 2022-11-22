package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import com.github.kwhat.jnativehook.NativeHookException;

public class Program {
	public static  Socket server;
	public static Socket client;
	
	
	public static DataInputStream  inFromServer;
	public static DataOutputStream  outToServer;

	
	public static void main(String[] args) throws IOException, NativeHookException
	{	
		Server server = new Server();
		server.setVisible(true);
	}
}
