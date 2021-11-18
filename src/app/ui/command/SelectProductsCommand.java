package app.ui.command;

import app.business.EvaluationOperationService;

public class SelectProductsCommand implements Command {

	private final EvaluationOperationService evalOperationService;

	public SelectProductsCommand(EvaluationOperationService evalOperationService) {
		this.evalOperationService = evalOperationService;
	}
	
	public void execute() {
		
	}
	
}
