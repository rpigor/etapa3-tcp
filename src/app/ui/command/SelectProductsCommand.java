package app.ui.command;

import java.util.List;

import app.business.EvaluationOperationService;
import app.business.domain.EvaluationGroup;
import app.ui.UIUtils;

public class SelectProductsCommand implements Command {

	private static final int THRESHOLD_RATING = 0;
	
	private final EvaluationOperationService evalOperationService;

	public SelectProductsCommand(EvaluationOperationService evalOperationService) {
		this.evalOperationService = evalOperationService;
	}
	
	public void execute() {
		List<EvaluationGroup> evaluationGroups = evalOperationService.getAllEvaluationGroups();
		
		printEvaluationGroups(evaluationGroups);
		
		EvaluationGroup selectedGroup = selectEvaluationGroup();
		System.out.println("Grupo de avaliação selecionado: " + selectedGroup.getName());
	}

	private void printEvaluationGroups(List<EvaluationGroup> evaluationGroups) {
		System.out.println("Grupos de avaliação: ");
		for (EvaluationGroup evaluationGroup : evaluationGroups) {
			System.out.println(evaluationGroup.getName());
		}
	}
	
	private EvaluationGroup selectEvaluationGroup() {
		String groupName = null;
		
		EvaluationGroup selectedGroup = null;
		while (selectedGroup == null) {
			System.out.print("Entre com o nome do grupo de avaliação: ");
			groupName = UIUtils.INSTANCE.readString();
			selectedGroup = evalOperationService.getEvaluationGroupByName(groupName);
		}
		
		return selectedGroup;
	}
	
}
