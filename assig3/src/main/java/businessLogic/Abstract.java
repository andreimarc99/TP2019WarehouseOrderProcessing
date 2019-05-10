package businessLogic;

import javax.swing.table.DefaultTableModel;

import dataAccess.OrderService;
import model.Order;
import presentation.GUI;

import java.lang.reflect.Field;
import java.util.Map.Entry;

public class Abstract {

	// private final Class<T> type;

	public Abstract() {
		// this.type = (Class<T>) ((ParameterizedType)
		// getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public String[] getColumnNames(Object object) {
		// if (this.type != null) {
		String[] columns = new String[object.getClass().getDeclaredFields().length];
		int index = 0;
		for (Field field : object.getClass().getDeclaredFields()) {
			columns[index++] = field.getName();
		}
		return columns;
		// }
	}

	public void createTable() {
		DefaultTableModel defaultTableModel = new DefaultTableModel(getColumnNames(OrderService.retrieveOrders().get(0)), 0);
		
		for (Entry<Integer, Order> entry : OrderService.retrieveOrders().entrySet()) {
			String product = entry.getValue().getProduct().getName() + " - "  + entry.getValue().getProduct().getPrice() + " $";
			String quantityPlusPrice = entry.getValue().getQuantity() + " => " + String.valueOf(entry.getValue().getQuantity() * entry.getValue().getProduct().getPrice() + " $");
			Object[] obj = new Object[] { entry.getValue().getClientName(), product, quantityPlusPrice};
			defaultTableModel.addRow(obj);
		}
		
		GUI.getTable_1().setModel(defaultTableModel);
	}
}
