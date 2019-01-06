/*  chat
 *  thread client Class 
 *  Natali Boniel, 201122140 */

package Q2;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

public class ThreadClient extends JFrame implements Runnable{
	
	private int PORT = 6666;
	private InetAddress host;
	private DatagramSocket ds;
	private byte[] buf = new byte[1000];

	private JEditorPane messageArea = new JEditorPane("text/html", "");
	private ClientPanel cp = new ClientPanel();

	private DatagramPacket in = new DatagramPacket(buf, buf.length);
	private DatagramPacket out;

	final private String clientNum;

	public ThreadClient() 
	{
		super("CLIENT");
		final JFrame me = this;
		try 
		{
			host = InetAddress.getByName("localhost");
			ds = new DatagramSocket();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		catch (SocketException e) {
			e.printStackTrace();
		}
		this.clientNum = UUID.randomUUID().toString();

		JScrollPane scroll = new JScrollPane(messageArea);
		messageArea.setEditable(false);
		this.add(scroll, BorderLayout.CENTER);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setBounds(70, 70, 400, 400);
		this.add(cp, BorderLayout.NORTH);
		this.setVisible(true);

		sendAddMsg();

		this.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				sendRemoveMsg();
				System.exit(0);
			}
		});

		cp.getLogin().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendAddMsg();
			}
		});
		cp.getExit().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sendRemoveMsg();
			}
		});

		this.run();
	}

	private void sendRemoveMsg() 
	{
		String send = clientNum + ",remove";
		buf = send.getBytes();
		out = new DatagramPacket(buf, buf.length, host, PORT);
		try 
		{
			ds.send(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void sendAddMsg() 
	{
		String send = clientNum + ",add";
		buf = send.getBytes();
		out = new DatagramPacket(buf, buf.length, host, PORT);
		try 
		{
			ds.send(out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void appendMessage(String msg) 
	{
		String msgToSet = msg + messageArea.getText();
		messageArea.setText(msgToSet);
	}

	public void run() 
	{
		while (true) 
		{
			try 
			{
				ds.receive(in);
			} catch (IOException e) {
				e.printStackTrace();
			}
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			Date date = new Date();
			String rcvd = "<b>" + dateFormat.format(date) + "</b>: " + new String(in.getData(), 0, in.getLength());

			this.appendMessage(rcvd + "\n");
		}
	}

	public static void main(String[] args) {
		new ThreadClient();
	}
}
