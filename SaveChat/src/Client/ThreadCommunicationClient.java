package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class ThreadCommunicationClient extends Thread {
	
	private MainClassClient client;
	private boolean keepAlive;
	
	public ThreadCommunicationClient(MainClassClient client) {
		this.client=client;
		keepAlive = true;
	}
	
	
	@Override
	public void run() {
		
		while(keepAlive) {
			
			try {
				
				DataOutputStream out = new DataOutputStream(client.getSocketCom().getOutputStream());
				out.writeUTF("");

				DataInputStream in = new DataInputStream(client.getSocketCom().getInputStream());
				String message = in.readUTF();
				

			} catch (Exception e) {
				e.printStackTrace();
			}
			
						
		}
		
		
	}

}
