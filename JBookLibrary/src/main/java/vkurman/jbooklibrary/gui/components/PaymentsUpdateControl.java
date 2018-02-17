package vkurman.jbooklibrary.gui.components;

/**
 * Control class for payments to update view.
 * 
 * <p>Date created: 2013.07.28
 * 
 * @author Vassili Kurman
 * @version 0.1
 */
public class PaymentsUpdateControl {
	
	private FinesPanel fines;
	private PaymentsPanel payments;
	
	public void addAction(Object input) {
		if (fines != null && payments != null) {
			payments.addPayment(input);
		}
	}

	public void setFines(FinesPanel fines) {
		this.fines = fines;
	}

	public void setPayments(PaymentsPanel payments) {
		this.payments = payments;
	}
}