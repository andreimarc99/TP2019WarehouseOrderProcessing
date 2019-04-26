package businessLogic;

public class NameValidator {

	public static boolean isNameValid(String name) {
		if (name.equals("")) {
			return false;
		}
		return true;
	}
}
