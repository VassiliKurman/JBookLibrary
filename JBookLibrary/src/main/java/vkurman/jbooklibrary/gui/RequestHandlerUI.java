package vkurman.jbooklibrary.gui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpringLayout;

import vkurman.jbooklibrary.core.AdminRequests;
import vkurman.jbooklibrary.core.Request;
import vkurman.jbooklibrary.enums.GeneralStatus;
import vkurman.jbooklibrary.enums.ItemType;
import vkurman.jbooklibrary.utils.SpringUtilities;

/**
 * <code>JDialog</code> that creates request for new item.
 * 
 * <p>
 * Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class RequestHandlerUI extends JDialog {

	/**
	 * Generated serialVersionUID
	 */
	private static final long serialVersionUID = -5507235166647906020L;
	private String TITLE = "New request";
	private int QUANTITY_VALUE = 1;
	private static final int QUANTITY_STEP = 1;
	private static final int QUANTITY_MINIMUM = 1;
	private static final int QUANTITY_MAXIMUM = 200;

	private Request request;
	private JButton okButton, cancelButton;
	private boolean okPressed;
	private JComboBox cbxItemType;
	private SpinnerModel model;
	private JSpinner spinner;
	private JTextField txtISBN;
	private JTextField txtTitle;
	private JTextField txtIssue;
	private JTextField txtYear;
	private JTextField txtAuthor;
	private JTextField txtPublisher;
	private JTextArea txtComments;

	/**
	 * Constructor
	 * 
	 */
	public RequestHandlerUI() {
		this(null);
	}

	public RequestHandlerUI(Request request) {
		if (request != null)
			QUANTITY_VALUE = request.getQuantity();
		this.request = request;
		okPressed = false;

		cbxItemType = new JComboBox();
		ItemType types[] = ItemType.values();
		for (int i = 0; i < types.length; i++) {
			cbxItemType.addItem(types[i]);
		}
		cbxItemType.setSelectedItem(request != null ? request.getType()
				: ItemType.BOOK);

		model = new SpinnerNumberModel(QUANTITY_VALUE, QUANTITY_MINIMUM,
				QUANTITY_MAXIMUM, QUANTITY_STEP);
		spinner = new JSpinner(model);
		spinner.setEditor(new JSpinner.NumberEditor(spinner, "###"));

		txtISBN = new JTextField();
		txtTitle = new JTextField();
		txtIssue = new JTextField();
		txtYear = new JTextField();
		txtAuthor = new JTextField();
		txtPublisher = new JTextField();
		txtComments = new JTextArea(5, 40);

		if (request != null) {
			txtISBN.setText(request.getIsbn());
			txtTitle.setText(request.getTitle());
			txtIssue.setText(request.getIssue());
			txtYear.setText(request.getYear());
			txtAuthor.setText(request.getAuthor());
			txtPublisher.setText(request.getPublisher());
			txtComments.setText(request.getComments());
		}

		showUI();
	}

	/**
	 * Creates and displays GUI.
	 * 
	 */
	private void showUI() {
		setTitle(TITLE);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		setResizable(false);
		setLayout(new BorderLayout(10, 10));

		add(getContentPanel(), BorderLayout.CENTER);
		add(getButtonPane(), BorderLayout.PAGE_END);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	/**
	 * Creates and returns main content panel.
	 * 
	 * @return JPanel
	 */
	private JPanel getContentPanel() {
		JPanel panel = new JPanel(new SpringLayout());

		panel.add(new JLabel("Type:", JLabel.TRAILING));
		panel.add(cbxItemType);
		panel.add(new JLabel("ISBN:", JLabel.TRAILING));
		panel.add(txtISBN);
		panel.add(new JLabel("Title:", JLabel.TRAILING));
		panel.add(txtTitle);
		panel.add(new JLabel("Issue:", JLabel.TRAILING));
		panel.add(txtIssue);
		panel.add(new JLabel("Year:", JLabel.TRAILING));
		panel.add(txtYear);
		panel.add(new JLabel("Author:", JLabel.TRAILING));
		panel.add(txtAuthor);
		panel.add(new JLabel("Publisher:", JLabel.TRAILING));
		panel.add(txtPublisher);
		panel.add(new JLabel("Quantity:", JLabel.TRAILING));
		panel.add(spinner);
		panel.add(new JLabel("Comments:", JLabel.TRAILING));
		panel.add(txtComments);

		// Lay out the panel.
		SpringUtilities.makeCompactGrid(panel, 9, 2, // rows, cols
				6, 6, // initX, initY
				6, 6); // xPad, yPad

		return panel;
	}

	/**
	 * Creates and returns button panel
	 * 
	 * @return JPanel
	 */
	private JPanel getButtonPane() {
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));

		{
			okButton = new JButton("OK");
			okButton.setActionCommand("OK");
			okButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent ev) {
					if (makeRequest())
						dispose();
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
	 * Validating input.
	 * 
	 * @return boolean
	 */
	private boolean makeRequest() {
		Request request = new Request();

		request.setType((ItemType) cbxItemType.getSelectedItem());
		request.setIsbn(txtISBN.getText());
		request.setTitle(txtTitle.getText());
		request.setIssue(txtIssue.getText());
		request.setYear(txtYear.getText());
		request.setAuthor(txtAuthor.getText());
		request.setPublisher(txtPublisher.getText());
		request.setQuantity((Integer) model.getValue());
		request.setComments(txtComments.getText());
		request.setStatus(GeneralStatus.ACTIVE);

		if (AdminRequests.getInstance().newRequest(request)) {
			// Setting request variable
			this.request = request;
			okPressed = true;
			// Displaying confirmation message
			UIDisplayManager.displayInformationMessage(this,
					"Request saved successfully!");
			return true;
		} else {
			UIDisplayManager.displayErrorMessage(this,
					"Error occur while saving new request.");
			return false;
		}
	}

	/**
	 * Returns request.
	 * 
	 * @return Request
	 */
	public Request getRequest() {
		return request;
	}

	/**
	 * Returns <code>true</code> if "OK" button was pressed.
	 * 
	 * @return boolean
	 */
	public boolean isOkPressed() {
		return okPressed;
	}
}