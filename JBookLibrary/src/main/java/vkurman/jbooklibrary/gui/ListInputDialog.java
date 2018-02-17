package vkurman.jbooklibrary.gui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Dialog that offers to user to choose <code>String</code> value
 * from the list.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class ListInputDialog extends JDialog {

	private static final long serialVersionUID = 8615368898717219472L;
	private List<String> list;
	private JComboBox cbxList;
	private String value;
	private boolean okPressed;
	
	/**
	 * Constructor.
	 * 
	 * @param list
	 */
	public ListInputDialog(String title, List<String> list) {
		if(list == null || list.isEmpty()){
			UIDisplayManager.displayErrorMessage(
					this,
					"List is empty!");
			
			dispose();
		} else {
			this.list = list;
			value = null;
			okPressed = false;
			
			showUI(title);
		}
	}
	
	/**
	 * Creates and displays UI.
	 * 
	 * @param title
	 */
	private void showUI(String title){
		setTitle(title);
		setLayout(new BorderLayout(10, 10));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		{
			JPanel textPanel = new JPanel();
			textPanel.setLayout(new FlowLayout());
			
			JLabel lblValue = new JLabel("Value: ");
			textPanel.add(lblValue);
			
			cbxList = new JComboBox();
			for (int i = 0; i < list.size(); i++) {
				cbxList.addItem(list.get(i));
			}
			
			textPanel.add(cbxList);
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
						if(cbxList.getSelectedIndex() != -1){
							value = (String) cbxList.getSelectedItem();
							okPressed = true;
							dispose();
						} else {
							UIDisplayManager.displayErrorMessage(
									ListInputDialog.this,
									"Value hasn't been selected!\n" +
									"Please select value from provided box");
						}
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
	 * Returns value from input field.
	 * 
	 * @return String
	 */
	public String getInput(){
		return value;
	}
	
	/**
	 * Marker for <code>OK</code> button if it has been
	 * pressed or not.
	 * 
	 * @return boolean
	 */
	public boolean isOkPressed() {
		return okPressed;
	}
}