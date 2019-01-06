/*  chat
 *  present the messages of the server 
 *  Natali Boniel, 201122140 */

package Q2;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class MsgPanel extends JPanel {
	
	private JLabel wclients = new JLabel("0");

	public MsgPanel() 
	{
		super();
		this.add(new JLabel("Clients:"));
		this.add(wclients);

		JLabel label = new JLabel("");
		this.add(label);
	}

	public synchronized void updateWatcher(String newValue) 
	{
		this.wclients.setText(newValue);
	}

}
