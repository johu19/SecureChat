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
				
				if(clientNum ==1 && server.getSecondClientPublicKey()==-1 ) {
					while(server.getSecondClientPublicKey()==-1) {
						Thread.sleep(500);
					}
					out.writeUTF(server.getSecondClientPublicKey()+"");
				}
				
				
				if(clientNum ==1) {
					
					//Servidor recibe mensaje del cliente 1
					String newMessage = in.readUTF();
					server.setLatestMessage(newMessage);
					server.setTrigger(true);
					
					while(server.getLatestMessage()==newMessage) {
						Thread.sleep(50);
					}
					
					
					
					//Servidor envia el mensaje del cliente 2 al cliente 1
					out.writeUTF(server.getLatestMessage());
					
				}else if(clientNum ==2) {
					while(server.getLatestMessage()==null || !server.isTrigger()) {
						Thread.sleep(50);
					}
					
					//Servidor envia el mensaje del cliente 1 al cliente 2
					out.writeUTF(server.getLatestMessage());
					
					//Servidor recibe mensaje del cliente 2
					String newMessage = in.readUTF();
					server.setTrigger(false);
					server.setLatestMessage(newMessage);
				}
				
				Thread.sleep(100);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			
		}
		
	}

}
