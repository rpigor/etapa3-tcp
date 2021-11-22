package app.ui.command;

import java.util.List;

import app.business.domain.EvaluationGroup;
import app.data.Database;
import app.ui.UIUtils;

public class AllowProductsCommand extends Command {
	
	private static final int MIN_PRODUCT_EVALUATORS = 2;
	private static final int MAX_PRODUCT_EVALUATORS = 5;
	
	public AllowProductsCommand(Database database) {
		super(database);
	}

	@Override
	public void execute() {
		List<EvaluationGroup> pendingGroups = database.getPendingEvaluationGroups();
		
		if (pendingGroups.isEmpty()) {
			System.out.println("Todos os grupos já foram alocados.");
			return;
		}
		
		printEvaluationGroups(pendingGroups);
		
		EvaluationGroup selectedGroup = selectPendingEvaluationGroup();
		System.out.println("Grupo de avaliação selecionado: " + selectedGroup.getName());
		
		System.out.print("Entre com o número de avaliadores a serem alocados a cada produto: ");
		int evaluatorsPerProduct = UIUtils.INSTANCE.readInteger(MIN_PRODUCT_EVALUATORS, MAX_PRODUCT_EVALUATORS);
		
		selectedGroup.allowProducts(database.getProductsByGroup(selectedGroup), evaluatorsPerProduct);
		
		System.out.println("Operação efetuada com sucesso.");
	}
	
	@Override
	public String toString() {
		return "Alocar Produtos";
	}

	private void printEvaluationGroups(List<EvaluationGroup> evaluationGroups) {
		System.out.println("Grupos de avaliação: ");
		for (EvaluationGroup evaluationGroup : evaluationGroups) {
			System.out.println(evaluationGroup.getName());
		}
	}
	
	private EvaluationGroup selectPendingEvaluationGroup() {
		String groupName = null;
		
		EvaluationGroup selectedGroup = null;
		while (selectedGroup == null || selectedGroup.isAllowed()) {
			System.out.print("Entre com o nome do grupo de avaliação: ");
			groupName = UIUtils.INSTANCE.readString();
			selectedGroup = database.getEvaluationGroupByName(groupName);
		}
		
		return selectedGroup;
	}
	
}
