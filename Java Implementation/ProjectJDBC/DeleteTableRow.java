package ProjectJDBC;

import java.util.HashMap;

public class DeleteTableRow {
	
	private static String[] allTables = {"ArtistInstruments", "SoloArtist", "Band", "StudioAlbum", "LiveAlbum", "SongMusicType"};
    private static HashMap<Integer, String> trueList = new HashMap<>();
    
    public static HashMap<Integer, String> getTrueList() {
		return trueList;
	}

	public static void setTrueList(int index, String attribute) {
		DeleteTableRow.trueList.put(index, attribute);
	}
	
	public DeleteTableRow() {
		int optionTable;
		do {
			System.out.println("\n\nSelect a table you would like to delete a row?\n");
	        for (int i = 0; i < allTables.length; i++) {
	            System.out.println(i + 1 + ") " + allTables[i]);
	        }
	        do {
	            App.setUserOption(false,"\nOption: "); //Choose table
	            optionTable = App.getUserOption();
	        } while(optionTable > allTables.length || optionTable < 1 );
	        App.setSqlQuery("SELECT * FROM " + allTables[optionTable - 1] + ";"); //Show table
	        new ShowResult(false,false);
	        
	        for (int i = 1; i < ShowResult.getAttributeNumber(); i++) {
	        	setTrueList(i, ShowResult.getNameAttributeDictionary().get(i)); //Get attributes names
	        }
	        if (ShowResult.getAttributeDictionary().isEmpty()) {
	        	System.err.println("No rows available on this table, please use another table!");
	        }
		} while (ShowResult.getAttributeDictionary().isEmpty()); //No rows available
        System.out.println("Which row would you like to delete?");
        String optionRow = AuxiliarFunctions.verifyInputOnTable("\nOption: ", "", false, false);
        App.setSqlQuery("DELETE FROM " + allTables[optionTable - 1] + " WHERE " + getTrueList().get(1) + " = " + optionRow + ";");
        new ShowResult(true, false);
    }
}
