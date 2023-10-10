package ProjectJDBC;

import java.util.HashMap;

public class ModifyTable {
    
    private static String[] allTables = App.getAvailableTables();
    private static HashMap<Integer, String> trueList = new HashMap<>();
    
    public static HashMap<Integer, String> getTrueList() {
		return trueList;
	}

	public static void setTrueList(int index, String attribute) {
		ModifyTable.trueList.put(index, attribute);
	}

	public ModifyTable() {
    	int optionTable;
    	do {
    		System.out.println("\n\nWhich table would you like to change?\n"); //Print table
            for (int i = 0; i < allTables.length; i++) {
                System.out.println(i + 1 + ") " + allTables[i]);
            }
            do {
                App.setUserOption(false,"\nOption: "); //Choose table
                optionTable = App.getUserOption();
            } while(optionTable > allTables.length || optionTable < 1 );
            App.setSqlQuery("SELECT * FROM " + allTables[optionTable - 1] + ";"); //Show table
            new ShowResult(false, false);
            if (ShowResult.getAttributeDictionary().isEmpty()) {
	        	System.err.println("No rows available on this table, please use another table!");
	        }
		} while (ShowResult.getAttributeDictionary().isEmpty()); //No rows available
        for (int i = 1; i < ShowResult.getAttributeNumber(); i++) {
        	setTrueList(i, ShowResult.getNameAttributeDictionary().get(i)); //Get attributes names
        }
        System.out.println("Which ID would you like to change?");
        String optionRow = AuxiliarFunctions.verifyInputOnTable("\nOption: ", "", false, false);
        App.setSqlQuery("SELECT ORDINAL_POSITION AS ID,COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = N\'" + allTables[optionTable -1] + "\' ORDER BY ORDINAL_POSITION;");
        new ShowResult(false, false);
        System.out.println("Which column would you like to change?");
        String optionCol = AuxiliarFunctions.verifyInputOnTable("\nOption: ", "", false, true);
        App.setUserString("\nWhat is the new value? "); //New value
        String optionValStr = App.getUserString();
        App.setSqlQuery("UPDATE "+ allTables[optionTable - 1] + " SET " + getTrueList().get(Integer.parseInt(optionCol)) + " = '" + optionValStr + "' WHERE " + getTrueList().get(1) + " = " + optionRow +";");
        new ShowResult(true, false);
    }
}
