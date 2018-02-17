package vkurman.jbooklibrary.gui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.border.EmptyBorder;

import vkurman.jbooklibrary.core.Address;
import vkurman.jbooklibrary.core.IDProvider;
import vkurman.jbooklibrary.core.TransObject;
import vkurman.jbooklibrary.utils.SpringUtilities;

/**
 * User interface for creating new <code>Address</code> object
 * or editing existing one.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class AddressUI extends JDialog {

	private static final long serialVersionUID = 5791262807475355063L;
	private final JPanel contentPanel = new JPanel();
	private JButton okButton, cancelButton;
	private boolean okPressed;
	private TransObject obj;
	private JTextField flatNumber, houseName, houseNumber, street,
		city, county, postcode, country;

	/**
	 * Constructor.
	 * 
	 * @param obj
	 */
	public AddressUI(TransObject obj) {
		this.obj = obj;
		
		okPressed = false;
		
		flatNumber = new JTextField(20);
		houseName = new JTextField(20);
		houseNumber = new JTextField(20);
		street = new JTextField(20);
		city = new JTextField(20);
		county = new JTextField(20);
		postcode = new JTextField(20);
		country = new JTextField(20);
		
		showUI();
	}
	
	/**
	 * Creates and displays UI.
	 */
	public void showUI(){
		setTitle(obj.getObject() == null ? "New Address" : "Address details");
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		setResizable(false);
		setLayout(new BorderLayout(10, 10));
		
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.setLayout(new BorderLayout(0, 0));
		contentPanel.add(getAddressPanel(), BorderLayout.CENTER);
		
		add(contentPanel, BorderLayout.CENTER);
		add(getButtonPane(), BorderLayout.PAGE_END);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * Creates and returns panel with text fields.
	 * 
	 * @return JPanel
	 */
	private JPanel getAddressPanel() {
		JPanel panel = new JPanel(new SpringLayout());
		
		if(obj.getObject() != null){
			Address address = (Address) obj.getObject();
			
			flatNumber.setText(address.getFlatNumber());
			houseName.setText(address.getHouseName());
			houseNumber.setText(address.getHouseNumber());
			street.setText(address.getStreet());
			city.setText(address.getCity());
			county.setText(address.getCounty());
			postcode.setText(address.getPostcode());
			country.setText(address.getCountry());
		}
		
		panel.add(new JLabel("Flat Number:", JLabel.TRAILING));
		panel.add(flatNumber);
		panel.add(new JLabel("House Name:", JLabel.TRAILING));
		panel.add(houseName);
		panel.add(new JLabel("House Number:", JLabel.TRAILING));
		panel.add(houseNumber);
		panel.add(new JLabel("Street:", JLabel.TRAILING));
		panel.add(street);
		panel.add(new JLabel("City:", JLabel.TRAILING));
		panel.add(city);
		panel.add(new JLabel("County:", JLabel.TRAILING));
		panel.add(county);
		panel.add(new JLabel("Postcode:", JLabel.TRAILING));
		panel.add(postcode);
		panel.add(new JLabel("Country:", JLabel.TRAILING));
		panel.add(country);
		
		// Lay out the panel.
		SpringUtilities.makeCompactGrid(
				panel,
				8, 2,  //rows, cols
				6, 6,  //initX, initY
				6, 6); //xPad, yPad
		
		return panel;
	}
	
	/**
	 * Creates and returns panel with "OK" and "Cancel" buttons.
	 * 
	 * @return JPanel
	 */
	private JPanel getButtonPane(){
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		{
			okButton = new JButton("OK");
			okButton.setActionCommand("OK");
			okButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ev) {
					if(validated()){
						setAddressObject();
						okPressed = true;
						dispose();
					}
				}
			});
			buttonPane.add(okButton);
		}
		{
			cancelButton = new JButton("Cancel");
			cancelButton.setActionCommand("Cancel");
			cancelButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ev) {
					dispose();
				}
			});
			buttonPane.add(cancelButton);
		}
		return buttonPane;
	}
	
	/**
	 * Validates text fields. Fields <code>street</code>, <code>city</code>,
	 * <code>postcode</code> and <code>country</code> should not be empty.
	 *  
	 * @return boolean
	 */
	private boolean validated(){
		boolean b = false;
		if(street.getText().isEmpty() && city.getText().isEmpty() &&
				postcode.getText().isEmpty() && country.getText().isEmpty()){
			
			UIDisplayManager.displayErrorMessage(
					AddressUI.this,
					
					"Error occur while validating entries! \n"
					+ "Please make sure that Street, City, Postcode and Country fields "
					+ "are not empty");
			
		} else {
			b = true;
		}
		return b;
	}
	
	/**
	 * Creates new <code>Address</code> object and passes it
	 * to the <code>TransObject</code>
	 */
	private void setAddressObject(){
		// setting up variable for new Address
		Address address;
		
		if(obj.getObject() != null){
			address = (Address) obj.getObject();
		} else {
			address = new Address(Locale.UK);
			address.setAddressID(IDProvider.getInstance().getAddressNextID());
		}
		
		// setting up data for new address
		{
			address.setFlatNumber(flatNumber.getText());
			address.setHouseName(houseName.getText());
			address.setHouseNumber(houseNumber.getText());
			address.setStreet(street.getText());
			address.setCity(city.getText());
			address.setCounty(county.getText());
			address.setPostcode(postcode.getText());
			address.setCountry(country.getText());
		}
		obj.setObject(address);
	}
	
	/**
	 * Returns <code>true</code> if "OK" buttons pressed.
	 * 
	 * @return boolean
	 */
	public boolean isOKPressed() {
		return this.okPressed;
	}
}