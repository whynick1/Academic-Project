package com.Intuit.view;

import java.util.Scanner;
import java.lang.Object;

import com.Intuit.domain.vender.abs.Vender;
import com.Intuit.service.VenderService;
import com.Intuit.service.VenderServiceImpl;

public class Application {

	public static void main(String[] args) {
		//create vender 
		Scanner sc=new Scanner(System.in);
		VenderService service=new VenderServiceImpl();
		System.out.println("Please select a vender: 1--Food Vender, 2--Beverage Vender, 3--DigitalProduct Vender, 4--MultiProduct Vender, 5--Quit");
		System.out.print("Input the corresponding number: ");
		int venderType = -1;
		String venderTypeTemp = sc.next(); 
		if (venderTypeTemp.matches("\\d*"))
	    	venderType=Integer.parseInt(venderTypeTemp);
		
		while(true){			
			Vender vender=service.createVender(venderType);
			System.out.println("These products in stock");
			vender.showProducts();
		    int position=service.getProductPositionFromUser(); //user select which product he wants
		    int amount=service.getAmountFromUser();            //user input how much he want, if amount larger than 3 count as 3
		    //Check wether product in stock
			while(!vender.stockCheck(position, amount)){
				System.out.println("Would you like choose other product? Y/N");
				if("N".equalsIgnoreCase(sc.next())){
					System.out.println("Transaction canceled, Thank you");
					System.exit(-1);
				} 
				position=service.getProductPositionFromUser();
			    amount=service.getAmountFromUser();
			}
			//calculate the total payments
			double totalPayment=service.calculateTotalPayment(position, amount, vender);
			System.out.println("Total amout to pay is $"+totalPayment);
			//user payment
			if(service.payment(totalPayment)){
				vender.incomeAdd(totalPayment); //Add pay to vender's sale's record
				//drive motor to get product
				service.getProducts(vender, position, amount);
			}else{
				System.out.println("Transaction canceled, Thank you");
			}
			
			System.out.println("Vender earned $"+vender.getIncome());
		}
		
	}

}
