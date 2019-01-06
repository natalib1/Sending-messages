/*  chat
 *  client Class 
 *  Natali Boniel, 201122140 */

package Q2;

import java.net.InetAddress;

public class Client {
	
	private int port;
	private InetAddress address;
	
	public Client(int port, InetAddress address) 
	{
		super();
		this.port = port;
		this.address = address;
	}

	public int getPort() 
	{
		return port;
	}

	public void setPort(int port) 
	{
		this.port = port;
	}

	public InetAddress getAddress() 
	{
		return address;
	}

	public void setAddress(InetAddress address) 
	{
		this.address = address;
	}
}
