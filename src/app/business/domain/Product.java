package app.business.domain;

import java.util.LinkedList;
import java.util.List;

public class Product {

	private int id;
	private String name;
	private Evaluator requester;
	private EvaluationGroup evaluationGroup;
	private Category category;
	
	public Product(int id, String name, Evaluator requester,
			EvaluationGroup evaluationGroup, Category category) {
		this.id = id;
		this.name = name;
		this.requester = requester;
		this.evaluationGroup = evaluationGroup;
		this.category = category;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public Evaluator getRequester() {
		return requester;
	}
	
	public EvaluationGroup getEvaluationGroup() {
		return evaluationGroup;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public List<Evaluator> getAllowedEvaluators() {
		List<Evaluator> allowedEvaluators = new LinkedList<Evaluator>();
		for (Evaluator evaluator : getEvaluationGroup().getMembers()) {
			for (Evaluation evaluation : evaluator.getEvaluations()) {
				if (evaluation.getProduct().equals(this) && evaluation.isPending()) {
					allowedEvaluators.add(evaluator);
					break;
				}
			}
		}
		
		return allowedEvaluators;
	}

}
