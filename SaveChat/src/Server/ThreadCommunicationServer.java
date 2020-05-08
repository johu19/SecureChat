package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadCommunicationServer extends Thread {
	
	private MainClassServer server;
	private ServerSocket Ssocket;
	private int clientNum;
	private boolean keepAlive;
	
	public ThreadCommunicationServer(MainClassServer server, ServerSocket socket, int clientNum) {
		this.server = server;
		this.Ssocket= socket;
		this.clientNum = clientNum;
		keepAlive = true;
		
	}
	
	@Override
	public void run() {
		
		while(keepAlive) {
			
			try {
				
				Socket socket = Ssocket.accept();
				DataInputStream in = new DataInputStream(socket.getInputStream());
				DataOutputStream out = new DataOutputStream(socket.getOutputStream());
				
				String message = in.readUTF();
				
				System.out.println("Cliente listo para comunicacion");
				
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			
		}
		
	}

}
