package businessLogic;

import javax.swing.JOptionPane;

public class StockValidator {

	public static int parseStock(String stock) {
		int st = -1;

		// if (stock.equals("") != false) {
		try {
			st = Integer.parseInt(stock);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		// }
		return st;
	}
}
