/*  chat
 *  server Class 
 *  Natali Boniel, 201122140 */

package Q2;

import java.awt.BorderLayout;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MultiServer extends JFrame implements Runnable{

	private final int PORT = 6666;
	
	//clients
	private Map<String, Client> clients = new HashMap<String, Client>();

	private byte[] buf = new byte[1000];
	private DatagramSocket ds = null;

	private DatagramPacket out;
	private DatagramPacket in = new DatagramPacket(buf, buf.length);

	private JButton send = new JButton("Send To All Clients");
	private MsgPanel wclients = new MsgPanel();
	private JTextArea messageText = new JTextArea("My Message");

	public MultiServer() 
	{
		super("SERVER");
		
		this.setLayout(new BorderLayout());
		this.add(wclients, BorderLayout.NORTH);
		this.add(send, BorderLayout.SOUTH);
		this.add(messageText, BorderLayout.CENTER);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(70, 70, 450, 200);
		this.setVisible(true);

		//server socket
		try 
		{
			ds = new DatagramSocket(PORT);
		} catch (IOException e) {
			e.printStackTrace();
		}

		send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendMsg(messageText.getText());
				messageText.setText("");
			}
		});

		this.run();
	}

	private void sendMsg(String s) 
	{
		buf = (s).getBytes();
		try 
		{
			for (Entry<String, Client> cl : clients.entrySet()) 
			{
				Client c = cl.getValue();
				out = new DatagramPacket(buf, buf.length, c.getAddress(), c.getPort());
				ds.send(out);
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private String getMsg() 
	{
		return messageText.getText();
	}

	public void run() 
	{
		while (true) 
		{
			try 
			{
				ds.receive(in);
				String dataFromClient = new String(in.getData(), 0, in.getLength());

				String[] data = dataFromClient.split(",");
				System.out.println(dataFromClient + ", No clients: " + clients.size());

				if (data[1].equals("add")) 
				{
					if (!clients.containsKey(data[0])) 
					{
						buf = ("Welcome")
								.getBytes();
						out = new DatagramPacket(buf, buf.length, in.getAddress(), in.getPort());
						clients.put(data[0], new Client(in.getPort(), in.getAddress()));
						wclients.updateWatcher(clients.size()+"");
						ds.send(out);
					}
				} else if (data[1].equals("remove")) 
				{
					if (clients.containsKey(data[0])) 
					{
						buf = ("End of messages")
								.getBytes();
						out = new DatagramPacket(buf, buf.length, in.getAddress(), in.getPort());
						clients.remove(data[0]);
						wclients.updateWatcher(clients.size()+"");
						ds.send(out);
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		new MultiServer();
	}
}
