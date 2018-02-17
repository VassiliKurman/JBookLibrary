/*
 * Copyright 2018 Vassili Kurman
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package vkurman.jbooklibrary.gui;

import java.awt.BorderLayout;
import java.awt.Dialog;

import javax.swing.JDialog;
import javax.swing.JFrame;

import vkurman.jbooklibrary.core.Book;
import vkurman.jbooklibrary.gui.components.BookDetailsPanel;
import vkurman.jbooklibrary.gui.components.CloseButtonPanel;

/**
 * Dialog with details about specified book.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class BookDetailsUI extends JDialog {

	private static final long serialVersionUID = 4491812798739284629L;
	private Book book;
	
	/**
	 * Constructor.
	 * 
	 * @param book
	 */
	public BookDetailsUI(Book book) {
		this.book = book;
		
		showUI();
	}
	
	/**
	 * Creates and displays UI.
	 */
	public void showUI(){
		setTitle(book.getTitle());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
		setResizable(false);
		
		add(new BookDetailsPanel(book), BorderLayout.CENTER);
		add(new CloseButtonPanel(this), BorderLayout.PAGE_END);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}