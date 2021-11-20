package app;

import app.business.EvaluationOperationService;
import app.business.impl.EvaluationOperationServiceImpl;
import app.data.Database;
import app.ui.TextInterface;

public class Application {

	public static void main(String[] args) {
		Database database = new Database();
		
		EvaluationOperationService evalOperationService = new EvaluationOperationServiceImpl(database);
		
		TextInterface appInterface = new TextInterface(evalOperationService, database);
		
		appInterface.createAndShowUI();
	}

}
