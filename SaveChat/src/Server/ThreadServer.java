package Server;

import java.io.DataInputStream;
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

		while (keepAlive) {

			try {
				
				Socket socket = server.getServerSocket().accept();
				DataInputStream in = new DataInputStream(socket.getInputStream());
				String message = in.readUTF();
				System.out.println("a client says: "+ message);
				Thread.sleep(100);

			} catch (Exception e) {

				e.printStackTrace();
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
