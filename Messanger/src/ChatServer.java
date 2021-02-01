import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

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
			
			start();
			
		}
		
		public void run()
		{
			while(true)
			{
				try
				{
					String msgFromClient = in.readUTF();
					
					StringTokenizer msgParts = new StringTokenizer(msgFromClient);
					
					String name= msgParts.nextToken();
					String msgType = msgParts.nextToken();
					
					StringBuffer messageBuffer= new StringBuffer();
					
					while (msgParts.hasMoreTokens())
					{
						messageBuffer.append(" "+ msgParts.nextToken());
					}
					String message = messageBuffer.toString();
					
					switch(msgType)
					{
					   case "LOGIN":
					   clientSockets.forEach(socket -> {
						   notifyLogin(socket, name);
					   });
					
					}
					
					
					
				} catch (IOException e)
				{
					
					e.printStackTrace();
				}
			}
		}
		
	public void notifyLogin(Socket socket,String name)
	{
		try
		{
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		out.writeUTF(name + " has loged in");
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
		
	}
	public static void main(String[] args) throws IOException
	{
		new ChatServer();
	}

}
