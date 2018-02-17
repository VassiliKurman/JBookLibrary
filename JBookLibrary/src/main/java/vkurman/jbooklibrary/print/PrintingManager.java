package vkurman.jbooklibrary.print;

import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import vkurman.jbooklibrary.core.IDCard;

public class PrintingManager {

	public static void printIDCard(IDCard card) {

//		PrinterJob job = PrinterJob.getPrinterJob();
//		try{
			IDCardTemplate cardPainter =  new IDCardTemplate(card);
			
//			job.setPrintable(cardPainter);
//			if (job.printDialog()) {
//				try {
//					job.print();
//				} catch (PrinterException e) {
//					e.printStackTrace();
//				}
//			}
//		} catch (Exception e){
//			e.printStackTrace();
//		}
	}
}