package vkurman.jbooklibrary.print;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import vkurman.jbooklibrary.core.AdminPrefs;
import vkurman.jbooklibrary.core.IDCard;
import vkurman.jbooklibrary.utils.BasicLibraryDateFormatter;

public class IDCardTemplate extends JDialog implements Printable {

	public static final double SCREEN_SCALE = 1;
	public static final double PRINT_SCALE = 1;
	public static final int CARD_WIDTH = 320;
	public static final int CARD_HEIGHT = 200;
	public static final int CARD_PADDING_TOP = 5;
	public static final int CARD_PADDING_BOTTOM = 5;
	public static final int CARD_PADDING_LEFT = 5;
	public static final int CARD_PADDING_RIGHT = 5;
	
	public static final int PHOTO_WIDTH = 4;
	public static final int PHOTO_HEIGHT = 6;
	public static final int PHOTO_SCALE = 20;
	
	public static final Color CARD_BORDER_COLOR = Color.BLACK;
	
	private JPanel content;
	private JPanel photo;
	private JLabel title;
	private JLabel name;
	private JLabel date;
	
	public IDCardTemplate(IDCard card) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setModalityType(ModalityType.APPLICATION_MODAL);
		setResizable(false);
		
		photo = new JPanel();
		photo.setPreferredSize(new Dimension((int)(PHOTO_WIDTH * PHOTO_SCALE), (int)(PHOTO_HEIGHT * PHOTO_SCALE)));
		photo.setMinimumSize(new Dimension((int)(PHOTO_WIDTH * PHOTO_SCALE), (int)(PHOTO_HEIGHT * PHOTO_SCALE)));
		photo.setMaximumSize(new Dimension((int)(PHOTO_WIDTH * PHOTO_SCALE), (int)(PHOTO_HEIGHT * PHOTO_SCALE)));
		photo.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(
						(int)(CARD_PADDING_TOP * SCREEN_SCALE),
						(int)(CARD_PADDING_LEFT * SCREEN_SCALE),
						(int)(CARD_PADDING_BOTTOM * SCREEN_SCALE),
						(int)(CARD_PADDING_RIGHT * SCREEN_SCALE)),
				BorderFactory.createLineBorder(CARD_BORDER_COLOR)));
		
		title = new JLabel(AdminPrefs.LIBRARY_NAME);
		name = new JLabel("NAME HERE");
		date = new JLabel("EXPIRE DATE HERE");
		
		if(card != null){
			name.setText(card.getUserName());
			date.setText("Card expires: " + BasicLibraryDateFormatter.formatDate((card.getValidTo())));
		}
		
		init();
	}

	private void init() {
		content = new JPanel(new GridBagLayout());
		content.setPreferredSize(new Dimension((int)(CARD_WIDTH * SCREEN_SCALE), (int)(CARD_HEIGHT * SCREEN_SCALE)));
		content.setMinimumSize(new Dimension((int)(CARD_WIDTH * SCREEN_SCALE), (int)(CARD_HEIGHT * SCREEN_SCALE)));
		content.setMaximumSize(new Dimension((int)(CARD_WIDTH * SCREEN_SCALE), (int)(CARD_HEIGHT * SCREEN_SCALE)));
		
		content.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(
						(int)(CARD_PADDING_TOP * SCREEN_SCALE),
						(int)(CARD_PADDING_LEFT * SCREEN_SCALE),
						(int)(CARD_PADDING_BOTTOM * SCREEN_SCALE),
						(int)(CARD_PADDING_RIGHT * SCREEN_SCALE)),
				BorderFactory.createLineBorder(CARD_BORDER_COLOR)));
		
		GridBagConstraints gc = new GridBagConstraints();
		
		
		
		////////////First row ///////////////////////////
		gc.gridy = 0;
		
		gc.gridheight = GridBagConstraints.RELATIVE;
		gc.gridwidth = 1;
		gc.insets = new Insets(5, 5, 5, 5);
		gc.fill = GridBagConstraints.NONE;
		
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		content.add(photo, gc);
		
		gc.gridx = 1;
		gc.gridheight = 1;
		gc.gridwidth = 1;
		gc.insets = new Insets(5, 5, 5, 5);
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		content.add(title, gc);
		
		////////////Next row ///////////////////////////
		
		gc.gridy++;
		content.add(name, gc);
		
		////////////Next row ///////////////////////////
		
		gc.gridy++;
		content.add(date, gc);
		
		/////////////////////////////////////////////////
		
		
		setContentPane(content);
		
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public JPanel getContent(){
		return content;
	}
	
	@Override
	public void printAll(Graphics g){
		content.printAll(g);
	}

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
			throws PrinterException {
		if (pageIndex > 0) {
	        return NO_SUCH_PAGE;
	    }

	    Graphics2D g2d = (Graphics2D)graphics;
	    g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

	    content.printAll(graphics);

	    return PAGE_EXISTS;
	}

//	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
//			throws PrinterException {
//		if (pageIndex > 0) {
//			return NO_SUCH_PAGE;
//		}
//		
//		Paper paper = new Paper();
//		paper.setSize(width, height);
//		paper.setImageableArea(0, 0, width, height);
//		pageFormat.setPaper(paper);
//		pageFormat.setOrientation(PageFormat.LANDSCAPE);
//
//		Graphics2D g2d = (Graphics2D) graphics;
//
//		g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
//
//		graphics.setColor(Color.BLACK);
//		graphics.drawRect(
//				(int)pageFormat.getImageableX(),
//				(int)pageFormat.getImageableY(),
//				(int)pageFormat.getImageableWidth(),
//				(int)pageFormat.getImageableHeight());
//		graphics.drawString(card.getUserName(), (int) pageFormat.getImageableX() + 25,
//				(int) pageFormat.getImageableY() + 25);
//
//		return PAGE_EXISTS;
//	}
}