package vkurman.jbooklibrary.gui;

import java.awt.BorderLayout;
import java.awt.Dialog;

import javax.swing.JDialog;

import vkurman.jbooklibrary.core.AdminPrefs;
import vkurman.jbooklibrary.gui.components.CloseButtonPanel;
import vkurman.jbooklibrary.gui.components.StatisticsPanel;

/**
 * Dialog that displays basic statistical information.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class StatisticsUI extends JDialog {
	private static final long serialVersionUID = -2797563505877565443L;
	
	/**
	 * Constructor.
	 * 
	 * @param parent
	 */
	public StatisticsUI() {
		showUI();
	}
	
	/**
	 * Creates and displays UI.
	 * 
	 * @param parent
	 */
	private void showUI(){
		setTitle("Library Statistics - " + AdminPrefs.LIBRARY_NAME);
		getContentPane().setLayout(new BorderLayout(10, 10));
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		add(new StatisticsPanel(), BorderLayout.CENTER);
		add(new CloseButtonPanel(this), BorderLayout.PAGE_END);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}