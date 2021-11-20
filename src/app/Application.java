package app;

import app.data.Database;
import app.ui.TextInterface;

public class Application {

	public static void main(String[] args) {
		Database database = new Database();
		
		TextInterface appInterface = new TextInterface(database);
		
		appInterface.createAndShowUI();
	}

}
