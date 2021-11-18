package app.business;

import java.util.List;

import app.business.domain.Evaluation;
import app.business.domain.Evaluator;
import app.business.domain.Product;

public interface EvaluationOperationService {
	
	public Evaluation rate(Product product, Evaluator evaluator, int rating);
	
	public List<Product> getAllProducts();
	
}
