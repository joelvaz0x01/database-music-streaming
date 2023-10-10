package ProjectJDBC;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class App {

    private static int userOption;
    private static String sqlQuery;
    private static String userString;
    private static Scanner userInput;
    private static int userOptionMenu;
    private static boolean error = true;
    private static String[] availableViews = {"vw_Artist", "vw_Band", "vw_Album", "vw_StudioAlbum", "vw_LiveAlbum", "vw_Song"};
    private static String[] availableTables = {"Artist", "ArtistInstruments", "SoloArtist", "Band", "Album", "StudioAlbum", "LiveAlbum", "Song", "SongMusicType"};
	
	public static Scanner getUserInput() {
		return userInput;
	}

	public static String[] getAvailableViews() {
		return availableViews;
	}

	public static String[] getAvailableTables() {
		return availableTables;
	}

    public static void setSqlQuery(String sqlQuery) {
		App.sqlQuery = sqlQuery;
	}

	public static String getSqlQuery() {
		return sqlQuery;
	}

	public static void setUserOption(boolean isMenuOption, String message) {
		while (true) {
			resetScanner(getUserInput()); //Clear buffer
			try {
				System.out.print(message);
				if (isMenuOption) { //Menu option
					userOptionMenu = userInput.nextInt();
				} else { //Other options
					userOption = userInput.nextInt();
				}
				break;
			} catch (InputMismatchException e) {
				System.err.println("Invalid number, please try again!\n");
			}
		}
	}
	
	public static int getUserOption() {
		return userOption;
	}
	
	public static void setUserString(String message) {
		while (true) {
			resetScanner(getUserInput()); //Clear buffer
			try {
				System.out.print(message);
				userString = userInput.nextLine();
				break;
			} catch (InputMismatchException e) {
				System.err.println("Invalid string, please try again!\n");
			}
		}
	}

	public static String getUserString() {
		return userString;
	}

	public static int getUserOptionMenu() {
		return userOptionMenu;
	}

	public static void setUserOptionMenu(int userOptionMenu) {
		App.userOptionMenu = userOptionMenu;
	}

	public static boolean isError() {
		return error;
	}

	public static void setError(boolean error) {
		App.error = error;
	}
	
	public static void resetScanner(Scanner userInput) {
		App.userInput = new Scanner(System.in);
	}

	public static void main(String[] args)  {
		new DB_Connection(); //Database connection
        while(true) {
        	setError(false);
           	new MainMenu();
           	switch(getUserOptionMenu()) {
           		case 0: //Quit program
           			try {
           				getUserInput().close(); //Close Scanner
                       	DB_Connection.getConnection().close(); //Close database connection
                       	System.exit(0);
                    } catch (SQLException e) {
                   		System.err.println("An error occurred when closing the connection to the database!");
                   		System.exit(1);
					}
           		case 1: //Insert values into table
           			new InsertIntoTable();
           			break;
           			
           		case 2: //Modify values into table
           			new ModifyTable();
           			break;
           			
           		case 3: //Delete values into table
           			new DeleteTableRow();
           			break;

           		case 4: //Show a table
           			new GetTableAndView(getAvailableTables());
           			break;
           			
           		case 5: //Show a view
           			new GetTableAndView(getAvailableViews());
           			break;
                    
           		case 6: //Show tables with queries (AND e OR)
           			new GetCustomTables();
           			break;

           		case 7: //Search for a string on all tables (selects 2 or more tables with a condition + string)
           			new SearchStringOnAllDatabase();
           			break;

           		case 8: //Get results through a calculation
           			new SQLCalculation();
           			break;

           		case 9: //Run a custom query
           			setUserString("\nPlease type your custom query: "); //Input String
           			setSqlQuery(getUserString());
           			break;
           			
           		default: //Error
           			setError(true);
           			System.err.println("Invalid option, please try again!");
           	}
           	if (!isError() && getUserOptionMenu() != 7 && getUserOptionMenu() > 3) {
           		new ShowResult(false, true);
           	}
        }
	} 
}
