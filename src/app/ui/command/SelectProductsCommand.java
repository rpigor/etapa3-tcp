package app.ui.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import app.business.domain.EvaluationGroup;
import app.business.domain.Product;
import app.data.Database;
import app.ui.UIUtils;

public class SelectProductsCommand extends Command {

	private static final double THRESHOLD_RATING = 0;
	
	public SelectProductsCommand(Database database) {
		super(database);
	}
	
	public void execute() {
		List<EvaluationGroup> evaluationGroups = new LinkedList<EvaluationGroup>(database.getAllEvaluationGroups());
		
		printEvaluationGroups(evaluationGroups);
		
		EvaluationGroup selectedGroup = selectEvaluationGroup();
		
		if (selectedGroup.isUnallowed()) {
			System.out.println("Este grupo ainda não foi alocado.");
			return;
		} else if (selectedGroup.hasPendingEvaluationForGroup(selectedGroup)) {
			System.out.println("Este grupo ainda possui avaliações pendentes.");
			return;
		}
		
		System.out.println("Grupo de avaliação selecionado: " + selectedGroup.getName());
		
		Entry<Map<Product, Double>, Map<Product, Double>> acceptableAndUnacceptableProductsMean =
				selectedGroup.getAcceptableAndUnacceptableProductsMean(THRESHOLD_RATING);
		
		Map<Product, Double> acceptableProductsMean = sortDescendingProductsMean(acceptableAndUnacceptableProductsMean.getKey());
		Map<Product, Double> unacceptableProductsMean = sortAscendingProductsMean(acceptableAndUnacceptableProductsMean.getValue());
		
		System.out.println("Lista de produtos aceitáveis do grupo " + selectedGroup.getName() + " (média das avaliações >= " + THRESHOLD_RATING + "): ");
		printProductsAndMean(acceptableProductsMean);
		
		System.out.println();
		
		System.out.println("Lista de produtos não aceitáveis do grupo " + selectedGroup.getName() + " (média das avaliações < " + THRESHOLD_RATING + "): ");
		printProductsAndMean(unacceptableProductsMean);
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
			selectedGroup = database.getEvaluationGroupByName(groupName);
		}
		
		return selectedGroup;
	}
	
	private void printProductsAndMean(Map<Product, Double> productsMean) {
		System.out.printf("%-30s %-30s %-30s\n", "Id", "Nome", "Média");
		for (Entry<Product, Double> set : productsMean.entrySet()) {
			System.out.printf("%-30s %-30s %-30f\n", set.getKey().getId(), set.getKey().getName(), set.getValue());
		}
	}
	
	private Map<Product, Double> sortAscendingProductsMean(Map<Product, Double> productsMean) {
		return sortProductsMean(productsMean, false);
	}
	
	private Map<Product, Double> sortDescendingProductsMean(Map<Product, Double> productsMean) {
		return sortProductsMean(productsMean, true);
	}
	
	private Map<Product, Double> sortProductsMean(Map<Product, Double> productsMean, boolean reversedOrder) {
		List<Entry<Product, Double>> entryList = new ArrayList<>(productsMean.entrySet());
		
		if (reversedOrder) {
			entryList.sort(Collections.reverseOrder(Entry.comparingByValue()));
		} else {
			entryList.sort(Entry.comparingByValue());
		}

        Map<Product, Double> sortedProductsMean = new LinkedHashMap<>();
        for (Entry<Product, Double> entry : entryList) {
        	sortedProductsMean.put(entry.getKey(), entry.getValue());
        }

        return sortedProductsMean;
	}
	
}
