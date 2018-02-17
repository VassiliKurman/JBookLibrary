package vkurman.jbooklibrary.gui.components;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import vkurman.jbooklibrary.core.AdminBooks;
import vkurman.jbooklibrary.core.Book;
import vkurman.jbooklibrary.enums.ItemStatus;

/**
 * Table model for Books table.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class BooksTableModel extends AbstractTableModel {

	private static final long serialVersionUID = -2227671741008288798L;
	private String[] columnNames = {
			"Book ID",
			"Title",
			"Year",
			"Authors",
			"Publisher",
			"Status"};
	private List<Book> books;
	
	/**
	 * Constructor that displays only specified books in
	 * the table.
	 * 
	 * @param books
	 */
	public BooksTableModel(List<Book> books) {
		this.books = new ArrayList<Book>();
		this.books.addAll(books);
	}
	
	/**
	 * Default constructor.
	 */
	public BooksTableModel() {
		this(null, false);
	}
	
	/**
	 * Main constructor.
	 * 
	 * @param status
	 * @param convert
	 */
	public BooksTableModel(ItemStatus status, boolean convert) {
		books = new ArrayList<Book>();
		this.retrieveData(status, convert);
	}
	
	@Override
	public int getColumnCount() {
		return columnNames.length;
	}
	
	@Override
	public int getRowCount() {
		return books.size();
	}
	
	@Override
	public Object getValueAt(int row, int col) {
		Book book = books.get(row);
		switch(col){
			case 0: return book.getBookID();
			case 1:	return book.getTitle();
			case 2:	return book.getYear();
			case 3: return book.getAuthorsAsString();
			case 4:	return book.getPublisher();
			case 5:	return book.getStatus().toString();
			default: return null;
		}
	}
	
	@Override
	public String getColumnName(int col) {
		return columnNames[col];
	}
	
	@Override
	public Class<?> getColumnClass(int c) {
		return (getValueAt(0, c) == null) ? String.class : getValueAt(0, c).getClass();
	}
	
	@Override
	public boolean isCellEditable(int row, int col) {
		return false;
	}
	
	/**
	 * Adds specified book to the table.
	 * 
	 * @param book
	 */
	public void addBook(Book book){
		if(book != null){
			int size = books.size();
			this.books.add(book);
			this.fireTableRowsInserted(size, size);
		}
	}
	
	/**
	 * Returns book from specified row.
	 * 
	 * @param row
	 * @return Book
	 */
	public Book getBook(int row){
		return books.get(row);
	}
	
	/**
	 * Adds list of books to the table.
	 * 
	 * @param books
	 */
	public void addBooks(List<Book> books){
		if(!books.isEmpty()){
			this.books.addAll(books);
			this.fireTableDataChanged();
		}
	}
	
	/**
	 * Removes specified book from table.
	 * 
	 * @param book
	 */
	public void removeBook(Book book){
		if(book != null){
			if (books.contains(book)) {
				int index = books.indexOf(book);
				this.books.remove(book);
				this.fireTableRowsDeleted(index, index);
			}
		}
	}
	
	/**
	 * Retrieves data from books administrator.
	 * 
	 * @param status
	 * @param convert
	 */
	private void retrieveData(ItemStatus status, boolean convert){
		books = AdminBooks.getInstance().getBooks(status, convert);
	}
	
	/**
	 * Replaces data in table with specified list.
	 * 
	 * @param list
	 */
	public void replaceData(List<Book> list){
		if(list != null){
			this.books.clear();
			this.books.addAll(list);
			this.fireTableDataChanged();
		}
	}
	
	/**
	 * Clears current data and retrieves new data.
	 * 
	 * @param status
	 * @param convert
	 */
	public void replaceData(ItemStatus status, boolean convert){
		this.books.clear();
		this.retrieveData(status, convert);
		this.fireTableDataChanged();
	}
}