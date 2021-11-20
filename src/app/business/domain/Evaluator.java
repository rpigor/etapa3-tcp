package app.business.domain;

import java.util.LinkedList;
import java.util.List;

public class Evaluator {

	private int id;
	private String name;
	private String state;
	private List<Category> interestCategories;
	private List<Evaluation> evaluations;
	
	public Evaluator(int id, String name, String state,
			List<Category> interestCategories) {
		this.id = id;
		this.name = name;
		this.state = state;
		this.interestCategories = interestCategories;
		this.evaluations = new LinkedList<Evaluation>();
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getState() {
		return state;
	}
	
	public List<Category> getInterestCategories() {
		return interestCategories;
	}

	public List<Evaluation> getEvaluations() {
		return evaluations;
	}

	public Evaluation allowProduct(Product product) {
		Evaluation pendingEvaluation = new Evaluation(this, product);
		evaluations.add(pendingEvaluation);
		return pendingEvaluation;
	}
	
	public boolean isProductCandidate(Product product) {
		return !this.equals(product.getRequester())
				&& this.getInterestCategories().contains(product.getCategory());
	}

	public int countAllowedProductsByGroup(EvaluationGroup group) {
		List<Evaluation> evaluations = getEvaluations();
		
		if (evaluations.isEmpty())
			return 0;
		
		int n = 0;
		for (Evaluation evaluation : evaluations) {
			if (evaluation.getProduct().getEvaluationGroup().equals(group)) {
				n++;
			}
		}
		
		return n;
	}

	public Evaluation getPendingEvaluationForProduct(Product product) {
		for (Evaluation evaluation : getEvaluations()) {
			if (evaluation.getProduct().equals(product) && evaluation.isPending()) {
				return evaluation;
			}
		}
		
		return null;
	}
	
	public boolean hasPendingEvaluationForGroup(EvaluationGroup evaluationGroup) {
		for (Evaluation evaluation : getEvaluations()) {
			if (evaluation.isPending() && evaluation.getProduct().getEvaluationGroup().equals(evaluationGroup)) {
				return true;
			}
		}
		
		return false;
	}

}
