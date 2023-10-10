package ProjectJDBC;

public class SearchStringOnAllDatabase {
	
	private static String[] allTables = App.getAvailableTables();
	
	private static String[][] attributesTable = {
			{"artist_name", "birth_country"}, //Artist Table
			{"instrument_name"}, //ArtistInstruments Table
			{"stage_name"}, //SoloArtist table
			{"band_name"}, //Band table
			{"album_name"}, //Album table
			{"studio"}, //StudioAlbum table
			{"location"}, //LiveAlbum table
			{"song_name"}, //Song tables
			{"music_type_name"} //SongMusicType table
		};
	
	public SearchStringOnAllDatabase() {
		App.setUserString("\nWhich STRING do you want to search for?: "); //Input String
		for(int i = 0; i < attributesTable.length; i++) {
			App.setSqlQuery(""); //Reset query
			for(int j = 0; j < attributesTable[i].length; j++) {
				App.setSqlQuery(App.getSqlQuery() + "SELECT * FROM " + allTables[i] + " WHERE " + attributesTable[i][j] +" LIKE \'%" + App.getUserString() + "%\'"); //Show tables with AND and OR
				if(j < attributesTable[i].length - 1) {
					App.setSqlQuery(App.getSqlQuery() + " UNION ALL "); //Get all values (distinct or not)
				}
	        }
			App.setSqlQuery(App.getSqlQuery() + ";"); //Reset query
			new ShowResult(false, true);
		}
	}
}
