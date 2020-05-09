package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ThreadServer extends Thread {

	private MainClassServer server;
	private boolean keepAlive;

	public ThreadServer(MainClassServer mcs) {
		server = mcs;
		keepAlive = true;
	}

	@Override
	public void run() {
		int n = 1;

		while (keepAlive) {
			
			if(n ==1 ) {
				try {
					Socket socket = server.getServerSocketInitial().accept();
					DataInputStream in = new DataInputStream(socket.getInputStream());
					String message = in.readUTF();
					System.out.println(message + " se ha conectado(" + n + ")");

					DataOutputStream out = new DataOutputStream(socket.getOutputStream());
					out.writeUTF(server.getG() + "," + server.getP()+","+n);

					long clientPublicKey = Long.parseLong(in.readUTF());
					System.out.println("Llave publica del cliente " + n + ": " + clientPublicKey);
					
					server.setFirstClientPublicKey(clientPublicKey);

					n++;
					
					server.initializeFirstSocket();

					Thread.sleep(100);

				} catch (Exception e) {

					e.printStackTrace();
				}
			}else if( n==2 ) {
				try {
					Socket socket = server.getServerSocketInitial().accept();
					DataInputStream in = new DataInputStream(socket.getInputStream());
					String message = in.readUTF();
					System.out.println(message + " se ha conectado(" + n + ")");

					DataOutputStream out = new DataOutputStream(socket.getOutputStream());
					out.writeUTF(server.getG() + "," + server.getP()+","+n);
					out.writeUTF(server.getFirstClientPublicKey()+"");

					long clientPublicKey = Long.parseLong(in.readUTF());
					System.out.println("Llave publica del cliente " + n + ": " + clientPublicKey);
					
					server.setSecondClientPublicKey(clientPublicKey);

					n++;
					
					server.initializeSecondSocket();

					Thread.sleep(100);

				} catch (Exception e) {

					e.printStackTrace();
				}
			}else if (n>2) {
				keepAlive = false;
				
				
			}

			

		}

	}

	public boolean isKeepAlive() {
		return keepAlive;
	}

	public void setKeepAlive(boolean keepAlive) {
		this.keepAlive = keepAlive;
	}

}
