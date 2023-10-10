package ProjectJDBC;

public class GetTableAndView {
	
	public GetTableAndView(String[] table) {
		do {
			System.out.println("\nWhich table would you like to visualize?\n\nSelect your option:\n");
			for (int i = 0; i < table.length; i++) {
				System.out.println(i + 1 + ") " + table[i] + " Table");
			}
			App.setUserOption(false,"\nOption: "); //Input number
			if (App.getUserOption() < 1 || App.getUserOption() > table.length) {
				System.err.println("Invalid option, please try again!");
			} else {
				App.setSqlQuery("SELECT * FROM " + table[App.getUserOption() - 1] + ";");
			}
		} while (App.getUserOption() < 1 || App.getUserOption() > table.length);
	}
}
