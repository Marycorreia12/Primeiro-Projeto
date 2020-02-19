package model.services;

import java.text.SimpleDateFormat;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService {

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm"); 
	
	private Double pricePerDay;
	private Double pricePerHour;
	
	private Taxservice taxService;
	
	public RentalService(Double pricePerDay, Double pricePerHour, Taxservice taxService) {
		this.pricePerDay = pricePerDay;
		this.pricePerHour = pricePerHour;
		this.taxService = taxService;
	}
	
	public void processInvoice(CarRental carRental) {
		long t1 = carRental.getStart().getTime();
		long t2 = carRental.getFinish().getTime();
		double hours = (double)(t2 - t1) / 1000 / 60 / 60;
		
		double basicPayment;
		if(hours <= 12) {
			basicPayment = Math.ceil(hours) * pricePerHour;
		}
		else {
			basicPayment = Math.ceil(hours / 24) * pricePerDay;
	    }
	    double tax = taxService.tax(basicPayment);
	    
	    carRental.setInvoice(new Invoice(basicPayment, tax));
	}
	

}
