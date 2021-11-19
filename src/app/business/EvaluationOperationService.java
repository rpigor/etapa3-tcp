package app.business;

import java.util.List;

import app.business.domain.Evaluation;
import app.business.domain.EvaluationGroup;
import app.business.domain.Evaluator;
import app.business.domain.Product;

public interface EvaluationOperationService {
	
	public Evaluation rate(Product product, Evaluator evaluator, int rating);
	
	public void allow(EvaluationGroup selectedGroup, int evaluatorsPerProduct);
	
	public List<Product> getAllProducts();
	
	public List<Product> getProductsByGroup(EvaluationGroup selectedGroup);

	public List<EvaluationGroup> getAllEvaluationGroups();

	public List<EvaluationGroup> getPendingEvaluationGroups();
	
	public EvaluationGroup getEvaluationGroupByName(String groupName);

	public Product getProductById(int productId);

	public Evaluator getEvaluatorById(int evaluatorId);
	
}
