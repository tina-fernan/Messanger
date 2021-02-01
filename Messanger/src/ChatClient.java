import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
    
    private void logout()
    {
    	try
		{
			out.writeUTF(loginName + " LOGOUT");
			
		} 
		catch(IOException e)
		{
			e.printStackTrace();
		}
    	System.exit(1);
    }
    
    public void send()
    {
    	try
		{
			if(sendMessage.getText().length() > 0)
			{
				out.writeUTF(loginName + " DATA " + sendMessage.getText());
			}
			
			sendMessage.setText("");
		} 
		catch(IOException e)
		{
			e.printStackTrace();
		}
    	
    }
    
	public ChatClient(String loginName) throws UnknownHostException,IOException
	{
		super(loginName);
		this.loginName = loginName;
		
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				logout();
			}
		});
		
		messages = new JTextArea(18,50);
		messages.setEditable(false);
		sendMessage = new JTextField(50);
		
		sendMessage.addKeyListener(new KeyListener()
		{
			
			@Override
			public void keyTyped(KeyEvent e)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e)
			{
				
				
			}
			
			@Override
			public void keyPressed(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					send();
				}
				
			}
		});
		
		send = new JButton("Send");
		logout = new JButton("Logout");
		
		send.addActionListener(event -> {
			
			send();
		});
		
		logout.addActionListener(Event -> {
 
			logout();
		
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
		setSize(550,400);
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
	 while(true)
	{
		 try
		{
			 messages.append("\n" + in.readUTF());
			
		} catch(IOException e)
		{
			e.printStackTrace();
		}
		
	}
		
	}

}
