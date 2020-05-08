package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Random;

public class MainClassServer {
	
	public static final int PORT = 35000;
	
	public static final int PORT_1 = 37000;
	
	public static final int PORT_2 = 39000;
	
	private ServerSocket serverSocketInitial;
	
	private ServerSocket serverSocket1;
	
	private ServerSocket serverSocket2;
	
	private ThreadCommunicationServer threadCom1, threadCom2;
	
	private ThreadServer thread;
	
	private int firstClientPublicKey;
	private int secondClientPublicKey;
	
	private int p,g;
	
	public MainClassServer() throws IOException {
		serverSocketInitial = new ServerSocket(PORT);
		System.out.println("SERVER IS UP");
		
		
		boolean cond=false;
		
		while(!cond) {
			p=(int) (Math.random() * 100) + 1;
			cond = isPrime(p);
		}
		
		g= findPrimitive(p);
		
		System.out.println("g : "+g);
		System.out.println("p : "+p);

		
		thread = new ThreadServer(this);
		thread.start();
		
	}
	
	public void initializeFirstSocket() throws IOException {
		serverSocket1 = new ServerSocket(PORT_1);
		threadCom1 = new ThreadCommunicationServer(this, serverSocket1, 1);
		threadCom1.start();
	}
	
	public void initializeSecondSocket() throws IOException {
		serverSocket2 = new ServerSocket(PORT_2);
		threadCom2 = new ThreadCommunicationServer(this, serverSocket2, 2);
		threadCom2.start();
	}
	
	//////Metodos isPrime, power, findPrimeFactors y findPrimitive obtenidos de https://www.geeksforgeeks.org/primitive-root-of-a-prime-number-n-modulo-n/
	
	// Returns true if n is prime 
    public boolean isPrime(int n)  
    { 
        // Corner cases 
        if (n <= 1) 
        { 
            return false; 
        } 
        if (n <= 3)  
        { 
            return true; 
        } 
  
        // This is checked so that we can skip 
        // middle five numbers in below loop 
        if (n % 2 == 0 || n % 3 == 0)  
        { 
            return false; 
        } 
  
        for (int i = 5; i * i <= n; i = i + 6)  
        { 
            if (n % i == 0 || n % (i + 2) == 0)  
            { 
                return false; 
            } 
        } 
  
        return true; 
    } 
  
    /* Iterative Function to calculate (x^n)%p in 
    O(logy) */
    public int power(int x, int y, int p) 
    { 
        int res = 1;     // Initialize result 
  
        x = x % p; // Update x if it is more than or 
        // equal to p 
  
        while (y > 0)  
        { 
            // If y is odd, multiply x with result 
            if (y % 2 == 1)  
            { 
                res = (res * x) % p; 
            } 
  
            // y must be even now 
            y = y >> 1; // y = y/2 
            x = (x * x) % p; 
        } 
        return res; 
    } 
  
    // Utility function to store prime factors of a number 
    public void findPrimefactors(HashSet<Integer> s, int n)  
    { 
        // Print the number of 2s that divide n 
        while (n % 2 == 0)  
        { 
            s.add(2); 
            n = n / 2; 
        } 
  
        // n must be odd at this point. So we can skip 
        // one element (Note i = i +2) 
        for (int i = 3; i <= Math.sqrt(n); i = i + 2)  
        { 
            // While i divides n, print i and divide n 
            while (n % i == 0)  
            { 
                s.add(i); 
                n = n / i; 
            } 
        } 
  
        // This condition is to handle the case when 
        // n is a prime number greater than 2 
        if (n > 2)  
        { 
            s.add(n); 
        } 
    } 
  
    // Function to find smallest primitive root of n 
    public int findPrimitive(int n)  
    { 
        HashSet<Integer> s = new HashSet<Integer>(); 
  
        // Check if n is prime or not 
        if (isPrime(n) == false)  
        { 
            return -1; 
        } 
  
        // Find value of Euler Totient function of n 
        // Since n is a prime number, the value of Euler 
        // Totient function is n-1 as there are n-1 
        // relatively prime numbers. 
        int phi = n - 1; 
  
        // Find prime factors of phi and store in a set 
        findPrimefactors(s, phi); 
  
        // Check for every number from 2 to phi 
        for (int r = 2; r <= phi; r++)  
        { 
            // Iterate through all prime factors of phi. 
            // and check if we found a power with value 1 
            boolean flag = false; 
            for (Integer a : s)  
            { 
  
                // Check if r^((phi)/primefactors) mod n 
                // is 1 or not 
                if (power(r, phi / (a), n) == 1)  
                { 
                    flag = true; 
                    break; 
                } 
            } 
  
            // If there was no power with value 1. 
            if (flag == false) 
            { 
                return r; 
            } 
        } 
  
        // If no primitive root found 
        return -1; 
    }
	
	
	
	
	public ThreadCommunicationServer getThreadCom1() {
		return threadCom1;
	}

	public void setThreadCom1(ThreadCommunicationServer threadCom1) {
		this.threadCom1 = threadCom1;
	}

	public ThreadCommunicationServer getThreadCom2() {
		return threadCom2;
	}

	public void setThreadCom2(ThreadCommunicationServer threadCom2) {
		this.threadCom2 = threadCom2;
	}

	public ServerSocket getServerSocketInitial() {
		return serverSocketInitial;
	}

	public void setServerSocketInitial(ServerSocket serverSocketInitial) {
		this.serverSocketInitial = serverSocketInitial;
	}

	public ServerSocket getServerSocket1() {
		return serverSocket1;
	}

	public void setServerSocket1(ServerSocket serverSocket1) {
		this.serverSocket1 = serverSocket1;
	}

	public ServerSocket getServerSocket2() {
		return serverSocket2;
	}

	public void setServerSocket2(ServerSocket serverSocket2) {
		this.serverSocket2 = serverSocket2;
	}

	public int getFirstClientPublicKey() {
		return firstClientPublicKey;
	}

	public void setFirstClientPublicKey(int firstClientPublicKey) {
		this.firstClientPublicKey = firstClientPublicKey;
	}

	public int getSecondClientPublicKey() {
		return secondClientPublicKey;
	}

	public void setSecondClientPublicKey(int secondClientPublicKey) {
		this.secondClientPublicKey = secondClientPublicKey;
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

}
