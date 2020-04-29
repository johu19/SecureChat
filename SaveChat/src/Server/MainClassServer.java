package Server;

import java.io.IOException;
import java.net.ServerSocket;

public class MainClassServer {
	
	public static final int PORT = 35000;
	
	private ServerSocket serverSocket;
	private ThreadServer thread;
	
	public MainClassServer() throws IOException {
		serverSocket = new ServerSocket(PORT);
		System.out.println("SERVER IS UP");
		thread = new ThreadServer(this);
		thread.start();
		
	}
	
	
	
	public ServerSocket getServerSocket() {
		return serverSocket;
	}



	public void setServerSocket(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}



	public ThreadServer getThread() {
		return thread;
	}



	public void setThread(ThreadServer thread) {
		this.thread = thread;
	}



	public static void main(String[] args) {
		try {
			MainClassServer mcs = new MainClassServer();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
