package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class ThreadClient extends Thread {

	private MainClassClient client;

	private boolean keepAlive;

	public ThreadClient(MainClassClient mcc) {
		client = mcc;
		keepAlive = true;

	}

	@Override
	public void run() {

		while (keepAlive) {

			try {
				
				DataInputStream in = new DataInputStream(client.getSocket().getInputStream());

				DataOutputStream out = new DataOutputStream(client.getSocket().getOutputStream());
				
				out.writeUTF("Hello im a client");
				
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
