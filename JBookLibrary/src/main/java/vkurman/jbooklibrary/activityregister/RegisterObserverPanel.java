package vkurman.jbooklibrary.activityregister;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.StyledDocument;

/**
 * UI for <code>ActivityRegister</code> which extends
 * <code>JPanel</code>.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class RegisterObserverPanel extends JPanel implements RegisterObserver {
	
	private static final long serialVersionUID = -2662380473625810491L;
	public static final Font FONT_TEXT_PANE = new Font("Serif", Font.BOLD, 14);
	public static final Color COLOUR_TEXTPANE_BACKGROUND = Color.WHITE;
	public static final Color COLOUR_TEXTPANE_FOREGROUND = Color.BLACK;
	
	private JTextPane textPane;
	private StyledDocument doc;
	
	/**
	 * Constructor.
	 */
	public RegisterObserverPanel(){
		showUI();
	}
	
	/**
	 * Creates and displays UI.
	 */
	private void showUI(){
		textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setFont(FONT_TEXT_PANE);
		textPane.setBackground(COLOUR_TEXTPANE_BACKGROUND);
		textPane.setForeground(COLOUR_TEXTPANE_FOREGROUND);
		
		doc = textPane.getStyledDocument();
		
		//Put the editor pane in a scroll pane.
		JScrollPane scrollPane = new JScrollPane(textPane);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setPreferredSize(new Dimension(512, 128));
		scrollPane.setMinimumSize(new Dimension(64, 32));
		
		add(scrollPane);
	}
	
	@Override
	public void displayActivity(String text){
		try{
			doc.insertString(doc.getLength(), text + "\n", null);
		} catch (Exception e) {
			EventQueue.invokeLater(new Runnable(){
				@Override
				public void run() {
					JOptionPane.showMessageDialog(
							RegisterObserverPanel.this,
							"Error occured while trying to display activity message!",
							"RegisterObserverPanel error!",
							JOptionPane.ERROR_MESSAGE);
				}
			});
		}
	}
}