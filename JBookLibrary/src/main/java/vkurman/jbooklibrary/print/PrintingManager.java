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