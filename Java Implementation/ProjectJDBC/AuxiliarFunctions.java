package ProjectJDBC;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Objects;

public class AuxiliarFunctions {
	public static <T, E> Integer getKeyByValue(HashMap<Integer, String> hashMap, String valueSearch) {
	    for (Entry<Integer, String> entry : hashMap.entrySet()) {
	        if (Objects.equals(valueSearch, entry.getValue())) {
	            return entry.getKey();
	        }
	    }
	    return null;
	}
	
	public static String verifyInputOnTable(String question, String choice, boolean error, boolean isPrimaryKey) {
		do {
            App.setUserString(question); //Choose row
            choice = App.getUserString();
            try {
            	Integer.parseInt(choice); //Verify if is a valid number
				error = false;
				if (isPrimaryKey && Integer.parseInt(choice) == 1) { //Error to modify primary key
					throw new NumberFormatException();
				}
			} catch (NumberFormatException e) { //Invalid number
				error = true;
			}
        } while(getKeyByValue(ShowResult.getAttributeDictionary(), choice) == null || error);
		return choice;
	}
}
