package vkurman.jbooklibrary.gui;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import vkurman.jbooklibrary.core.AdminPrefs;
import vkurman.jbooklibrary.gui.components.CloseButtonPanel;

/**
 * <code>JDiaog</code> that displays information about author
 * of this application.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class AboutUI extends JDialog {
	private static final long serialVersionUID = -4256737236599752969L;
	private static final Dimension MINIMUM_SIZE = new Dimension(432, 217);
	private static final Dimension PREFERRED_SIZE = new Dimension(432, 217);
	private static final Dimension MAXIMUM_SIZE = new Dimension(432, 217);
	
	/**
	 * Constructor.
	 * 
	 * @param parent
	 */
	public AboutUI() {
		showUI();
	}
	
	/**
	 * Creates and displays UI.
	 * 
	 * @param parent
	 */
	private void showUI(){
		setResizable(false);
		setTitle("About - " + AdminPrefs.LIBRARY_NAME);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setMinimumSize(MINIMUM_SIZE);
		setPreferredSize(PREFERRED_SIZE);
		setMaximumSize(MAXIMUM_SIZE);
		
		setLayout(new BorderLayout(5, 5));
		
		add(getContentPanel(), BorderLayout.CENTER);
		add(new CloseButtonPanel(this), BorderLayout.PAGE_END);
		
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	/**
	 * Creates and returns content panel for dialog.
	 * 
	 * @return JPanel
	 */
	private JPanel getContentPanel(){
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		panel.add(getTextPanel());
		
		return panel;
	}
	
	/**
	 * Creates and returns panel with text about application.
	 * 
	 * @return JPanel
	 */
	private JPanel getTextPanel(){
		JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
			
		panel.add(new JLabel(AdminPrefs.APPLICATION_NAME));
		panel.add(new JLabel("Version: " + AdminPrefs.VERSION));
		panel.add(new JLabel("Developed by: " + AdminPrefs.AUTHOR));
		panel.add(new JLabel(AdminPrefs.YEAR));
		
		return panel;
	}
}