package ro.tuc.tp.assig3.Model;

public class Product {
	private String name;
	private float price;
	private int stock;
	/**
	 * Returneaza numele unui produs
	 * @return numele produsului respectiv
	 */
	public String getName() {
		return name;
	}
	/**
	 * Asigneaza un nume unui produs
	 * @param name numele ce va fi purtat de produsul respectiv
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Returneaza pretul unui produs
	 * @return pretul produsului respectiv
	 */
	public float getPrice() {
		return price;
	}
	/**
	 * Asigneaza un pret unui produs
	 * @param price pretul produsului respectiv
	 */
	public void setPrice(float price) {
		this.price = price;
	}
	/**
	 * Returneaza numarul de produse ramase in stoc
	 * @return numarul de produse
	 */
	public int getStock() {
		return stock;
	}
	/**
	 * Asigneaza un numar de produse ramase in stoc
	 * @param stock numarul de produse 
	 */
	public void setStock(int stock) {
		this.stock = stock;
	}
	
}
