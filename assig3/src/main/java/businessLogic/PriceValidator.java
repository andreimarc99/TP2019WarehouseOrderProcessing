package businessLogic;

import javax.swing.JOptionPane;

public class PriceValidator {

	public static float parsePrice(String price) {
		float pr = -1;

		// if (price.isEmpty() != false) {						
		try {
			pr = Float.parseFloat(price);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		// }
		return pr;
	}
}
