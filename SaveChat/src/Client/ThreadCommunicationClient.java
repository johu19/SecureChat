package Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.Scanner;

public class ThreadCommunicationClient extends Thread {

	private MainClassClient client;
	private boolean keepAlive;

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
					
					//Envia al servidor el mensaje
					out.writeUTF(client.getName() + ": " + message);
					
					//Recibe del servidor el mensaje que haya sido enviado por el otro cliente
					String newMessage = in.readUTF();
					System.out.println(newMessage);

					
				} else if (client.getnClient() == 2) {
					//En caso de que el cliente haya sido el segundo en conectarse al servidor

					
					//Recibe del servidor el mensaje que haya sido enviado por el otro cliente
					String newMessage = in.readUTF();
					System.out.println(newMessage);
					@SuppressWarnings("resource")
					Scanner keyboard = new Scanner(System.in);
					System.out.print("Tu: ");
					
					//Obtiene el mensaje escrito por el cliente en la consola
					String message = keyboard.nextLine();
					
					//Recibe del servidor el mensaje que haya sido enviado por el otro cliente
					out.writeUTF(client.getName() + ": " + message);

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
