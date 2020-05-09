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
		this.Ssocket = socket;
		this.clientNum = clientNum;
		keepAlive = true;

	}

	@Override
	public void run() {

		try {

			Socket socket = Ssocket.accept();
			DataInputStream in = new DataInputStream(socket.getInputStream());
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());

			String message = in.readUTF();

			System.out.println("Cliente listo para comunicacion");

			if (clientNum == 1 && server.getSecondClientPublicKey() == -1) {
				while (server.getSecondClientPublicKey() == -1) {
					Thread.sleep(500);
				}
				out.writeUTF(server.getSecondClientPublicKey() + "");
			}

			while (keepAlive) {

				if (clientNum == 1) {

					// Servidor recibe mensaje del cliente 1
					String newMessage = in.readUTF();
					server.setLatestMessageForClient2(newMessage);
//					server.setTrigger(true);

					while (server.getLatestMessageForClient1() == null) {
						Thread.sleep(50);
					}

					// Servidor envia el mensaje del cliente 2 al cliente 1
					out.writeUTF(server.getLatestMessageForClient1());
					System.out.println("Envia el siguiente mensaje a cliente 1: " + server.getLatestMessageForClient1());
					
					server.setLatestMessageForClient1(null);

				} else if (clientNum == 2) {
//					while (server.getLatestMessage() == null || !server.isTrigger()) {
//						Thread.sleep(50);
//					}
					
					while(server.getLatestMessageForClient2() == null) {
						Thread.sleep(50);
					}

					// Servidor envia el mensaje del cliente 1 al cliente 2
					System.out.println("Envia el siguiente mensaje a cliente 2: " + server.getLatestMessageForClient2());
					out.writeUTF(server.getLatestMessageForClient2());
//					server.setLatestMessage(null);

					// Servidor recibe mensaje del cliente 2
					String newMessage = in.readUTF();
					
//					server.setTrigger(false);
					
					server.setLatestMessageForClient1(newMessage);
					
					server.setLatestMessageForClient2(null);
				}

				Thread.sleep(50);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
