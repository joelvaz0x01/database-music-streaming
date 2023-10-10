package ProjectJDBC;

public class MainMenu {
    
    public MainMenu() {
        System.out.println("\n\nWhich query would you like to run?\n");
        System.out.println("1) Insert a new value");
        System.out.println("2) Modify a chosen value");
        System.out.println("3) Delete a chose value\n");
        System.out.println("4) Show a table");
        System.out.println("5) Show a view");
        System.out.println("6) Show tables with queries");
        System.out.println("7) Search for a string on all tables");
        System.out.println("8) Get results through a calculation");
        System.out.println("9) Run a custom query\n");
        System.out.println("0) Quit\n");
        App.setUserOption(true,"Option: ");
    }
}
