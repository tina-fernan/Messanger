import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.sun.jdi.event.Event;

public class ChatClient extends JFrame implements Runnable
{
	String loginName;
	
	JTextArea messages;
    JTextField sendMessage;
    
    JButton send;
    JButton logout;
    
    DataInputStream in;
    DataOutputStream out;
    
	public ChatClient(String loginName) throws UnknownHostException,IOException
	{
		super(loginName);
		this.loginName = loginName;
		
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				
			}
		});
		
		messages = new JTextArea(18,50);
		messages.setEditable(false);
		sendMessage = new JTextField(50);
		
		send = new JButton("Send");
		logout = new JButton("Logout");
		
		send.addActionListener(event -> {
			
			try
			{
				if(sendMessage.getText().length() > 0)
				{
					out.writeUTF(loginName + "DATA" + sendMessage.getText());
				}
				
				sendMessage.setText("");
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
		});
		
		logout.addActionListener(Event -> {

			try
			{
				if(sendMessage.getText().length() > 0)
				{
					out.writeUTF(loginName + "LogOut");
				}

				System.exit(1);
			} 
			catch (IOException e)
			{
				e.printStackTrace();
			}
		});
		
		Socket socket = new Socket("127.0.0.1",5217);
		
		in= new DataInputStream(socket.getInputStream());
		out= new DataOutputStream(socket.getOutputStream());
		
		out.writeUTF(loginName);
		out.writeUTF(loginName + " LOGIN");
		
		
		setUp();
	}
	
	private void setUp()
	{
		setSize(600,400);
		JPanel panel = new JPanel();
		
		panel.add(new JScrollPane(messages));
		panel.add(sendMessage);
		panel.add(send);
		panel.add(logout);
		
		
		add(panel);
		new Thread(this).start();
		
		setVisible(true);
		
	}

	@Override
	public void run()
	{
	 while (true)
	{
		 try
		{
			 messages.append("\n" + in.readUTF());
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
		
	}

}
