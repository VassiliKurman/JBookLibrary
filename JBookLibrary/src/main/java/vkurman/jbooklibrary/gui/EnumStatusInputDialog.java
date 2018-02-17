package vkurman.jbooklibrary.gui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import vkurman.jbooklibrary.enums.GeneralStatus;

/**
 * Dialog that offers to user to choose general status.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class EnumStatusInputDialog extends JDialog {

	private static final long serialVersionUID = 8615368898717219472L;
	private JComboBox cbxGeneralStatus;
	private GeneralStatus value;
	private boolean okPressed;
	
	public EnumStatusInputDialog() {
		this(null);
	}
	
	public EnumStatusInputDialog(GeneralStatus generalStatus) {
		this.value = generalStatus;
		this.okPressed = false;
		
		showUI();
	}
	
	private void showUI(){
		setTitle("Change generalStatus");
		getContentPane().setLayout(new BorderLayout(10, 10));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		{
			JPanel textPanel = new JPanel();
			textPanel.setLayout(new FlowLayout());
			
			JLabel lblStatus = new JLabel("GeneralStatus: ");
			textPanel.add(lblStatus);
			
			cbxGeneralStatus = new JComboBox();
			GeneralStatus statuses[] = GeneralStatus.values();
			for (int i = 0; i < statuses.length; i++) {
				cbxGeneralStatus.addItem(statuses[i]);
			}
			if(value != null){
				cbxGeneralStatus.setSelectedItem(value);
			}
			
			textPanel.add(cbxGeneralStatus);
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
						if(cbxGeneralStatus.getSelectedIndex() != -1){
							value = (GeneralStatus) cbxGeneralStatus.getSelectedItem();
							okPressed = true;
							dispose();
						} else {
							UIDisplayManager.displayErrorMessage(
									EnumStatusInputDialog.this,
									"GeneralStatus hasn't been selected!\n" +
									"Please select the STATUS from provided box");
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
	
	public GeneralStatus getInput(){
		return value;
	}
	
	public boolean isOkPressed() {
		return okPressed;
	}
}