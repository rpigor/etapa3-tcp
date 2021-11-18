package app.business.impl;

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
	public List<Product> getAllProducts() {
		return new LinkedList<Product>(database.getAllProducts());
	}

	@Override
	public List<EvaluationGroup> getAllEvaluationGroups() {
		return new LinkedList<EvaluationGroup>(database.getAllEvaluationGroups());
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
	public Evaluator getEvaluatorById(int evaluatorId) {
		return database.getEvaluatorById(evaluatorId);
	}
	
}
