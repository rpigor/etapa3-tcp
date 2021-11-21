package app.ui.command;

import java.util.List;

import app.business.domain.EvaluationGroup;
import app.data.Database;
import app.ui.UIUtils;

public class AllowProductsCommand extends Command {
	
	public AllowProductsCommand(Database database) {
		super(database);
	}

	@Override
	public void execute() {
		List<EvaluationGroup> pendingGroups = database.getPendingEvaluationGroups();
		
		if (pendingGroups.isEmpty()) {
			System.out.println("Todos os grupos j� foram alocados.");
			return;
		}
		
		printEvaluationGroups(pendingGroups);
		
		EvaluationGroup selectedGroup = selectPendingEvaluationGroup();
		System.out.println("Grupo de avalia��o selecionado: " + selectedGroup.getName());
		
		System.out.print("Entre com o n�mero de avaliadores a serem alocados a cada produto: ");
		int evaluatorsPerProduct = UIUtils.INSTANCE.readInteger(2, 5);
		
		selectedGroup.allowProducts(database.getProductsByGroup(selectedGroup), evaluatorsPerProduct);
		
		System.out.println("Opera��o efetuada com sucesso.");
	}
	
	@Override
	public String toString() {
		return "Alocar Produtos";
	}

	private void printEvaluationGroups(List<EvaluationGroup> evaluationGroups) {
		System.out.println("Grupos de avalia��o: ");
		for (EvaluationGroup evaluationGroup : evaluationGroups) {
			System.out.println(evaluationGroup.getName());
		}
	}
	
	private EvaluationGroup selectPendingEvaluationGroup() {
		String groupName = null;
		
		EvaluationGroup selectedGroup = null;
		while (selectedGroup == null || selectedGroup.isAllowed()) {
			System.out.print("Entre com o nome do grupo de avalia��o: ");
			groupName = UIUtils.INSTANCE.readString();
			selectedGroup = database.getEvaluationGroupByName(groupName);
		}
		
		return selectedGroup;
	}
	
}
