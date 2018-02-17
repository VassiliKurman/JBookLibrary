package vkurman.jbooklibrary.gui.components;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import vkurman.jbooklibrary.core.AdminBooks;
import vkurman.jbooklibrary.core.AdminReservations;
import vkurman.jbooklibrary.core.Book;
import vkurman.jbooklibrary.enums.GeneralStatus;
import vkurman.jbooklibrary.enums.ItemStatus;
import vkurman.jbooklibrary.gui.BigDecimalInputDialog;
import vkurman.jbooklibrary.gui.ListInputDialog;
import vkurman.jbooklibrary.gui.NewLoanUI;
import vkurman.jbooklibrary.gui.NewReservationUI;
import vkurman.jbooklibrary.gui.NumberInputDialog;
import vkurman.jbooklibrary.gui.TextAreaInputDialog;
import vkurman.jbooklibrary.gui.TextInputDialog;
import vkurman.jbooklibrary.gui.UIDisplayManager;
import vkurman.jbooklibrary.utils.BasicLibraryDateFormatter;
import vkurman.jbooklibrary.utils.jbasiccalendar.JBasicCalendarUI;

/**
 * Panel that displays details about specified <code>Book</code>.
 * 
 * <p>
 * Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class BookDetailsPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = -5150816374646174724L;
	private Book book;

	private JButton reserveButton;
	private JButton cancelReservationButton;
	private JButton borrowButton;
	private JButton returnButton;
	private JButton disposeButton;
	private JButton restoreButton;
	private JButton lostButton;

	private JTextArea txtFootnote, txtDescription;
	private JTextField txtTitle, txtSubtitle, txtEdition, txtPagination,
			txtPrice, txtStatus, txtLanguage, txtEditor, txtPublicationPlace,
			txtPublisher, txtISBN, txtFormat, txtLocation, txtPublicationDate,
			txtAuthors, txtSubjectHeadings, txtRecommendedAge, txtCondition,
			txtTranslatedFrom, txtSeries, txtSupplements, txtDateOfEntry,
			txtGenres, txtKeywords;

	private LoansPanel bookLoans;

	public BookDetailsPanel(Book book) {
		this.book = book;

		bookLoans = new LoansPanel(true, false, GeneralStatus.ACTIVE, null,
				book);

		showUI();
	}

	private void showUI() {
		setLayout(new FlowLayout());
		setBorder(new EmptyBorder(5, 5, 5, 5));
		add(getTabbedPanel());
	}

	private JTabbedPane getTabbedPanel() {
		JTabbedPane tabbedPane = new JTabbedPane();

		tabbedPane.addTab("Basic Data", getBasicDataPanel());
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		tabbedPane.addTab("Advanced Data", getAdvancedDataPanel());
		tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

		return tabbedPane;
	}

	private JPanel getBasicDataPanel() {
		JPanel wrapper = new JPanel();
		wrapper.setLayout(new BorderLayout(5, 5));
		wrapper.setBorder(new EmptyBorder(5, 5, 5, 5));

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));
		panel.setBorder(new TitledBorder(UIManager
				.getBorder("TitledBorder.border"), null, TitledBorder.LEADING,
				TitledBorder.TOP, null, null));

		JPanel panelTop = new JPanel();
		panelTop.setLayout(new GridLayout(3, 1, 5, 5));

		panelTop.add(getBookIDPanel());

		JPanel p0 = new JPanel();
		p0.setLayout(new GridLayout(1, 2, 5, 5));
		p0.add(getTitlePanel());
		p0.add(getSubtitlePanel());
		panelTop.add(p0);

		JPanel p1 = new JPanel();
		p1.setLayout(new GridLayout(1, 3, 5, 5));
		p1.add(getEditionPanel());
		p1.add(getPaginationPanel());
		p1.add(getIsbnPanel());
		panelTop.add(p1);

		JPanel panelBottom = new JPanel();
		panelBottom.setLayout(new GridLayout(10, 1, 5, 5));
		JPanel p2 = new JPanel();
		p2.setLayout(new GridLayout(1, 2, 5, 5));
		p2.add(getLanguagePanel());
		p2.add(getTranslatedFromPanel());
		panelBottom.add(p2);

		JPanel p3 = new JPanel();
		p3.setLayout(new GridLayout(1, 3, 5, 5));
		p3.add(getPublicationDatePanel());
		p3.add(getPublicationPlacePanel());
		p3.add(getPublisherPanel());
		panelBottom.add(p3);

		JPanel p7 = new JPanel();
		p7.setLayout(new GridLayout(1, 2, 5, 5));
		p7.add(getSeriesPanel());
		p7.add(getEditorPanel());
		panelBottom.add(p7);

		JPanel p4 = new JPanel();
		p4.setLayout(new GridLayout(1, 3, 5, 5));
		p4.add(getStatusPanel());
		p4.add(getConditionPanel());
		p4.add(getSupplementsPanel());
		panelBottom.add(p4);

		JPanel p5 = new JPanel();
		p5.setLayout(new GridLayout(1, 2, 5, 5));
		p5.add(getDateOfEntryPanel());
		p5.add(getRecommendedAgePanel());
		panelBottom.add(p5);

		JPanel p6 = new JPanel();
		p6.setLayout(new GridLayout(1, 3, 5, 5));
		p6.add(getLocationPanel());
		p6.add(getFormatPanel());
		p6.add(getPricePanel());
		panelBottom.add(p6);

		panelBottom.add(getAuthorsPanel());
		panelBottom.add(getGenresPanel());
		panelBottom.add(getSubjectHeadingsPanel());
		panelBottom.add(getKeywordsPanel());

		JPanel panelMiddle = new JPanel();
		panelMiddle.setLayout(new GridLayout(1, 2, 5, 5));
		panelMiddle.add(getDescriptionPanel());
		panelMiddle.add(getFootNotePanel());

		panel.add(panelBottom, BorderLayout.CENTER);
		panel.add(panelMiddle, BorderLayout.PAGE_END);
		panel.add(panelTop, BorderLayout.PAGE_START);

		wrapper.add(panel, BorderLayout.CENTER);
		wrapper.add(getActionsButtonPane(), BorderLayout.PAGE_END);

		return wrapper;
	}

	private JPanel getAdvancedDataPanel() {
		JPanel wrapper = new JPanel();
		wrapper.setLayout(new BorderLayout(5, 5));
		wrapper.setBorder(new EmptyBorder(5, 5, 5, 5));

		JPanel loansPanel = new JPanel();
		loansPanel.setLayout(new BorderLayout(5, 5));

		loansPanel.add(bookLoans, BorderLayout.CENTER);

		wrapper.add(loansPanel, BorderLayout.CENTER);

		return wrapper;
	}

	private JPanel getBookIDPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));

		JTextField txtBookID = new JTextField(Long.toString(book.getBookID()));
		txtBookID.setEditable(false);
		txtBookID.setColumns(20);

		panel.add(new JLabel("Book ID:"), BorderLayout.LINE_START);
		panel.add(txtBookID, BorderLayout.CENTER);

		return panel;
	}

	private JPanel getTitlePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));

		txtTitle = new JTextField(book.getTitle());
		txtTitle.setEditable(false);
		txtTitle.setColumns(20);

		JButton btnEdit = new JButton("Edit");
		btnEdit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					TextInputDialog dialog = new TextInputDialog("Title", book
							.getTitle());
					if (dialog.isOkPressed()) {
						book.setTitle(dialog.getInput());

						// Updating database
						new SwingWorker<Void, Void>() {
							@Override
							protected Void doInBackground() throws Exception {
								AdminBooks.getInstance().updateBookDB(
										book.getBookID(), "TITLE",
										book.getTitle());
								return null;
							}
						}.execute();

						// Updating UI text field
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									txtTitle.setText(book.getTitle());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		panel.add(new JLabel("Title:"), BorderLayout.LINE_START);
		panel.add(txtTitle, BorderLayout.CENTER);
		panel.add(btnEdit, BorderLayout.LINE_END);

		return panel;
	}

	private JPanel getSubtitlePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));

		txtSubtitle = new JTextField(book.getSubtitle());
		txtSubtitle.setEditable(false);
		txtSubtitle.setColumns(20);

		JButton edit = new JButton("Edit");
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					TextInputDialog dialog = new TextInputDialog("Subtitle",
							book.getSubtitle());
					if (dialog.isOkPressed()) {
						book.setSubtitle(dialog.getInput());

						// Updating database
						new SwingWorker<Void, Void>() {
							@Override
							protected Void doInBackground() throws Exception {
								AdminBooks.getInstance().updateBookDB(
										book.getBookID(), "SUBTITLE",
										book.getSubtitle());
								return null;
							}
						}.execute();

						// Updating UI text field
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									txtSubtitle.setText(book.getSubtitle());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		panel.add(new JLabel("Subtitle:"), BorderLayout.LINE_START);
		panel.add(txtSubtitle, BorderLayout.CENTER);
		panel.add(edit, BorderLayout.LINE_END);

		return panel;
	}

	private JPanel getEditionPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));

		txtEdition = new JTextField(Integer.toString(book.getEdition()));
		txtEdition.setEditable(false);
		txtEdition.setColumns(20);

		JButton edit = new JButton("Edit");
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					NumberInputDialog dialog = new NumberInputDialog(book
							.getEdition(), 1, 200);
					if (dialog.isOkPressed()) {
						book.setEdition(dialog.getInput());

						// Updating database
						new SwingWorker<Void, Void>() {
							@Override
							protected Void doInBackground() throws Exception {
								AdminBooks.getInstance().updateBookDB(
										book.getBookID(), "EDITION",
										book.getEdition());
								return null;
							}
						}.execute();

						// Updating UI text field
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									txtEdition.setText(Integer.toString(book
											.getEdition()));
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		panel.add(new JLabel("Edition:"), BorderLayout.LINE_START);
		panel.add(txtEdition, BorderLayout.CENTER);
		panel.add(edit, BorderLayout.LINE_END);

		return panel;
	}

	private JPanel getPaginationPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));

		txtPagination = new JTextField(Integer.toString(book.getPagination()));
		txtPagination.setEditable(false);
		txtPagination.setColumns(20);

		JButton edit = new JButton("Edit");
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					NumberInputDialog dialog = new NumberInputDialog(book
							.getPagination(), 1, 2000);
					if (dialog.isOkPressed()) {
						book.setPagination(dialog.getInput());

						// Updating database
						new SwingWorker<Void, Void>() {
							@Override
							protected Void doInBackground() throws Exception {
								AdminBooks.getInstance().updateBookDB(
										book.getBookID(), "PAGINATION",
										book.getPagination());
								return null;
							}
						}.execute();

						// Updating UI text field
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									txtPagination.setText(Integer.toString(book
											.getPagination()));
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		panel.add(new JLabel("Pagination:"), BorderLayout.LINE_START);
		panel.add(txtPagination, BorderLayout.CENTER);
		panel.add(edit, BorderLayout.LINE_END);

		return panel;
	}

	private JPanel getPricePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));

		txtPrice = new JTextField(book.getPrice().toString());
		txtPrice.setEditable(false);
		txtPrice.setColumns(20);

		JButton edit = new JButton("Edit");
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					BigDecimalInputDialog dialog = new BigDecimalInputDialog(
							book.getPrice());
					if (dialog.isOkPressed()) {
						book.setPrice(dialog.getInput());
						// Updating database
						new SwingWorker<Void, Void>() {
							@Override
							protected Void doInBackground() throws Exception {
								AdminBooks.getInstance().updateBookDB(
										book.getBookID(), "PRICE",
										book.getPrice().toString());
								return null;
							}
						}.execute();

						// Updating UI text field
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									txtPrice.setText(book.getPrice().toString());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		panel.add(new JLabel("Price:"), BorderLayout.LINE_START);
		panel.add(txtPrice, BorderLayout.CENTER);
		panel.add(edit, BorderLayout.LINE_END);

		return panel;
	}

	private JPanel getStatusPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));

		txtStatus = new JTextField(book.getStatus().toString(), 20);
		txtStatus.setEditable(false);

		restoreButton = new JButton("Restore");
		restoreButton.setEnabled(book.isStatusDisposed() ? true : false);
		restoreButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {

				if (JOptionPane
						.showConfirmDialog(
								null,
								"Are you sure you want restoreButton '"
										+ book.getTitle()
										+ "' book?"
										+ "\nBook status will be changed 'ONSHELF'!",
								"Confirm restoration dialog",
								JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

					book.setStatus(ItemStatus.ONSHELF);

					// Updating database
					new SwingWorker<Void, Void>() {
						@Override
						protected Void doInBackground() throws Exception {
							AdminBooks.getInstance().updateBookDB(
									book.getBookID(), "STATUS",
									book.getStatus().toString());
							return null;
						}
					}.execute();

					// Updating UI text field
					EventQueue.invokeLater(new Runnable() {
						public void run() {
							try {
								// Updating book details on screen
								updateStatusTextField();
								// Updating buttons
								updateButtonsState();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					});
				}
			}
		});

		panel.add(new JLabel("Status:"), BorderLayout.LINE_START);
		panel.add(txtStatus, BorderLayout.CENTER);
		panel.add(restoreButton, BorderLayout.LINE_END);

		return panel;
	}

	private JPanel getLanguagePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));

		txtLanguage = new JTextField(book.getLanguage());
		txtLanguage.setEditable(false);
		txtLanguage.setColumns(20);

		JButton edit = new JButton("Edit");
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					TextInputDialog dialog = new TextInputDialog("Language",
							book.getLanguage());
					if (dialog.isOkPressed()) {
						book.setLanguage(dialog.getInput());

						// Updating database
						new SwingWorker<Void, Void>() {
							@Override
							protected Void doInBackground() throws Exception {
								AdminBooks.getInstance().updateBookDB(
										book.getBookID(), "LANGUAGE",
										book.getLanguage());
								return null;
							}
						}.execute();

						// Updating UI text field
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									txtLanguage.setText(book.getLanguage());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		panel.add(new JLabel("Language:"), BorderLayout.LINE_START);
		panel.add(txtLanguage, BorderLayout.CENTER);
		panel.add(edit, BorderLayout.LINE_END);

		return panel;
	}

	private JPanel getDescriptionPanel() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		txtDescription = new JTextArea(3, 40);
		txtDescription.setText(book.getDescription());
		JScrollPane scrollPane = new JScrollPane(txtDescription);
		txtDescription.setEditable(false);

		JButton edit = new JButton("Edit");
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					TextAreaInputDialog dialog = new TextAreaInputDialog(
							"Description", book.getDescription());
					if (dialog.isOkPressed()) {
						book.setDescription(dialog.getInput());

						// Updating database
						new SwingWorker<Void, Void>() {
							@Override
							protected Void doInBackground() throws Exception {
								AdminBooks.getInstance().updateBookDB(
										book.getBookID(), "DESCRIPTION",
										book.getDescription());
								return null;
							}
						}.execute();

						// Updating UI text field
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									txtDescription.setText(book
											.getDescription());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		panel.add(new JLabel("Description:"));
		panel.add(scrollPane);
		panel.add(edit);

		return panel;
	}

	private JPanel getEditorPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));

		txtEditor = new JTextField(book.getEditor());
		txtEditor.setEditable(false);
		txtEditor.setColumns(20);

		JButton edit = new JButton("Edit");
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					TextInputDialog dialog = new TextInputDialog("Editor", book
							.getEditor());
					if (dialog.isOkPressed()) {
						book.setEditor(dialog.getInput());

						// Updating database
						new SwingWorker<Void, Void>() {
							@Override
							protected Void doInBackground() throws Exception {
								AdminBooks.getInstance().updateBookDB(
										book.getBookID(), "EDITOR",
										book.getEditor());
								return null;
							}
						}.execute();

						// Updating UI text field
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									txtEditor.setText(book.getEditor());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		panel.add(new JLabel("Editor:"), BorderLayout.LINE_START);
		panel.add(txtEditor, BorderLayout.CENTER);
		panel.add(edit, BorderLayout.LINE_END);

		return panel;
	}

	private JPanel getPublicationPlacePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));

		txtPublicationPlace = new JTextField(book.getPublicationPlace());
		txtPublicationPlace.setEditable(false);
		txtPublicationPlace.setColumns(20);

		JButton edit = new JButton("Edit");
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					TextInputDialog dialog = new TextInputDialog(
							"Publication place", book.getPublicationPlace());
					if (dialog.isOkPressed()) {
						book.setPublicationPlace(dialog.getInput());

						// Updating database
						new SwingWorker<Void, Void>() {
							@Override
							protected Void doInBackground() throws Exception {
								AdminBooks.getInstance().updateBookDB(
										book.getBookID(), "PUBLICATIONPLACE",
										book.getPublicationPlace());
								return null;
							}
						}.execute();

						// Updating UI text field
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									txtPublicationPlace.setText(book
											.getPublicationPlace());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		panel.add(new JLabel("Publication Place:"), BorderLayout.LINE_START);
		panel.add(txtPublicationPlace, BorderLayout.CENTER);
		panel.add(edit, BorderLayout.LINE_END);

		return panel;
	}

	private JPanel getPublisherPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));

		txtPublisher = new JTextField(book.getPublisher());
		txtPublisher.setEditable(false);
		txtPublisher.setColumns(20);

		JButton edit = new JButton("Edit");
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					TextInputDialog dialog = new TextInputDialog("Publisher",
							book.getPublisher());
					if (dialog.isOkPressed()) {
						book.setPublisher(dialog.getInput());

						// Updating database
						new SwingWorker<Void, Void>() {
							@Override
							protected Void doInBackground() throws Exception {
								AdminBooks.getInstance().updateBookDB(
										book.getBookID(), "PUBLISHER",
										book.getPublisher());
								return null;
							}
						}.execute();

						// Updating UI text field
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									txtPublisher.setText(book.getPublisher());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		panel.add(new JLabel("Publisher:"), BorderLayout.LINE_START);
		panel.add(txtPublisher, BorderLayout.CENTER);
		panel.add(edit, BorderLayout.LINE_END);

		return panel;
	}

	private JPanel getIsbnPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));

		txtISBN = new JTextField(book.getIsbn());
		txtISBN.setEditable(false);
		txtISBN.setColumns(20);

		JButton edit = new JButton("Edit");
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					TextInputDialog dialog = new TextInputDialog("ISBN", book
							.getIsbn());
					if (dialog.isOkPressed()) {
						book.setIsbn(dialog.getInput());

						// Updating database
						new SwingWorker<Void, Void>() {
							@Override
							protected Void doInBackground() throws Exception {
								AdminBooks.getInstance().updateBookDB(
										book.getBookID(), "ISBN",
										book.getIsbn());
								return null;
							}
						}.execute();

						// Updating UI text field
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									txtISBN.setText(book.getIsbn());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		panel.add(new JLabel("ISBN:"), BorderLayout.LINE_START);
		panel.add(txtISBN, BorderLayout.CENTER);
		panel.add(edit, BorderLayout.LINE_END);

		return panel;
	}

	private JPanel getFormatPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));

		txtFormat = new JTextField(book.getFormat());
		txtFormat.setEditable(false);
		txtFormat.setColumns(20);

		JButton edit = new JButton("Edit");
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					TextInputDialog dialog = new TextInputDialog("Format", book
							.getFormat());
					if (dialog.isOkPressed()) {
						book.setFormat(dialog.getInput());

						// Updating database
						new SwingWorker<Void, Void>() {
							@Override
							protected Void doInBackground() throws Exception {
								AdminBooks.getInstance().updateBookDB(
										book.getBookID(), "FORMAT",
										book.getFormat());
								return null;
							}
						}.execute();

						// Updating UI text field
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									txtFormat.setText(book.getFormat());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		panel.add(new JLabel("Format:"), BorderLayout.LINE_START);
		panel.add(txtFormat, BorderLayout.CENTER);
		panel.add(edit, BorderLayout.LINE_END);

		return panel;
	}

	private JPanel getLocationPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));

		txtLocation = new JTextField(book.getLocation());
		txtLocation.setEditable(false);
		txtLocation.setColumns(20);

		JButton edit = new JButton("Edit");
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					TextInputDialog dialog = new TextInputDialog("Location",
							book.getLocation());
					if (dialog.isOkPressed()) {
						book.setLocation(dialog.getInput());

						// Updating database
						new SwingWorker<Void, Void>() {
							@Override
							protected Void doInBackground() throws Exception {
								AdminBooks.getInstance().updateBookDB(
										book.getBookID(), "LOCATION",
										book.getLocation());
								return null;
							}
						}.execute();

						// Updating UI text field
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									txtLocation.setText(book.getLocation());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		panel.add(new JLabel("Location:"), BorderLayout.LINE_START);
		panel.add(txtLocation, BorderLayout.CENTER);
		panel.add(edit, BorderLayout.LINE_END);

		return panel;
	}

	private JPanel getPublicationDatePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));

		txtPublicationDate = new JTextField(
				(book.getPublicationDate() == null) ? ""
						: BasicLibraryDateFormatter.formatDate(book
								.getPublicationDate()));
		txtPublicationDate.setEditable(false);
		txtPublicationDate.setColumns(20);

		JButton edit = new JButton("Edit");
		edit.setEnabled(true);
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				JBasicCalendarUI dialog = (book.getPublicationDate() == null) ? new JBasicCalendarUI()
						: new JBasicCalendarUI(BookDetailsPanel.this, book
								.getPublicationDate());

				dialog.setVisible(true);
				if (!dialog.isShowing()) {
					if (dialog.isOkPressed()) {
						book.setPublicationDate(dialog.getCalendar());

						// Updating database
						new SwingWorker<Void, Void>() {
							@Override
							protected Void doInBackground() throws Exception {
								AdminBooks.getInstance().updateBookDB(
										book.getBookID(), "PUBLICATIONDATE",
										book.getPublicationDate().getTime());
								return null;
							}
						}.execute();

						// Updating UI text field
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									txtPublicationDate.setText(BasicLibraryDateFormatter
											.formatDate(book
													.getPublicationDate()));
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});
					}
				}
			}
		});

		panel.add(new JLabel("Publication Date:"), BorderLayout.LINE_START);
		panel.add(txtPublicationDate, BorderLayout.CENTER);
		panel.add(edit, BorderLayout.LINE_END);

		return panel;
	}

	private JPanel getAuthorsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));

		txtAuthors = new JTextField(book.getAuthorsAsString(), 20);
		txtAuthors.setEditable(false);

		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(1, 2, 5, 5));

		JButton add = new JButton("Add");
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					TextInputDialog dialog = new TextInputDialog("Authors");
					if (dialog.isOkPressed()) {
						if (!dialog.getInput().equals("")) {
							book.addAuthor(dialog.getInput());
							// Updating database
							new SwingWorker<Void, Void>() {
								@Override
								protected Void doInBackground()
										throws Exception {
									AdminBooks.getInstance().updateBookDB(
											book.getBookID(), "AUTHORS",
											book.getAuthorsAsString());
									return null;
								}
							}.execute();
							// Updating UI text field
							EventQueue.invokeLater(new Runnable() {
								public void run() {
									try {
										txtAuthors.setText(book
												.getAuthorsAsString());
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							});
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		JButton remove = new JButton("Remove");
		remove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					ListInputDialog dialog = new ListInputDialog("Authors",
							book.getAuthors());
					if (dialog.isOkPressed()) {
						book.removeAuthor(dialog.getInput());

						// Updating database
						new SwingWorker<Void, Void>() {
							@Override
							protected Void doInBackground() throws Exception {
								AdminBooks.getInstance().updateBookDB(
										book.getBookID(), "AUTHORS",
										book.getAuthorsAsString());
								return null;
							}
						}.execute();

						// Updating UI text field
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									txtAuthors.setText(book
											.getAuthorsAsString());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		buttons.add(add);
		buttons.add(remove);

		panel.add(new JLabel("Authors:"), BorderLayout.LINE_START);
		panel.add(txtAuthors, BorderLayout.CENTER);
		panel.add(buttons, BorderLayout.LINE_END);

		return panel;
	}

	private JPanel getSubjectHeadingsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));

		txtSubjectHeadings = new JTextField(book.getSubjectHeadingsAsString(),
				20);
		txtSubjectHeadings.setEditable(false);

		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(1, 2, 5, 5));

		JButton add = new JButton("Add");
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					TextInputDialog dialog = new TextInputDialog(
							"Subject headings");
					if (dialog.isOkPressed()) {
						if (!dialog.getInput().equals("")) {
							book.addSubjectHeading(dialog.getInput());
							// Updating database
							new SwingWorker<Void, Void>() {
								@Override
								protected Void doInBackground()
										throws Exception {
									AdminBooks.getInstance().updateBookDB(
											book.getBookID(), "SUBHEADINGS",
											book.getSubjectHeadingsAsString());
									return null;
								}
							}.execute();
							// Updating UI text field
							EventQueue.invokeLater(new Runnable() {
								public void run() {
									try {
										txtSubjectHeadings.setText(book
												.getSubjectHeadingsAsString());
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							});
						}

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		JButton remove = new JButton("Remove");
		remove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					ListInputDialog dialog = new ListInputDialog(
							"Subject headings", book.getSubjectHeadings());
					if (dialog.isOkPressed()) {
						book.removeSubjectHeading(dialog.getInput());

						// Updating database
						new SwingWorker<Void, Void>() {
							@Override
							protected Void doInBackground() throws Exception {
								AdminBooks.getInstance().updateBookDB(
										book.getBookID(), "SUBHEADINGS",
										book.getSubjectHeadingsAsString());
								return null;
							}
						}.execute();

						// Updating UI text field
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									txtSubjectHeadings.setText(book
											.getSubjectHeadingsAsString());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		buttons.add(add);
		buttons.add(remove);

		panel.add(new JLabel("Subject Headings:"), BorderLayout.LINE_START);
		panel.add(txtSubjectHeadings, BorderLayout.CENTER);
		panel.add(buttons, BorderLayout.LINE_END);

		return panel;
	}

	private JPanel getRecommendedAgePanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));

		txtRecommendedAge = new JTextField(Integer.toString(book
				.getRecommendedAge()));
		txtRecommendedAge.setEditable(false);
		txtRecommendedAge.setColumns(20);

		JButton edit = new JButton("Edit");
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					NumberInputDialog dialog = new NumberInputDialog(book
							.getRecommendedAge(), 1, 100);
					if (dialog.isOkPressed()) {
						book.setRecommendedAge(dialog.getInput());

						// Updating database
						new SwingWorker<Void, Void>() {
							@Override
							protected Void doInBackground() throws Exception {
								AdminBooks.getInstance().updateBookDB(
										book.getBookID(), "RECOMMENDEDAGE",
										book.getRecommendedAge());
								return null;
							}
						}.execute();

						// Updating UI text field
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									txtRecommendedAge.setText(Integer
											.toString(book.getRecommendedAge()));
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		panel.add(new JLabel("Recommended Age:"), BorderLayout.LINE_START);
		panel.add(txtRecommendedAge, BorderLayout.CENTER);
		panel.add(edit, BorderLayout.LINE_END);

		return panel;
	}

	private JPanel getConditionPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));

		txtCondition = new JTextField(book.getCondition());
		txtCondition.setEditable(false);
		txtCondition.setColumns(20);

		JButton edit = new JButton("Edit");
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					TextInputDialog dialog = new TextInputDialog(
							"Item condition", book.getCondition());
					if (dialog.isOkPressed()) {
						book.setCondition(dialog.getInput());

						// Updating database
						new SwingWorker<Void, Void>() {
							@Override
							protected Void doInBackground() throws Exception {
								AdminBooks.getInstance().updateBookDB(
										book.getBookID(), "CONDITION",
										book.getCondition());
								return null;
							}
						}.execute();

						// Updating UI text field
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									txtCondition.setText(book.getCondition());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		panel.add(new JLabel("Condition:"), BorderLayout.LINE_START);
		panel.add(txtCondition, BorderLayout.CENTER);
		panel.add(edit, BorderLayout.LINE_END);

		return panel;
	}

	private JPanel getTranslatedFromPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));

		txtTranslatedFrom = new JTextField(book.getTranslatedFrom());
		txtTranslatedFrom.setEditable(false);
		txtTranslatedFrom.setColumns(20);

		JButton edit = new JButton("Edit");
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					TextInputDialog dialog = new TextInputDialog(
							"Translated from", book.getTranslatedFrom());
					if (dialog.isOkPressed()) {
						book.setTranslatedFrom(dialog.getInput());

						// Updating database
						new SwingWorker<Void, Void>() {
							@Override
							protected Void doInBackground() throws Exception {
								AdminBooks.getInstance().updateBookDB(
										book.getBookID(), "TRANSLATEDFROM",
										book.getTranslatedFrom());
								return null;
							}
						}.execute();

						// Updating UI text field
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									txtTranslatedFrom.setText(book
											.getTranslatedFrom());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		panel.add(new JLabel("Translated From:"), BorderLayout.LINE_START);
		panel.add(txtTranslatedFrom, BorderLayout.CENTER);
		panel.add(edit, BorderLayout.LINE_END);

		return panel;
	}

	private JPanel getSeriesPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));

		txtSeries = new JTextField(book.getSeries());
		txtSeries.setEditable(false);
		txtSeries.setColumns(20);

		JButton edit = new JButton("Edit");
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					TextInputDialog dialog = new TextInputDialog("Series", book
							.getSeries());
					if (dialog.isOkPressed()) {
						book.setSeries(dialog.getInput());

						// Updating database
						new SwingWorker<Void, Void>() {
							@Override
							protected Void doInBackground() throws Exception {
								AdminBooks.getInstance().updateBookDB(
										book.getBookID(), "SERIES",
										book.getSeries());
								return null;
							}
						}.execute();

						// Updating UI text field
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									txtSeries.setText(book.getSeries());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		panel.add(new JLabel("Series:"), BorderLayout.LINE_START);
		panel.add(txtSeries, BorderLayout.CENTER);
		panel.add(edit, BorderLayout.LINE_END);

		return panel;
	}

	private JPanel getSupplementsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));

		txtSupplements = new JTextField(book.getSupplements());
		txtSupplements.setEditable(false);
		txtSupplements.setColumns(20);

		JButton edit = new JButton("Edit");
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					TextInputDialog dialog = new TextInputDialog("Supplements",
							book.getSupplements());
					if (dialog.isOkPressed()) {
						book.setSupplements(dialog.getInput());

						// Updating database
						new SwingWorker<Void, Void>() {
							@Override
							protected Void doInBackground() throws Exception {
								AdminBooks.getInstance().updateBookDB(
										book.getBookID(), "SUPPLEMENTS",
										book.getSupplements());
								return null;
							}
						}.execute();

						// Updating UI text field
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									txtSupplements.setText(book
											.getSupplements());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		panel.add(new JLabel("Supplements:"), BorderLayout.LINE_START);
		panel.add(txtSupplements, BorderLayout.CENTER);
		panel.add(edit, BorderLayout.LINE_END);

		return panel;
	}

	private JPanel getFootNotePanel() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		txtFootnote = new JTextArea(3, 40);
		txtFootnote.setText(book.getFootNote());
		JScrollPane scrollPane = new JScrollPane(txtFootnote);
		txtFootnote.setEditable(false);

		JButton edit = new JButton("Edit");
		edit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					TextAreaInputDialog dialog = new TextAreaInputDialog(
							"Footnote", book.getFootNote());
					if (dialog.isOkPressed()) {
						book.setFootNote(dialog.getInput());

						// Updating database
						new SwingWorker<Void, Void>() {
							@Override
							protected Void doInBackground() throws Exception {
								AdminBooks.getInstance().updateBookDB(
										book.getBookID(), "FOOTNOTE",
										book.getFootNote());
								return null;
							}
						}.execute();

						// Updating UI text field
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									txtFootnote.setText(book.getFootNote());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		panel.add(new JLabel("Footnote:"));
		panel.add(scrollPane);
		panel.add(edit);

		return panel;
	}

	private JPanel getDateOfEntryPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));

		txtDateOfEntry = new JTextField(
				BasicLibraryDateFormatter.formatDateExtended(book
						.getDateOfEntry()));
		txtDateOfEntry.setEditable(false);
		txtDateOfEntry.setColumns(20);

		panel.add(new JLabel("Date Of Entry:"), BorderLayout.LINE_START);
		panel.add(txtDateOfEntry, BorderLayout.CENTER);

		return panel;
	}

	private JPanel getGenresPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));

		txtGenres = new JTextField(book.getGenresAsString());
		txtGenres.setEditable(false);
		txtGenres.setColumns(20);

		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(1, 2, 5, 5));

		JButton add = new JButton("Add");
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					TextInputDialog dialog = new TextInputDialog("Genres");
					if (dialog.isOkPressed()) {
						if (!dialog.getInput().equals("")) {
							book.addGenre(dialog.getInput());
							// Updating database
							new SwingWorker<Void, Void>() {
								@Override
								protected Void doInBackground()
										throws Exception {
									AdminBooks.getInstance().updateBookDB(
											book.getBookID(), "GENRES",
											book.getGenresAsString());
									return null;
								}
							}.execute();
							// Updating UI text field
							EventQueue.invokeLater(new Runnable() {
								public void run() {
									try {
										txtGenres.setText(book
												.getGenresAsString());
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							});
						}

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		JButton remove = new JButton("Remove");
		remove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					ListInputDialog dialog = new ListInputDialog("Genres", book
							.getGenres());
					if (dialog.isOkPressed()) {
						book.removeGenre(dialog.getInput());

						// Updating database
						new SwingWorker<Void, Void>() {
							@Override
							protected Void doInBackground() throws Exception {
								AdminBooks.getInstance().updateBookDB(
										book.getBookID(), "GENRES",
										book.getGenresAsString());
								return null;
							}
						}.execute();

						// Updating UI text field
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									txtGenres.setText(book.getGenresAsString());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		buttons.add(add);
		buttons.add(remove);

		panel.add(new JLabel("Genres:"), BorderLayout.LINE_START);
		panel.add(txtGenres, BorderLayout.CENTER);
		panel.add(buttons, BorderLayout.LINE_END);

		return panel;
	}

	private JPanel getKeywordsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(5, 5));

		txtKeywords = new JTextField(book.getKeywordsAsString());
		txtKeywords.setEditable(false);
		txtKeywords.setColumns(20);

		JPanel buttons = new JPanel();
		buttons.setLayout(new GridLayout(1, 2, 5, 5));

		JButton add = new JButton("Add");
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					TextInputDialog dialog = new TextInputDialog("Keywords");
					if (dialog.isOkPressed()) {
						if (!dialog.getInput().equals("")) {
							book.addKeyword(dialog.getInput());
							// Updating database
							new SwingWorker<Void, Void>() {
								@Override
								protected Void doInBackground()
										throws Exception {
									AdminBooks.getInstance().updateBookDB(
											book.getBookID(), "KEYWORDS",
											book.getKeywordsAsString());
									return null;
								}
							}.execute();
							// Updating UI text field
							EventQueue.invokeLater(new Runnable() {
								public void run() {
									try {
										txtKeywords.setText(book
												.getKeywordsAsString());
									} catch (Exception e) {
										e.printStackTrace();
									}
								}
							});
						}

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		JButton remove = new JButton("Remove");
		remove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					ListInputDialog dialog = new ListInputDialog("Keywords",
							book.getKeywords());
					if (dialog.isOkPressed()) {
						book.removeKeyword(dialog.getInput());

						// Updating database
						new SwingWorker<Void, Void>() {
							@Override
							protected Void doInBackground() throws Exception {
								AdminBooks.getInstance().updateBookDB(
										book.getBookID(), "KEYWORDS",
										book.getKeywordsAsString());
								return null;
							}
						}.execute();

						// Updating UI text field
						EventQueue.invokeLater(new Runnable() {
							public void run() {
								try {
									txtKeywords.setText(book
											.getKeywordsAsString());
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						});

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		buttons.add(add);
		buttons.add(remove);

		panel.add(new JLabel("Keywords:"), BorderLayout.LINE_START);
		panel.add(txtKeywords, BorderLayout.CENTER);
		panel.add(buttons, BorderLayout.LINE_END);

		return panel;
	}

	private JPanel getActionsButtonPane() {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		reserveButton = new JButton("Reserve");
		reserveButton.setActionCommand("reserve");
		reserveButton.setEnabled(false);
		reserveButton.addActionListener(this);

		cancelReservationButton = new JButton("Cancel Reservation");
		cancelReservationButton.setActionCommand("cancel reservation");
		cancelReservationButton.setEnabled(false);
		cancelReservationButton.addActionListener(this);

		borrowButton = new JButton("Borrow");
		borrowButton.setActionCommand("borrow");
		borrowButton.setEnabled(false);
		borrowButton.addActionListener(this);

		returnButton = new JButton("Return");
		returnButton.setActionCommand("return");
		returnButton.setEnabled(false);
		returnButton.addActionListener(this);

		disposeButton = new JButton("Dispose");
		disposeButton.setActionCommand("dispose");
		disposeButton.setEnabled(false);
		disposeButton.addActionListener(this);
		
		lostButton = new JButton("Lost");
		lostButton.setActionCommand("lost");
		lostButton.setEnabled(false);
		lostButton.addActionListener(this);

		panel.add(reserveButton);
		panel.add(cancelReservationButton);
		panel.add(borrowButton);
		panel.add(returnButton);
		panel.add(disposeButton);
		panel.add(lostButton);

		this.updateButtonsState();

		return panel;
	}

	private void lostBook() {
		try {
			if (!book.isStatusDisposed() && !book.isStatusUnknown()) {
				book.setStatus(ItemStatus.UNKNOWN);

				// Updating database
				new SwingWorker<Void, Void>() {
					@Override
					protected Void doInBackground() throws Exception {
						if (AdminBooks.getInstance().updateBookDB(
								book.getBookID(), "STATUS",
								book.getStatus().toString())) {
							UIDisplayManager.displayInformationMessage(
									BookDetailsPanel.this,
									"Book status changed to UNKNOWN!");

							// Updating book details on screen
							updateStatusTextField();
						}
						return null;
					}
				}.execute();
			} else {
				UIDisplayManager
						.displayErrorMessage(BookDetailsPanel.this,
								"Cannot change status for already DISPOSED or UNKNOWN book!");
			}
			// Updating buttons
			updateButtonsState();
		} catch (Exception e) {
			UIDisplayManager.displayErrorMessage(BookDetailsPanel.this,
					"Can't change book status to UNKNOWN!");
		}
	}

	private void reserveBook() {
		try {
			NewReservationUI dialog = new NewReservationUI(book);
			if (!dialog.isShowing()) {
				// Updating book details on screen
				updateStatusTextField();
				// Updating buttons details on screen
				updateButtonsState();
			}
		} catch (Exception e) {
			UIDisplayManager.displayErrorMessage(BookDetailsPanel.this,
					"Can't reserve book!");
		}
	}

	private void cancelReservation() {
		int n = JOptionPane.showConfirmDialog(null,
				"Are you sure you want cancel '" + book.getTitle()
						+ "' reservation?", "Confirm cancelation dialog",
				JOptionPane.YES_NO_OPTION);
		if (n == JOptionPane.YES_OPTION) {
			try {
				if (AdminReservations.getInstance().remove(book)) {
					// Updating book details on screen
					updateStatusTextField();

					// Updating buttons UI
					updateButtonsState();

					// Displaying confirmation dialog
					UIDisplayManager.displayInformationMessage(
							BookDetailsPanel.this, "Reservation for book "
									+ book.getTitle() + " cancelled!");
				}
			} catch (Exception e) {
				UIDisplayManager.displayErrorMessage(BookDetailsPanel.this,
						"Can't cancel Reservation!");
			}
		}
	}

	private void borrowBook() {
		try {
			NewLoanUI dialog = new NewLoanUI(book);
			if (dialog.isOkPressed()) {
				// Updating book details on screen
				updateStatusTextField();

				// Updating book loan details on screen
				bookLoans.refreshTable();

				// Updating buttons
				updateButtonsState();
			}
		} catch (Exception e) {
			UIDisplayManager.displayErrorMessage(BookDetailsPanel.this,
					"Can't borrow book!");
		}
	}

	private void returnBook() {
		try {
			if (AdminBooks.getInstance().returnBook(book)) {
				UIDisplayManager.displayInformationMessage(
						BookDetailsPanel.this, "Book " + book.getTitle()
								+ " returned!");

				// Updating book details on screen
				updateStatusTextField();

				// Updating book loan details on screen
				bookLoans.refreshTable();

				// Updating buttons
				updateButtonsState();
			}
		} catch (Exception e) {
			UIDisplayManager.displayErrorMessage(BookDetailsPanel.this,
					"Can't return book!");
		}
	}

	private void disposeBook() {
		try {
			if (book.isStatusOnShelf() || book.isStatusUnknown()) {
				book.setStatus(ItemStatus.DISPOSED);

				// Updating database
				new SwingWorker<Void, Void>() {
					@Override
					protected Void doInBackground() throws Exception {
						if (AdminBooks.getInstance().updateBookDB(
								book.getBookID(), "STATUS",
								book.getStatus().toString())) {
							UIDisplayManager.displayInformationMessage(
									BookDetailsPanel.this,
									"Book status changed to DISPOSED!");

							// Updating book details on screen
							updateStatusTextField();
						}
						return null;
					}
				}.execute();
			} else {
				UIDisplayManager
						.displayErrorMessage(BookDetailsPanel.this,
								"Book status needs to be set to ONSHELF or UNKNOWN before disposing!");
			}
			// Updating buttons
			updateButtonsState();
		} catch (Exception e) {
			UIDisplayManager.displayErrorMessage(BookDetailsPanel.this,
					"Can't change book status to DISPOSED!");
		}
	}

	private void updateButtonsState() {
		// Checking if book reserved or disposed
		ItemStatus status = book.getStatus();
		switch (status) {
		case RESERVED:
			// Updating UI buttons properties
			reserveButton.setEnabled(false);
			cancelReservationButton.setEnabled(true);
			borrowButton.setEnabled(true);
			returnButton.setEnabled(false);
			disposeButton.setEnabled(true);
			lostButton.setEnabled(true);
			restoreButton.setEnabled(false);
			break;
		case ONLOAN:
			// Updating UI buttons properties
			reserveButton.setEnabled(true);
			cancelReservationButton.setEnabled(true);
			borrowButton.setEnabled(false);
			returnButton.setEnabled(true);
			disposeButton.setEnabled(true);
			lostButton.setEnabled(true);
			restoreButton.setEnabled(false);
			break;
		case ONSHELF:
			// Updating UI buttons properties
			reserveButton.setEnabled(true);
			cancelReservationButton.setEnabled(false);
			borrowButton.setEnabled(true);
			returnButton.setEnabled(false);
			disposeButton.setEnabled(true);
			lostButton.setEnabled(true);
			restoreButton.setEnabled(false);
			break;
		case DISPOSED:
			// Updating UI buttons properties
			reserveButton.setEnabled(false);
			cancelReservationButton.setEnabled(false);
			borrowButton.setEnabled(false);
			returnButton.setEnabled(false);
			disposeButton.setEnabled(false);
			lostButton.setEnabled(false);
			restoreButton.setEnabled(true);
			break;
		case UNKNOWN:
			// Updating UI buttons properties
			reserveButton.setEnabled(false);
			cancelReservationButton.setEnabled(false);
			borrowButton.setEnabled(false);
			returnButton.setEnabled(false);
			disposeButton.setEnabled(true);
			lostButton.setEnabled(false);
			restoreButton.setEnabled(true);
			break;
		default:
			// Updating UI buttons properties
			reserveButton.setEnabled(false);
			cancelReservationButton.setEnabled(false);
			borrowButton.setEnabled(false);
			returnButton.setEnabled(false);
			disposeButton.setEnabled(false);
			lostButton.setEnabled(false);
			restoreButton.setEnabled(false);
			break;
		}
	}

	private void updateStatusTextField() {
		// Updating UI buttons properties
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				txtStatus.setText(book.getStatus().toString());
			}
		});
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String action = e.getActionCommand();
		if(action.equalsIgnoreCase("reserve")){
			reserveBook();
		} else if(action.equalsIgnoreCase("cancel reservation")){
			cancelReservation();
		} else if(action.equalsIgnoreCase("borrow")){
			borrowBook();
		} else if(action.equalsIgnoreCase("return")){
			returnBook();
		} else if(action.equalsIgnoreCase("dispose")){
			disposeBook();
		} else if(action.equalsIgnoreCase("lost")){
			lostBook();
		}
	}
}