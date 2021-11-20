package app.business;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import app.business.domain.EvaluationGroup;
import app.business.domain.Evaluator;
import app.business.domain.Product;

public interface EvaluationOperationService {
	
	public void allow(EvaluationGroup selectedGroup, int evaluatorsPerProduct);
	
	public Entry<Map<Product, Double>, Map<Product, Double>> getAcceptableAndUnacceptableProductsMean(EvaluationGroup selectedGroup, double thresholdRating);
	
	public List<Product> getAllProducts();
	
	public List<Product> getProductsByGroup(EvaluationGroup selectedGroup);

	public List<EvaluationGroup> getAllEvaluationGroups();

	public List<EvaluationGroup> getPendingEvaluationGroups();
	
	public EvaluationGroup getEvaluationGroupByName(String groupName);

	public Product getProductById(int productId);

	public Evaluator getEvaluatorById(int evaluatorId);
	
}
