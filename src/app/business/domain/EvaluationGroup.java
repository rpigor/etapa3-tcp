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
	
	public boolean hasPendingEvaluation() {
		for (Evaluator evaluator : getMembers()) {
			if (evaluator.hasPendingEvaluationForGroup(this)) {
				return true;
			}
		}
		
		return false;
	}
	
	public void allowProducts(List<Product> products, int evaluatorsPerProduct) {
		System.out.println("Iniciando alocação.");
		for (int i = 0; i < evaluatorsPerProduct; i++) {
			List<Product> sortedProducts = new LinkedList<Product>(products);
			Collections.sort(sortedProducts, (o1, o2) -> o1.getId() - o2.getId());
			
			while (!sortedProducts.isEmpty()) {
				Product product = sortedProducts.get(0);
				List<Evaluator> candidateEvaluators = getCandidateEvaluatorsForProduct(product);
				sortEvaluatorsByAllowedProducts(candidateEvaluators, this);
				
				int j = 0;
				Evaluation evaluation = null;
				while (evaluation == null && j < candidateEvaluators.size()) {
					Evaluator selectedEvaluator = candidateEvaluators.get(j);
					evaluation = selectedEvaluator.allowProduct(product); // returns null if this product was already allowed to this evaluator
					j++;
				}
				
				if (evaluation != null) {
					System.out.println("Produto de id " + product.getId()
							+ " alocado ao avaliador de id " + evaluation.getEvaluator().getId() + ".");
				} else {
					System.out.println("Produto de id " + product.getId() + " esgotou todos avaliadores disponíveis para alocação.");
				}
				
				sortedProducts.remove(product);
			}
		}
		
		System.out.println("Fim da alocação.");
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
				if (evaluation.isPending()) {
					continue;
				}
				
				Product product = evaluation.getProduct();
				
				if (!product.getEvaluationGroup().equals(this))
					continue;
				
				if (productsRatings.containsKey(product)) {
					productsRatings.get(product).add(evaluation.getRating());
				} else {
					productsRatings.put(product, new LinkedList<Integer>(Arrays.asList(evaluation.getRating())));
				}
			}
		}
		
		return productsRatings;
	}
	
	private List<Evaluator> getCandidateEvaluatorsForProduct(Product product) {
		List<Evaluator> candidateEvaluators = new LinkedList<Evaluator>();
		for (Evaluator evaluator : getMembers()) {
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
