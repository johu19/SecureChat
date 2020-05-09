package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Scanner;

public class ThreadCommunicationClient extends Thread {

	private MainClassClient client;
	private boolean keepAlive;
	private EncryterDecrypter encryterDecrypter;

	public ThreadCommunicationClient(MainClassClient client) {
		this.client = client;
		keepAlive = true;
	}

	@Override
	public void run() {

		try {

			boolean cond = true;

			DataOutputStream out = new DataOutputStream(client.getSocketCom().getOutputStream());
			out.writeUTF("");

			DataInputStream in = new DataInputStream(client.getSocketCom().getInputStream());

			while (keepAlive) {

				if (client.getnClient() == 1 && client.getOtherPK() == -1) {
					String otherKey = in.readUTF();
					client.setOtherPK(Integer.parseInt(otherKey));
					System.out.println("Llave publica del otro cliente: " + client.getOtherPK());

				}

				if (cond) {
					client.generateCipherKey();
					System.out.println("---------------------------------------------------");
					System.out.println("----------------------- CHAT ----------------------");
					System.out.println("---------------------------------------------------");
					cond = false;
				}


				if (client.getnClient() == 1) {
					//En caso de que el cliente haya sido el primero en conectarse al servidor
					
					@SuppressWarnings("resource")
					Scanner keyboard = new Scanner(System.in);
					System.out.print("Tu: ");
					
					//Obtiene el mensaje escrito por el cliente en la consola
					String message = keyboard.nextLine();
					
					//encrypta el mensaje escrito por el cliente
					String EncrypMessage=encryterDecrypter.encriptar(message, client.getCipherKey());
					
					
					//Envia al servidor el mensaje
					out.writeUTF(client.getName() + ": " + EncrypMessage);
					
					//Recibe del servidor el mensaje que haya sido enviado por el otro cliente
					String newMessage = in.readUTF();
					
					String[] messageEncrypt=newMessage.split(": ");
					//desencripta el mensaje recibido por el cliente
					String DecryptMessage= encryterDecrypter.doDecrypt(messageEncrypt[1], client.getCipherKey());
					
					System.out.println(messageEncrypt[0]+": "+DecryptMessage);
					

					
				} else if (client.getnClient() == 2) {
					//En caso de que el cliente haya sido el segundo en conectarse al servidor

					
					//Recibe del servidor el mensaje que haya sido enviado por el otro cliente
					String newMessage = in.readUTF();
					
					String[] messageEncrypt=newMessage.split(": ");
					//desencripta el mensaje recibido por el cliente
					String DecryptMessage= encryterDecrypter.doDecrypt(messageEncrypt[1], client.getCipherKey());
					
					System.out.println(messageEncrypt[0]+": "+DecryptMessage);
					
					@SuppressWarnings("resource")
					Scanner keyboard = new Scanner(System.in);
					System.out.print("Tu: ");
					
					//Obtiene el mensaje escrito por el cliente en la consola
					String message = keyboard.nextLine();
					
					//encrypta el mensaje escrito por el cliente
					String EncrypMessage=encryterDecrypter.encriptar(message, client.getCipherKey());
					
					
					//Envia al servidor el mensaje
					out.writeUTF(client.getName() + ": " + EncrypMessage);

				}

				Thread.sleep(100);

			}
			
			out.flush();
			out.close();
			in.close();

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

}
