package ro.tuc.tp.assig3.BusinessLogic;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JOptionPane;
/**
 * Clasa care creeaza factura pentru comanda
 * @author Marc
 *
 */
public class BillCreator {
	
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	/**
	 * Metoda care creeaza factura
	 * @param clientName numele clientului care a efectuat comanda
	 * @param productName numele produsului comandat
	 * @param quantity cantitatea de produse comandate
	 * @param price pretul produsului comandat
	 */
	public static void createBill(String clientName, String productName, int quantity, float price) {
		try {
			File file = new File("Bill - " + clientName + ".txt");
			
			if (!file.exists()) {
				file.createNewFile();
			} else {
				for (int i = 1; i < 10000; ++i) {
					file = new File("Bill - " + clientName + i + ".txt");
					if (!file.exists()) {
						file.createNewFile();
						break;
					}
				}
			}
			
			LocalDateTime now = LocalDateTime.now();
			
			PrintWriter printWriter = new PrintWriter(file);
			printWriter.println("WAREHOUSE INC.\t\tDATE: " + dtf.format(now) + "\n\n");
			printWriter.println("\n");
			printWriter.println("\nClient:\t\t\t\t" + clientName);
			printWriter.println("\nProduct:\t\t\t" + productName);
			printWriter.println("\nProduct Price:\t\t\t" + price);
			printWriter.println("\nQuantity:\t\t\t" + quantity);
			printWriter.println("\n----------------------------------------------");
			printWriter.println("\n\nTOTAL PRICE:\t\t\t" + quantity * price);
			printWriter.close();
			
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Cannot create file");
		}
	}

}
