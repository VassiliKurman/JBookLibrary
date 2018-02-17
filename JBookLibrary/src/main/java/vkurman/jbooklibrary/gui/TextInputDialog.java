package vkurman.jbooklibrary.gui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * <code>JDialog</code> that takes input text from application
 * user.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class TextInputDialog extends JDialog {

	private static final long serialVersionUID = 6236300312794433904L;
	private JTextField input;
	private String title, text;
	private boolean okPressed;
	
	/**
	 * Constructor
	 */
	public TextInputDialog() {
		this("Text input", null);
	}
	
	/**
	 * Constructor
	 * 
	 * @param title
	 */
	public TextInputDialog(String title) {
		this(title, null);
	}
	
	/**
	 * Constructor
	 * 
	 * @param title
	 * @param text
	 */
	public TextInputDialog(String title, String text) {
		this.title = title;
		this.text = text;
		this.okPressed = false;
		
		showUI();
	}
	
	/**
	 * Creates and displays UI.
	 */
	private void showUI(){
		setTitle(title);
		getContentPane().setLayout(new BorderLayout(10, 10));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		{
			JPanel textPanel = new JPanel();
			textPanel.setLayout(new FlowLayout());
			
			input = new JTextField(text, 30);
			
			textPanel.add(input);
			getContentPane().add(textPanel, BorderLayout.CENTER);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.PAGE_END);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				okButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent ev) {
						text = input.getText();
						okPressed = true;
						dispose();
					}
				});
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent ev) {
						dispose();
					}
				});
				buttonPane.add(cancelButton);
			}
		}
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * Returns input text.
	 * 
	 * @return String
	 */
	public String getInput(){
		return text;
	}
	
	/**
	 * Returns true if <code>OK</code> button was pressed.
	 * 
	 * @return boolean
	 */
	public boolean isOkPressed() {
		return okPressed;
	}
}