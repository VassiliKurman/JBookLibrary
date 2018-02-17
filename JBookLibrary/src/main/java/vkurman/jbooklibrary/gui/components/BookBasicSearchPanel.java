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





import vkurman.jbooklibrary.core.Book;
import vkurman.jbooklibrary.core.TransObject;
import vkurman.jbooklibrary.gui.BookFinderUI;
import vkurman.jbooklibrary.interfaces.FilterObserver;
import vkurman.jbooklibrary.interfaces.FilterSubject;

/**
 * Book basic search panel.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class BookBasicSearchPanel extends JPanel implements FilterSubject {
	
	private static final long serialVersionUID = 4278349555121352898L;
	private List<FilterObserver> observers;
	private boolean isEnabled;
	private JTextField txtTitle;
	private JButton findButton,
		clearButton;
	
	public BookBasicSearchPanel(boolean isEnabled){
		// Creating an ArrayList to hold all observers
		observers = new ArrayList<FilterObserver>();
		
		this.isEnabled = isEnabled;
		
		showUI();
	}
	
	private void showUI(){
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setBorder(new TitledBorder(
				UIManager.getBorder("TitledBorder.border"),
				"Book Search",
				TitledBorder.LEADING,
				TitledBorder.TOP,
				null,
				null));
		
		add(searchText());
		add(findButton());
		add(clearButton());
	}
	
	private JTextField searchText(){
		txtTitle = new JTextField("");
		txtTitle.setColumns(12);
		txtTitle.setEnabled(isEnabled);
		
		return txtTitle;
	}
	
	private JButton findButton(){
		findButton = new JButton("Find Book");
		findButton.setActionCommand("findBook");
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
		clearButton = new JButton("Clear Book");
		clearButton.setActionCommand("clearBook");
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
					BookFinderUI dialog = new BookFinderUI(b, txtTitle.getText(), null, true);
					
					try {
						if(dialog.isOKPressed()){
							if (((Book) b.getObject()) != null) {
								txtTitle.setText(((Book) b.getObject()).getTitle());
								
								txtTitle.setEnabled(false);
								findButton.setEnabled(false);
								clearButton.setEnabled(true);
								
								// Request Observer update
								changeList((Book) b.getObject());
							} else {
								JOptionPane.showMessageDialog(
										null,
										"Found Book is NULL! \nPlease try to find book again!!!",
										"Book not found error",
										JOptionPane.ERROR_MESSAGE);
							}
						}
					} catch (Exception e) {
						JOptionPane.showMessageDialog(
								null,
								"Error occur while setting book details!",
								"BookBasicSearchPanel error",
								JOptionPane.ERROR_MESSAGE);
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(
							null,
							"Can't display BookFinderUI!",
							"Book Finder error",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
	}
	
	private void clear(){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				txtTitle.setText("");
				txtTitle.setEnabled(true);
				findButton.setEnabled(true);
				clearButton.setEnabled(false);
				
				// Request Observer update
				changeList(null);
			}
		});
	}
	
	@Override
	public void setEnabled(boolean enable){
		isEnabled = enable;
		txtTitle.setText("");
		txtTitle.setEnabled(enable);
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
	public void notifyObserver(Object book) {
		for(FilterObserver observer : observers){
			observer.update(this.getClass().getSimpleName(), book);
		}
  	}
	
	/**
	 * This method notifies observers about change
	 * @param book
	 */
	private void changeList(Book book) {
		notifyObserver(book);
	}
}