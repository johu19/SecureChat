package Client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainClassClient {
	
	public static final int SERVER_PORT = 35000;
	public static final String SERVER_IP = "localhost";
	
	private Socket socket;
	private ThreadClient thread;
	
	public MainClassClient() throws Exception{
		socket = new Socket(SERVER_IP,SERVER_PORT);
		thread = new ThreadClient(this);
		thread.start();
		
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
