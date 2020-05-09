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

				client.setG(Integer.parseInt(message.split(",")[0]));
				client.setP(Integer.parseInt(message.split(",")[1]));
				
				int numClient = Integer.parseInt(message.split(",")[2]);
				
				client.setnClient(numClient);
				
				System.out.println("Número del cliente : "+client.getnClient());
				System.out.println("G : "+client.getG());
				System.out.println("P : "+client.getP());

				
				
				if(Integer.parseInt(message.split(",")[2])==2){
					String otherPK = in.readUTF();
					client.setOtherPK(Long.parseLong(otherPK));
					System.out.println("Llave publica del otro cliente: " + client.getOtherPK());
				}
				
				
				//(g^a mod p)
				
				double d=(Math.pow(client.getG(), client.getPrivateKey())%client.getP());
				long pk = (long) d;
				
				client.setPublicKey(pk);

				System.out.println("Llave publica : "+client.getPublicKey());
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
