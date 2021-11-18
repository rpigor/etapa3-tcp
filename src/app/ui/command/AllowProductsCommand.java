package app.ui.command;

import app.business.EvaluationOperationService;

public class AllowProductsCommand implements Command {
	
	private final EvaluationOperationService evalOperationService;
	
	public AllowProductsCommand(EvaluationOperationService evalOperationService) {
		this.evalOperationService = evalOperationService;
	}
	
	public void execute() {
		
	}

}
