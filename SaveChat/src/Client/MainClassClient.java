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
	private long privateKey, publicKey, cipherKey, p,g, otherPK;
	
	
	public MainClassClient() throws Exception{
		
		socket = new Socket(SERVER_IP,SERVER_PORT);
		
		otherPK = -1;
		
		@SuppressWarnings("resource")
		Scanner keyboard = new Scanner(System.in);
        System.out.print("Introduzca su nombre: ");
        this.name = keyboard.nextLine();
       
        boolean cond = false;
        while(!cond) {
        	try {
        		System.out.print("Introduzca su llave privada (numero entero positivo menor 10): ");
        		int key = Integer.parseInt(keyboard.nextLine());
        		if(key>0 && key<10) {
        			this.privateKey = key;
        		}else {
        			throw new Exception();
        		}
            	
            	cond=true;
    		} catch (Exception e) {
    			System.out.println("Debe ser un numero entero positivo menor que 20!");
    		}
        }
        
        
		thread = new ThreadClient(this);
		thread.start();
		
	}
	
	//Generated Secret Key = k_a = y^a mod P	Generated Secret Key = k_b = x^b mod P
	public void generateCipherKey() {
		double d = (Math.pow(otherPK, privateKey)) % p;
		cipherKey = (long) d;
		System.out.println("Llave de cifrado de "+name+" : "+cipherKey);
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

	


	public long getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(long privateKey) {
		this.privateKey = privateKey;
	}

	public long getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(long publicKey) {
		this.publicKey = publicKey;
	}

	public long getCipherKey() {
		return cipherKey;
	}

	public void setCipherKey(long cipherKey) {
		this.cipherKey = cipherKey;
	}

	public long getP() {
		return p;
	}

	public void setP(long p) {
		this.p = p;
	}

	public long getG() {
		return g;
	}

	public void setG(long g) {
		this.g = g;
	}

	public long getOtherPK() {
		return otherPK;
	}

	public void setOtherPK(long otherPK) {
		this.otherPK = otherPK;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
