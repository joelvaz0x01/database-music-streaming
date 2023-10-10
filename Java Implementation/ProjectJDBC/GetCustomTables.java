package ProjectJDBC;

public class GetCustomTables {
	
	private static String[] artistOption = {
			"View Artists born after the year 2000",
			"View Artists born in the USA",
			"View Artists born after the year 2000 or born in the USA",
			"View all Albums that include at least one song with Pop and Rock genres"
		};
	
	public GetCustomTables() {
		do {
			System.out.println("\n\nWhich table would you like to custom visualize?\nSelect your option:\n");
			for (int i = 0; i < artistOption.length; i++) {
				System.out.println((i + 1) + ") " + artistOption[i]);
			}
			App.setUserOption(false,"\nOption: "); //Input number
			App.setSqlQuery("SELECT artist_id, artist_name, birth_year, birth_country, age FROM vw_Artist WHERE ");

			switch(App.getUserOption()) {
				case 1:
					App.setSqlQuery(App.getSqlQuery() + "birth_year >= 2000;");
					break;

				case 2:
					App.setSqlQuery(App.getSqlQuery() + "birth_country = \"USA\";");
					break;

				case 3:
					App.setSqlQuery(App.getSqlQuery() + "birth_year >= 2000 OR birth_country = \"USA\";");
					break;
					
				case 4:
					App.setSqlQuery("SELECT * FROM Album a WHERE EXISTS (SELECT * FROM Song s WHERE a.album_id = s.album_id AND EXISTS (SELECT music_type_name FROM SongMusicType smt WHERE s.song_id = smt.song_id AND music_type_name IN (\"Pop\",\"Folk\")));");
					break;

				default:
					System.err.println("Invalid option, please try again!");
			}
		} while (App.getUserOption() < 1 || App.getUserOption() > 4);
	}
}
