package ProjectJDBC;

public class SQLCalculation {
		
	private static String[] questionsArray = {
			"How many songs exists on database",
			"How many songs exists by genre",
			"What genres have songs above a specified value",
			"What is the genre with the largerst number of songs"
		};
	
	public SQLCalculation() {
		System.out.println("\nWhat would you like to calculate?\n");
        for (int i = 0; i < questionsArray.length; i++) {
            System.out.println(i + 1 + ") " + questionsArray[i]);
        }
        App.setUserOption(false,"\nOption: "); //Input number
        
        while(true) {
	        switch(App.getUserOption()) {
	            case 1: //Number of songs
	            	App.setSqlQuery("SELECT COUNT(*) as Song_Count from vw_Song");
	                break;
	            
	            case 2: //Number of songs per genre
	            	App.setSqlQuery("SELECT music_type, COUNT(song_id) FROM vw_Song GROUP BY music_type;");
	                break;
	            
	            case 3: //Number of songs above X value
	                App.setUserOption(false,"\nSpecify threshold value: "); //Input number
	                App.setSqlQuery("SELECT music_type, COUNT(*) FROM vw_Song GROUP BY music_type HAVING COUNT(*) > " + App.getUserOption() + ";");
	                break;
	            
	            case 4: //Genre with the highest music count
	            	App.setSqlQuery("SELECT MAX(music_type) FROM vw_Song;");
	                break;
	            
	            default:
	                System.err.println("Invalid option, please try again!");
	        }
	        break;
        }
	}
}
