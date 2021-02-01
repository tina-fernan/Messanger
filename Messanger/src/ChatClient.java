import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatClient extends JFrame
{
	String loginName;
	
	JTextArea messages;
    JTextField sendMessage;
    
    JButton send;
    JButton logout;
    
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
		
		setVisible(true);
		
	}

}
