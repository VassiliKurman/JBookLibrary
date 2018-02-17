package vkurman.jbooklibrary.gui;

import java.awt.BorderLayout;
import java.awt.Dialog;

import javax.swing.JDialog;
import javax.swing.JFrame;

import vkurman.jbooklibrary.gui.components.BooksPanel;
import vkurman.jbooklibrary.gui.components.CloseButtonPanel;

/**
 * Books administrator UI.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class BookAdminUI extends JDialog {

	private static final long serialVersionUID = -5757525497084426601L;
	private BooksPanel booksPanel;
	
	/**
	 * Constructor.
	 */
	public BookAdminUI() {
		booksPanel = new BooksPanel();
		
		showUI();
	}
	
	/**
	 * Creates and displays UI.
	 */
	private void showUI(){
		setTitle("Book Administration UI");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		
		setLayout(new BorderLayout(5, 5));
		add(booksPanel, BorderLayout.CENTER);
		add(new CloseButtonPanel(this), BorderLayout.PAGE_END);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}