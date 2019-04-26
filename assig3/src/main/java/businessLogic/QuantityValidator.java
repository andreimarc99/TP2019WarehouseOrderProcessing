package businessLogic;

import javax.swing.JOptionPane;

public class QuantityValidator {

	public static int parseQuantity(String quantity) {
		int q = -1;

		// if (quantity.equals("") != false) {
		try {
			q = Integer.parseInt(quantity);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		// }

		return q;
	}
}
