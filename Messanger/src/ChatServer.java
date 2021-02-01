import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer
{
	ArrayList<String>loginNames;
	ArrayList<Socket>clientSockets;
	
	public ChatServer() throws IOException
	{
		ServerSocket server = new ServerSocket(5217);
		loginNames = new ArrayList<String>();
		clientSockets = new ArrayList<Socket>();
		
		while(true)
		{
			Socket clientSocket = server.accept();
			Client client = new Client(clientSocket);
		}
	}
	
	class Client extends Thread
	{
		Socket clientSocket;
		
		DataInputStream in;
		DataOutputStream out;
		
		Client(Socket client) throws IOException
		{
			clientSocket = client;
			in= new DataInputStream(clientSocket.getInputStream());
			out= new DataOutputStream(clientSocket.getOutputStream());
			
			String loginName = in.readUTF();
			System.out.println("Login Name" + loginName);
			
			loginNames.add(loginName);
			clientSockets.add(clientSocket);
			
		}
		
	}
	public static void main(String[] args) throws IOException
	{
		new ChatServer();
	}

}
