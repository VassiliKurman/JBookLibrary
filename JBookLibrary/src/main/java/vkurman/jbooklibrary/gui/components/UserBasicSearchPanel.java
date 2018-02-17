package vkurman.jbooklibrary.gui.components;

import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;





import vkurman.jbooklibrary.core.TransObject;
import vkurman.jbooklibrary.core.User;
import vkurman.jbooklibrary.gui.UserFinderUI;
import vkurman.jbooklibrary.interfaces.FilterObserver;
import vkurman.jbooklibrary.interfaces.FilterSubject;

/**
 * Panel for user basic search.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class UserBasicSearchPanel extends JPanel implements FilterSubject {
	
	private static final long serialVersionUID = 4278349555121352898L;
	private List<FilterObserver> observers;
	private boolean isEnabled;
	private JTextField txtName;
	private JButton findButton,
		clearButton;
	
	public UserBasicSearchPanel(){
		this(true);
	}
	
	public UserBasicSearchPanel(boolean isEnabled){
		// Creating an ArrayList to hold all observers
		observers = new ArrayList<FilterObserver>();
		
		this.isEnabled = isEnabled;
		
		showUI();
	}
	
	private void showUI(){
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setBorder(new TitledBorder(
				UIManager.getBorder("TitledBorder.border"),
				"User Search",
				TitledBorder.LEADING,
				TitledBorder.TOP,
				null,
				null));
		
		add(searchText());
		add(findButton());
		add(clearButton());
	}
	
	private JTextField searchText(){
		txtName = new JTextField("");
		txtName.setColumns(12);
		txtName.setEnabled(isEnabled);
		
		return txtName;
	}
	
	private JButton findButton(){
		findButton = new JButton("Find User");
		findButton.setActionCommand("findUser");
		findButton.setEnabled(isEnabled);
		findButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				find();
			}
		});
		
		return findButton;
	}
	
	private JButton clearButton(){
		clearButton = new JButton("Clear User");
		clearButton.setActionCommand("clearUser");
		clearButton.setEnabled(false);
		clearButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				clear();
			}
		});
		
		return clearButton;
	}
	
	private void find(){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TransObject b = new TransObject();
					UserFinderUI dialog = new UserFinderUI(b, txtName.getText());
					
					try {
						if(dialog.isOKPressed()){
							if (((User) b.getObject()) != null) {
								txtName.setText(((User) b.getObject()).getName());
								
								txtName.setEnabled(false);
								findButton.setEnabled(false);
								clearButton.setEnabled(true);
								
								// Request Observer update
								changeList((User) b.getObject());
							} else {
								JOptionPane.showMessageDialog(
										null,
										"Found User is NULL! \nPlease try to find user again!!!",
										"User not found error",
										JOptionPane.ERROR_MESSAGE);
							}
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(
								null,
								"Error occur while setting user details!",
								"UserBasicSearchPanel error",
								JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(
							null,
							"Can't display UserFinderUI!",
							"User Finder error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
	
	private void clear(){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				txtName.setText("");
				txtName.setEnabled(true);
				findButton.setEnabled(true);
				clearButton.setEnabled(false);
				
				// Request Observer update
				changeList(null);
			}
		});
	}
	
	/**
	 * This method is reseting components to values and states
	 * that makes it to be ready to perform searching process
	 */
	public void reset(){
		isEnabled = true;
		txtName.setText("");
		txtName.setEnabled(true);
		findButton.setEnabled(true);
		clearButton.setEnabled(false);
	}
	
	@Override
	public void setEnabled(boolean enable){
		isEnabled = enable;
		txtName.setText("");
		txtName.setEnabled(enable);
		findButton.setEnabled(enable);
		clearButton.setEnabled(false);
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
	public void notifyObserver(Object user) {
		for(FilterObserver observer : observers){
			observer.update(this.getClass().getSimpleName(), user);
		}
  	}
	
	/**
	 * This method notifies observers about change
	 * @param user
	 */
	private void changeList(User user) {
		notifyObserver(user);
	}
}