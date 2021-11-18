package app.data;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import app.business.domain.Category;
import app.business.domain.EvaluationGroup;
import app.business.domain.Evaluator;
import app.business.domain.Product;

public class Database {

	private final Map<Integer, Evaluator> evaluators;
	private final Map<String, EvaluationGroup> evaluationGroups;
	private final Map<Integer, Product> products;
	
	public Database() {
		this(true);
	}
	
	public Database(boolean initData) {
		this.evaluators = new HashMap<>();
		this.evaluationGroups = new HashMap<>();
		this.products = new HashMap<>();
		
		if (initData)
			initData();
	}

	public Collection<Evaluator> getAllEvaluators() {
		return this.evaluators.values();
	}
	
	public Collection<EvaluationGroup> getAllEvaluationGroups() {
		return this.evaluationGroups.values();
	}
	
	public Collection<Product> getAllProducts() {
		return this.products.values();
	}
	
	public EvaluationGroup getEvaluationGroupByName(String groupName) {
		return this.evaluationGroups.get(groupName);
	}
	
	public Product getProductById(int productId) {
		return this.products.get(productId);
	}

	public Evaluator getEvaluatorById(int evaluatorId) {
		return this.evaluators.get(evaluatorId);
	}
	
	
	private void initData() {
		// evaluators
		Evaluator e1 = new Evaluator(1, "João", "RS",
				new LinkedList<Category>(Arrays.asList(Category.BB_CREAM, Category.CC_CREAM, Category.DD_CREAM)));
		save(e1);
		
		Evaluator e2 = new Evaluator(2, "Ana", "SP",
				new LinkedList<Category>(Arrays.asList(Category.FOUNDATION_SPF, Category.CC_CREAM, Category.DD_CREAM)));
		save(e2);
		
		Evaluator e3 = new Evaluator(3, "Manoela", "RS",
				new LinkedList<Category>(Arrays.asList(Category.BB_CREAM, Category.OIL_FREE_MATTE_SPF)));
		save(e3);
		
		Evaluator e4 = new Evaluator(4, "Joana", "CE",
				new LinkedList<Category>(Arrays.asList(Category.BB_CREAM, Category.CC_CREAM, Category.FOUNDATION_SPF, Category.POWDER_SUNSCREEN)));
		save(e4);
		
		Evaluator e5 = new Evaluator(5, "Miguel", "RS",
				new LinkedList<Category>(Arrays.asList(Category.FOUNDATION_SPF, Category.DD_CREAM, Category.OIL_FREE_MATTE_SPF)));
		save(e5);
		
		Evaluator e6 = new Evaluator(6, "Beatriz", "CE",
				new LinkedList<Category>(Arrays.asList(Category.OIL_FREE_MATTE_SPF, Category.CC_CREAM, Category.POWDER_SUNSCREEN)));
		save(e6);
		
		Evaluator e7 = new Evaluator(7, "Suzana", "RS",
				new LinkedList<Category>(Arrays.asList(Category.POWDER_SUNSCREEN, Category.CC_CREAM, Category.DD_CREAM)));
		save(e7);
		
		Evaluator e8 = new Evaluator(8, "Natasha", "CE",
				new LinkedList<Category>(Arrays.asList(Category.BB_CREAM, Category.CC_CREAM, Category.DD_CREAM)));
		save(e8);
		
		Evaluator e9 = new Evaluator(9, "Pedro", "SP",
				new LinkedList<Category>(Arrays.asList(Category.POWDER_SUNSCREEN, Category.FOUNDATION_SPF)));
		save(e9);
		
		Evaluator e10 = new Evaluator(10, "Carla", "SP",
				new LinkedList<Category>(Arrays.asList(Category.CC_CREAM)));
		save(e10);
		
		// evaluation groups
		EvaluationGroup eg1 = new EvaluationGroup("SPF A",
				new LinkedList<Evaluator>(Arrays.asList(e1, e2, e3, e4, e5, e6, e7)));
		save(eg1);
		
		EvaluationGroup eg2 = new EvaluationGroup("SPF B",
				new LinkedList<Evaluator>(Arrays.asList(e1, e2, e3, e4, e5, e6, e7)));
		save(eg2);
		
		EvaluationGroup eg3 = new EvaluationGroup("SPF C",
				new LinkedList<Evaluator>(Arrays.asList(e4, e5, e6, e7, e8, e9, e10)));
		save(eg3);
		
		// products
		Product p1 = new Product(1, "L'oreal DD Cream", evaluators.get(1),
				evaluationGroups.get("SPF C"), Category.DD_CREAM);
		save(p1);
		
		Product p2 = new Product(2, "Avon CC Cream ", evaluators.get(6),
				evaluationGroups.get("SPF B"), Category.DD_CREAM);
		save(p2);
		
		Product p3 = new Product(3, "Revolution Powder Sunscreeen", evaluators.get(7),
				evaluationGroups.get("SPF B"), Category.DD_CREAM);
		save(p3);
		
		Product p4 = new Product(4, "Maybelline BB Cream", evaluators.get(8),
				evaluationGroups.get("SPF B"), Category.DD_CREAM);
		save(p4);
		
		Product p5 = new Product(5, "Revlon Foundation+SPF20 ", evaluators.get(9),
				evaluationGroups.get("SPF B"), Category.DD_CREAM);
		save(p5);
		
		Product p6 = new Product(6, "Nivea Matte Face SPF", evaluators.get(10),
				evaluationGroups.get("SPF B"), Category.DD_CREAM);
		save(p6);
		
		Product p7 = new Product(7, "La Roche CC Cream", evaluators.get(6),
				evaluationGroups.get("SPF A"), Category.DD_CREAM);
		save(p7);
		
		Product p8 = new Product(8, "Yves Rocher Powder+SPF15", evaluators.get(7),
				evaluationGroups.get("SPF A"), Category.DD_CREAM);
		save(p8);
		
		Product p9 = new Product(9, "Nivea BB Cream", evaluators.get(8),
				evaluationGroups.get("SPF A"), Category.DD_CREAM);
		save(p9);
		
		Product p10 = new Product(10, "Base O Boticário SPF20", evaluators.get(9),
				evaluationGroups.get("SPF A"), Category.DD_CREAM);
		save(p10);
		
		Product p11 = new Product(11, "Natura SPF20 Rosto Matte", evaluators.get(10),
				evaluationGroups.get("SPF A"), Category.DD_CREAM);
		save(p11);
	}
	
	private void save(Evaluator evaluator) {
		this.evaluators.put(evaluator.getId(), evaluator);
	}
	
	private void save(EvaluationGroup evaluationGroup) {
		this.evaluationGroups.put(evaluationGroup.getName(), evaluationGroup);
	}
	
	private void save(Product product) {
		this.products.put(product.getId(), product);
	}

}
