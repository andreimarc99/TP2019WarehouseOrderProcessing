package model;
/**
 * Clasa Client este clasa ce retine informatiile ce au legatura cu clientii
 * @author Marc
 *
 */
public class Client {
	private String name;
	/**
	 * Returneaza numele clientului
	 * @return numele clientului
	 */
	public String getName() {
		return name;
	}
	/**
	 * Asigneaza un nume clientului
	 * @param name numele purtat de client
	 */
	public void setName(String name) {
		this.name = name;
	}
	
}
