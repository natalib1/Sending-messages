/*  chat
 *  part of the client panel (Thread client) for the exit and login buttons 
 *  Natali Boniel, 201122140 */

package Q2;

import javax.swing.JButton;
import javax.swing.JPanel;

public class ClientPanel extends JPanel {
	
	final private JButton exit = new JButton("Exit");
	final private JButton login = new JButton("Log in");

	public ClientPanel() 
	{
		super();
		this.add(exit);
		this.add(login);
	}

	public JButton getExit() 
	{
		return exit;
	}

	public JButton getLogin() 
	{
		return login;
	}

}
