package vkurman.jbooklibrary.gui.filters;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import vkurman.jbooklibrary.enums.ItemType;
import vkurman.jbooklibrary.interfaces.FilterObserver;
import vkurman.jbooklibrary.interfaces.FilterSubject;

/**
 * Filter that holds combo box with all item types.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class ItemTypeFilterPanel extends JPanel implements FilterSubject {
	
	private static final long serialVersionUID = -6119150490144661069L;
	private JCheckBox chkAll;
	private JComboBox cbxItemType;
	private List<FilterObserver> observers;
	
	/**
	 * Constructor.
	 */
	public ItemTypeFilterPanel(){
		// Creating an ArrayList to hold all observers
		observers = new ArrayList<FilterObserver>();
		
		// Initialising check box for all types
		chkAll = new JCheckBox("All");
		chkAll.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED){
					cbxItemType.setEnabled(false);
					changeType(null);
				} else if(e.getStateChange() == ItemEvent.DESELECTED) {
					cbxItemType.setEnabled(true);
					changeType((ItemType) cbxItemType.getSelectedItem());
				}
			}
		});
		
		// Initialising type combo box
		cbxItemType = new JComboBox();
		ItemType types[] = ItemType.values();
		for (int i = 0; i < types.length; i++) {
			cbxItemType.addItem(types[i]);
		}
		cbxItemType.setSelectedItem(ItemType.BOOK);
		cbxItemType.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				EventQueue.invokeLater(new Runnable() {
					public void run() {
						changeType((ItemType) cbxItemType.getSelectedItem());
					}
				});
			}
		});
		
		showUI();
	}
	
	/**
	 * Creates and displays UI.
	 */
	private void showUI(){
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				"Item Types Filter", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		add(chkAll);
		add(cbxItemType);
	}
	
	@Override
	public void setEnabled(boolean arg){
		chkAll.setEnabled(arg);
		cbxItemType.setEnabled(arg);
	}
	
	@Override
	public void register(FilterObserver observer) {
		observers.add(observer);
	}

	@Override
	public void unregister(FilterObserver observer) {
		observers.remove(observer);
	}

	@Override
	public void notifyObserver(Object status) {
		for(FilterObserver observer : observers){
			observer.update(this.getClass().getSimpleName(), status);
		}
  	}
	
	/**
	 * setStatus method notifies observers about change
	 * @param generalStatus
	 */
	public void changeType(Object arg) {
		notifyObserver(arg);
	}
}