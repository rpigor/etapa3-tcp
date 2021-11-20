package app.business.domain;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class EvaluationGroup {

	private String name;
	private List<Evaluator> members;
	private Status status;
	
	public EvaluationGroup(String name, List<Evaluator> members) {
		this.name = name;
		this.members = members;
		this.status = Status.PENDING;
	}
	
	public String getName() {
		return name;
	}
	
	public List<Evaluator> getMembers() {
		return members;
	}

	public boolean isAllowed() {
		return status.equals(Status.COMPLETED);
	}
	
	public boolean isUnallowed() {
		return status.equals(Status.PENDING);
	}

	public void setAllowed() {
		status = Status.COMPLETED;
	}
	
	public boolean hasPendingEvaluationForGroup(EvaluationGroup evaluationGroup) {
		for (Evaluator evaluator : getMembers()) {
			if (evaluator.hasPendingEvaluationForGroup(evaluationGroup)) {
				return true;
			}
		}
		
		return false;
	}

	public Map<Product, List<Integer>> getProductsRatings() {
		Map<Product, List<Integer>> productsRatings = new HashMap<Product, List<Integer>>();
		for (Evaluator evaluator : getMembers()) {
			for (Evaluation evaluation : evaluator.getEvaluations()) {
				Product product = evaluation.getProduct();
				
				if (productsRatings.containsKey(product)) {
					productsRatings.get(product).add(evaluation.getRating());
				} else {
					productsRatings.put(product, new LinkedList<Integer>(Arrays.asList(evaluation.getRating())));
				}
			}
		}
		
		return productsRatings;
	}

}
