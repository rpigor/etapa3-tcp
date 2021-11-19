package app.business.impl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import app.business.EvaluationOperationService;
import app.business.domain.Evaluation;
import app.business.domain.EvaluationGroup;
import app.business.domain.Evaluator;
import app.business.domain.Product;
import app.data.Database;

public class EvaluationOperationServiceImpl implements EvaluationOperationService {

	private final Database database;
	
	public EvaluationOperationServiceImpl(Database database) {
		this.database = database;
	}

	@Override
	public Evaluation rate(Product product, Evaluator evaluator, int rating) {
		return null;
	}
	
	@Override
	public void allow(EvaluationGroup selectedGroup, int evaluatorsPerProduct) {
		List<Product> groupProducts = getProductsByGroup(selectedGroup);
		Collections.sort(groupProducts, (o1, o2) -> o1.getId() - o2.getId());
		
		System.out.println("Iniciando alocação.");
		for (Product product : groupProducts) {
			List<Evaluator> pendingEvaluators = new LinkedList<Evaluator>(selectedGroup.getMembers());
			
			for (int i = 0; i < evaluatorsPerProduct; i++) {
				List<Evaluator> candidateEvaluators = getCandidateEvaluators(pendingEvaluators, product);
				
				if (!candidateEvaluators.isEmpty()) {
					sortEvaluatorsByAllowedProducts(candidateEvaluators, selectedGroup);
					
					Evaluator selectedEvaluator = candidateEvaluators.get(0);
					selectedEvaluator.allowProduct(product);
					System.out.println("Produto de id " + product.getId()
							+ " alocado ao avaliador de id " + selectedEvaluator.getId() + ".");
					
					pendingEvaluators.remove(selectedEvaluator);
				}
			}
		}
		
		System.out.println("Fim da alocação.");
		selectedGroup.setAllowed();
	}
	
	@Override
	public List<Product> getAllProducts() {
		return new LinkedList<Product>(database.getAllProducts());
	}

	@Override
	public List<EvaluationGroup> getAllEvaluationGroups() {
		return new LinkedList<EvaluationGroup>(database.getAllEvaluationGroups());
	}
	
	@Override
	public List<EvaluationGroup> getPendingEvaluationGroups() {
		List<EvaluationGroup> pendingGroups = new LinkedList<EvaluationGroup>();
		for (EvaluationGroup evaluationGroup : getAllEvaluationGroups()) {
			if (evaluationGroup.isUnallowed()) {
				pendingGroups.add(evaluationGroup);
			}
		}
		
		return pendingGroups;
	}

	@Override
	public EvaluationGroup getEvaluationGroupByName(String groupName) {
		return database.getEvaluationGroupByName(groupName);
	}

	@Override
	public Product getProductById(int productId) {
		return database.getProductById(productId);
	}
	
	@Override
	public List<Product> getProductsByGroup(EvaluationGroup selectedGroup) {
		List<Product> groupProducts = new LinkedList<Product>();
		
		for (Product product : getAllProducts()) {
			if (product.getEvaluationGroup().equals(selectedGroup)) {
				groupProducts.add(product);
			}
		}
		
		return groupProducts;
	}

	@Override
	public Evaluator getEvaluatorById(int evaluatorId) {
		return database.getEvaluatorById(evaluatorId);
	}
	
	private List<Evaluator> getCandidateEvaluators(List<Evaluator> pendingEvaluators, Product product) {
		List<Evaluator> candidateEvaluators = new LinkedList<Evaluator>();
		for (Evaluator evaluator : pendingEvaluators) {
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
