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
				

				DataOutputStream out = new DataOutputStream(client.getSocket().getOutputStream());
				out.writeUTF(client.getName());
				

				DataInputStream in = new DataInputStream(client.getSocket().getInputStream());
				String message = in.readUTF();
				System.out.println("g, p, numero cliente : "+message);

				client.setG(Integer.parseInt(message.split(",")[0]));
				client.setP(Integer.parseInt(message.split(",")[1]));
				
				int numClient = Integer.parseInt(message.split(",")[2]);
				
				client.setnClient(numClient);
				
				if(Integer.parseInt(message.split(",")[2])==2){
					String otherPK = in.readUTF();
					client.setOtherPK(Integer.parseInt(otherPK));
					System.out.println("Other client PK: " + client.getOtherPK());
				}
				
				
				//(g^a mod p)
				
				double d=(Math.pow(client.getG(), client.getPrivateKey())%client.getP());
				int pk = Math.toIntExact(Math.round(d));
				
				client.setPublicKey(pk);

				System.out.println("publicKey : "+client.getPublicKey());
				out.writeUTF(client.getPublicKey()+"");
				
				keepAlive = false;

				Thread.sleep(100);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			

		}
		try {
			client.startComSocket(client.getnClient());
			client.startThreadCom();
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

	public boolean isKeepAlive() {
		return keepAlive;
	}

	public void setKeepAlive(boolean keepAlive) {
		this.keepAlive = keepAlive;
	}

}
