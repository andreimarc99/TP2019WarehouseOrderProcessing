package ro.tuc.tp.assig3.Model;
/**
 * Order este clasa ce retine informatii in legatura cu comenzile
 * @author Marc
 *
 */
public class Order {
	private String clientName;
	private Product product;
	private int quantity;
	/**
	 * Returneaza numele clientului ce a plasat comanda
	 * @return numele clientului respectiv
	 */
	public String getClientName() {
		return clientName;
	}
	/**
	 * Asigneaza un nume clientului ce va plasa comanda
	 * @param clientName numele clientului respectiv
	 */
	public void setClientName(String clientName) {
		this.clientName= clientName;
	}
	/**
	 * Returneaza numele produsului comandat
	 * @return numele produsului respectiv
	 */
	public Product getProduct() {
		return product;
	}
	/**
	 * Asigneaza un produs comenzii
	 * @param product produsul respectiv
	 */
	public void setProduct(Product product) {
		this.product = product;
	}
	/**
	 * Returneaza cantitatea de produse comandate
 	 * @return numarul de produse comandate
	 */
	public int getQuantity() {
		return quantity;
	}
	/**
	 * Asigneaza o cantitate de produse comandate
	 * @param quantity numarul de produse comandate
	 */
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
