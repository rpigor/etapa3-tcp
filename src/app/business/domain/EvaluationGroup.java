package app.business.domain;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
	
	public void setUnallowed() {
		status = Status.PENDING;
	}
	
	public boolean hasPendingEvaluationForGroup(EvaluationGroup evaluationGroup) {
		for (Evaluator evaluator : getMembers()) {
			if (evaluator.hasPendingEvaluationForGroup(evaluationGroup)) {
				return true;
			}
		}
		
		return false;
	}
	
	public void allowProducts(List<Product> products, int evaluatorsPerProduct) {
		List<Product> sortedProducts = new LinkedList<Product>(products);
		Collections.sort(sortedProducts, (o1, o2) -> o1.getId() - o2.getId());
		
		System.out.println("Iniciando aloca��o.");
		for (Product product : sortedProducts) {
			List<Evaluator> pendingEvaluators = new LinkedList<Evaluator>(getMembers());
			for (int i = 0; i < evaluatorsPerProduct; i++) {
				List<Evaluator> candidateEvaluators = getCandidateEvaluators(pendingEvaluators, product);
				
				if (!candidateEvaluators.isEmpty()) {
					sortEvaluatorsByAllowedProducts(candidateEvaluators, this);
					
					Evaluator selectedEvaluator = candidateEvaluators.get(0);
					selectedEvaluator.allowProduct(product);
					System.out.println("Produto de id " + product.getId()
							+ " alocado ao avaliador de id " + selectedEvaluator.getId() + ".");
					
					pendingEvaluators.remove(selectedEvaluator);
				}
			}
		}
		
		System.out.println("Fim da aloca��o.");
		setAllowed();
	}
	
	public Entry<Map<Product, Double>, Map<Product, Double>> getAcceptableAndUnacceptableProductsMean(double thresholdRating) {
		Map<Product, Double> acceptableProductsMean = new HashMap<Product, Double>();
		Map<Product, Double> unacceptableProductsMean = new HashMap<Product, Double>();
		
		Map<Product, List<Integer>> productsRatings = getProductsRatings();
		
		for (Map.Entry<Product, List<Integer>> set : productsRatings.entrySet()) {
			Product product = set.getKey();
			List<Integer> ratings = set.getValue();
			
			double ratingsSum = ratings.stream().reduce((a,b) -> a + b).get();
			double ratingsMean = ratingsSum / Double.valueOf(ratings.size());
			
			if (ratingsMean >= thresholdRating) {
				acceptableProductsMean.put(product, ratingsMean);
			} else {
				unacceptableProductsMean.put(product, ratingsMean);
			}
		}
		
		return new AbstractMap.SimpleEntry<Map<Product, Double>, Map<Product, Double>>(acceptableProductsMean, unacceptableProductsMean);
	}

	private Map<Product, List<Integer>> getProductsRatings() {
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
	
	private List<Evaluator> getCandidateEvaluators(List<Evaluator> evaluators, Product product) {
		List<Evaluator> candidateEvaluators = new LinkedList<Evaluator>();
		for (Evaluator evaluator : evaluators) {
			if (evaluator.isProductCandidate(product)) {
				candidateEvaluators.add(evaluator);
			}
		}
		
		return candidateEvaluators;
	}
	
	private void sortEvaluatorsByAllowedProducts(List<Evaluator> evaluators, EvaluationGroup group) {
		Collections.sort(evaluators, (o1, o2) -> o1.countAllowedProductsByGroup(group) - o2.countAllowedProductsByGroup(group));
	}

}
