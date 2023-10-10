package ProjectJDBC;

import java.sql.Date;
import java.util.Calendar;

public class InsertIntoTable {
	
	private static String[] albumQuestions = {"\nRelease year: ", "\nRelease month: ", "\nRelease day: "};
	private static String[] contractQuestions = {"\nYear of contract: ", "\nMonth of contract: ", "\nDay of contract: "};
	private static String[] liveAlbumQuestions = {"\nYear of live event: ", "\nMonth of live event: ", "\nDay of live event: "};
	private static String[] careerQuestions = {"\nYear of start carrer: ", "\nMonth of start carrer: ", "\nDay of start carrer: "};
	
	public static String[] getAlbumQuestions() {
		return albumQuestions;
	}
	
	public static String[] getContractQuestions() {
		return contractQuestions;
	}

	public static String[] getLiveAlbumQuestions() {
		return liveAlbumQuestions;
	}

	public static String[] getCareerQuestions() {
		return careerQuestions;
	}
	
	private static int[] DateEvaluation(String[] questions) {
		int year, month, day;
		App.setUserOption(false, questions[0]);
    	year = App.getUserOption(); //Set year
    	do { //Set month
    		App.setUserOption(false, questions[1]);
    		month = App.getUserOption();
        	if (month > 12 || month < 1) {
        		System.err.println("Please insert a valid month (1 - 12)!");
        	}
    	} while (month > 12 || month < 1);
    	do { //Set day
    		App.setUserOption(false, questions[2]);
    		day = App.getUserOption();
        	if (day > 31 || day < 1) {
        		System.err.println("Please insert a valid ady (1 - 31)!");
        	}
    	} while (day > 31 || day < 1);
    	
    	return new int[] {year, month, day};
	}

	private static Date DateSQLBuilder(String[] questions) { //Convert to SQL Date
		int[] dateEvaluated = DateEvaluation(questions);
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, dateEvaluated[0]);
		calendar.set(Calendar.MONTH, dateEvaluated[1] - 1);
		calendar.set(Calendar.DAY_OF_MONTH, dateEvaluated[2]);
    	java.util.Date utilDate = calendar.getTime();
    	return new java.sql.Date(utilDate.getTime());
	}
	
	public InsertIntoTable() {
		int inicialSelection, artistBelong;
		do {
			System.out.println("\n\nWhat do you want to do?\n");
	        System.out.println("1) Create a new Artist");
	        System.out.println("2) Create a new Album\n");
			App.setUserOption(false, "Option: ");
			inicialSelection = App.getUserOption();
	        switch(inicialSelection) {
	            case 1: //Create Artist
	            	App.setUserString("\nName of the artist: ");
	            	String artistName = App.getUserString();
	            	App.setUserOption(false, "\nYear of birth: ");
	            	int artistBirth = App.getUserOption();
	            	App.setUserString("\nArtist's country: ");
	            	String artistCountry = App.getUserString();
	            	App.setSqlQuery("INSERT INTO Artist (artist_name, birth_year, birth_country) VALUES (\"" + artistName + "\", " + artistBirth + ", \"" + artistCountry + "\");");
	            	new ShowResult(true, false);
	            	App.setUserOption(false, "\nHow many instruments does the artist play (if 0 Vocals will be selected by default)?: ");
	            	App.setSqlQuery("INSERT INTO ArtistInstruments (artist_id, instrument_name) VALUES ");
	            	if (App.getUserOption() > 0) { //Play "instrument"
		            	for(int i = 0; i < App.getUserOption(); i++) {
		            		App.setUserString("Instrument " + (i + 1) + ": ");
			            	String artistInstrument = App.getUserString();
			            	App.setSqlQuery(App.getSqlQuery() + "((SELECT MAX(artist_id) FROM Artist), \"" + artistInstrument + "\")");
			            	if (i < App.getUserOption()- 1) {
			            		App.setSqlQuery(App.getSqlQuery() + ",");
			            	} else {
			            		App.setSqlQuery(App.getSqlQuery() + ";");
			            	}
		            	}
	            	} else { //Do not play any "instruments"
	            		App.setSqlQuery(App.getSqlQuery() + "((SELECT MAX(artist_id) FROM Artist), \"Vocals\");");
	            	}
	            	new ShowResult(true, false);
	            	
	            	do {
	            		System.out.println("\nThe artist belongs to:");
		            	System.out.println("1) A band");
		            	System.out.println("2) A solo career\n");
		            	
		            	App.setUserOption(false, "Option: ");
		            	artistBelong = App.getUserOption();
		            	
		            	switch (artistBelong) {
							case 1: //Band
								App.setUserString("\nName of the band: ");
				            	String bandName = App.getUserString();
				            	Date contractDate = DateSQLBuilder(getContractQuestions());
								App.setSqlQuery("INSERT INTO Band (artist_id, band_name, start_contract) VALUES ((SELECT MAX(artist_id) FROM Artist), \"" + bandName + "\", \'" + contractDate + "\');");
								break;
		
							case 2: //Solo career
								App.setUserString("\nStage name: ");
				            	String stageName = App.getUserString();
				            	Date careerDate = DateSQLBuilder(getCareerQuestions());
								App.setSqlQuery("INSERT INTO SoloArtist (artist_id, start_career, stage_name) VALUES ((SELECT MAX(artist_id) FROM Artist), \'" + careerDate + "\', \"" + stageName + "\");");
								break;
								
							default:
								System.err.println("Invalid option, please try again!");
						}
		            } while (artistBelong > 2 || artistBelong < 1);
	            	new ShowResult(true, false);
	                break;
	            
	            case 2: //Create Album
	            	int songNumbers, liveStudio;
	            	App.setUserString("\nAlbum name: ");
	            	String albumName = App.getUserString();
	            	App.setSqlQuery("SELECT artist_id, artist_name FROM Artist");
	            	new ShowResult(false, false);
	            	String artistID = AuxiliarFunctions.verifyInputOnTable("\nAlbum belongs to what artist_id: ","", false, false);	            	
	            	int[] albumDate = DateEvaluation(getAlbumQuestions());
	            	App.setSqlQuery("INSERT INTO Album (artist_id, album_name, release_year, release_month, release_day) VALUES (" + artistID + ", \"" + albumName + "\", " + albumDate[0] + ", " + albumDate[1] + ", " + albumDate[2] + ")");
	            	new ShowResult(true, false);
	            	do { //Number of songs
	            		App.setUserOption(false, "\nHow many songs will have the album?: ");
	            		songNumbers = App.getUserOption();
	            		if (songNumbers < 0) {
	            			System.err.println("The album must have at least one song!");
	            		} else {
	            			for(int i = 0; i < songNumbers; i++) {
	            				App.setSqlQuery("INSERT INTO Song (album_id, song_name, song_length) VALUES ");
			            		App.setUserString("Name of song " + (i + 1) + ": ");
				            	String songName = App.getUserString();
				            	App.setUserOption(false, "Song " + (i + 1) + " length (in seconds): ");
				            	int songLength = App.getUserOption();
				            	App.setSqlQuery(App.getSqlQuery() + "((SELECT MAX(album_id) FROM Album), \"" + songName + "\", " + songLength + ");");
				            	new ShowResult(true, false);
				            	App.setUserString("Song " + (i + 1) + " genre: ");
				            	String songType = App.getUserString();
				            	App.setSqlQuery("INSERT INTO SongMusicType (song_id, music_type_name) VALUES ((SELECT MAX(song_id) FROM Song), \"" + songType + "\");");
				            	new ShowResult(true, false);
			            	}
	            		}
	            	} while (songNumbers < 0);
	            	do {
	            		System.out.println("\nThis album is:");
		            	System.out.println("1) A live album");
		            	System.out.println("2) A studio album\n");
		            	App.setUserOption(false, "Option: ");
		            	liveStudio = App.getUserOption();
		            	switch (liveStudio) {
							case 1:
								App.setUserString("\nLocation of live event: ");
				            	String location = App.getUserString();
				            	Date liveDate = DateSQLBuilder(getLiveAlbumQuestions());
								App.setSqlQuery("INSERT INTO LiveAlbum (album_id, date, location) VALUES ((SELECT MAX(album_id) FROM Album), \"" + liveDate + "\", \'" + location + "\');");
								break;
		
							case 2:
								App.setUserString("\nStudio name: ");
				            	String studioName = App.getUserString();
								App.setSqlQuery("INSERT INTO StudioAlbum (album_id, studio) VALUES ((SELECT MAX(album_id) FROM Album), \"" + studioName + "\");");
								break;
								
							default:
								System.err.println("Invalid option, please try again!");
						}
					} while (liveStudio < 1 || liveStudio > 2);
	            	new ShowResult(true, false);
	                break;
	            
	            default:
	                System.err.println("Invalid option, please try again!");
	        }
		} while(inicialSelection > 2 || inicialSelection < 1);
	}
}
