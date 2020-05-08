package Client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class MainClassClient {
	
	public static final int SERVER_PORT = 35000;
	public static final int SERVER_PORT_1 = 37000;
	public static final int SERVER_PORT_2 = 39000;
	public static final String SERVER_IP = "localhost";
	
	private Socket socket;
	private Socket socketCom;
	private ThreadClient thread;
	private ThreadCommunicationClient threadCom;
	private String name;
	private int nClient;
	private int privateKey, publicKey, cipherKey, p,g, otherPK;
	
	
	public MainClassClient() throws Exception{
		
		socket = new Socket(SERVER_IP,SERVER_PORT);
		
		@SuppressWarnings("resource")
		Scanner keyboard = new Scanner(System.in);
        System.out.print("Introduzca su nombre: ");
        this.name = keyboard.nextLine();
       
        boolean cond = false;
        while(!cond) {
        	try {
        		 System.out.print("Introduzca su llave privada (numero entero): ");
            	this.privateKey = Integer.parseInt(keyboard.nextLine());
            	cond=true;
    		} catch (Exception e) {
    			System.out.println("Debe ser un numero entero!");
    		}
        }
        
        
		thread = new ThreadClient(this);
		thread.start();
		
	}
	
	public void startComSocket(int numClient) throws Exception {
		if(numClient == 1) {
			socketCom = new Socket(SERVER_IP, SERVER_PORT_1);
		}else if(numClient ==2) {
			socketCom = new Socket(SERVER_IP, SERVER_PORT_2);
		}
	}
	
	public void startThreadCom() {
		threadCom = new ThreadCommunicationClient(this);
		threadCom.start();
	}
	
	
	
	
	public Socket getSocketCom() {
		return socketCom;
	}

	public void setSocketCom(Socket socketCom) {
		this.socketCom = socketCom;
	}

	public ThreadCommunicationClient getThreadCom() {
		return threadCom;
	}

	public void setThreadCom(ThreadCommunicationClient threadCom) {
		this.threadCom = threadCom;
	}

	public int getnClient() {
		return nClient;
	}

	public void setnClient(int nClient) {
		this.nClient = nClient;
	}

	public int getOtherPK() {
		return otherPK;
	}



	public void setOtherPK(int otherPK) {
		this.otherPK = otherPK;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(int privateKey) {
		this.privateKey = privateKey;
	}

	public int getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(int publicKey) {
		this.publicKey = publicKey;
	}

	public int getCipherKey() {
		return cipherKey;
	}

	public void setCipherKey(int cipherKey) {
		this.cipherKey = cipherKey;
	}

	public int getP() {
		return p;
	}

	public void setP(int p) {
		this.p = p;
	}

	public int getG() {
		return g;
	}

	public void setG(int g) {
		this.g = g;
	}

	
	
	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public static void main(String[] args) {
		try {
			MainClassClient mcc = new MainClassClient();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
