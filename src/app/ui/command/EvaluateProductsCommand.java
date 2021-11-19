package app.ui.command;

import java.util.LinkedList;
import java.util.List;

import app.business.EvaluationOperationService;
import app.business.domain.Evaluation;
import app.business.domain.Evaluator;
import app.business.domain.Product;
import app.ui.UIUtils;

public class EvaluateProductsCommand implements Command {

	private static final int MIN_RATING = -3;
	private static final int MAX_RATING = 3;
	
	private final EvaluationOperationService evalOperationService;
	
	public EvaluateProductsCommand(EvaluationOperationService evalOperationService) {
		this.evalOperationService = evalOperationService;
	}
	
	@Override
	public void execute() {
		List<Product> products = evalOperationService.getAllProducts();
		
		if (products.isEmpty()) {
			System.out.println("Não há produtos para avaliar.");
			return;
		}
		
		printProducts(products);
		
		Product selectedProduct = selectProduct();
		System.out.println("Produto selecionado: " + selectedProduct.getName());
		
		List<Evaluator> allowedEvaluators = getAllowedEvaluatorsForProduct(selectedProduct);
		
		if (allowedEvaluators.isEmpty()) {
			System.out.println("Não há avaliadores alocados para este produto.");
			return;
		}
		
		printEvaluators(allowedEvaluators);
		
		Evaluator selectedEvaluator = selectEvaluator(allowedEvaluators);
		System.out.println("Avaliador selecionado: " + selectedEvaluator.getName());
		
		int rating = readRating();
		Evaluation evaluation = evalOperationService.rate(selectedProduct, selectedEvaluator, rating);
		
		System.out.println("Operação efetuada com sucesso.");
		System.out.println("Avaliação: nota " + evaluation.getRating()
				+ " para o produto " + selectedProduct.getName() + " (id " + selectedProduct.getId() + ")"
				+ " pelo avaliador " + selectedEvaluator.getName() + " (id " + selectedEvaluator.getId() + ").");	
	}
	
	private void printProducts(List<Product> products) {
		System.out.println("Produtos:");
		for (Product product : products) {
			System.out.println(product.getId() + " - " + product.getName());
		}
	}
	
	private Product selectProduct() {
		int productId = -1;
		
		Product selectedProduct = null;
		while (selectedProduct == null) {
			System.out.print("Entre com o id de um produto: ");
			productId = UIUtils.INSTANCE.readInteger();
			selectedProduct = evalOperationService.getProductById(productId);
		}
		
		return selectedProduct;
	}
	
	private void printEvaluators(List<Evaluator> evaluators) {		
		System.out.println("Avaliadores: ");
		for (Evaluator evaluator : evaluators) {
			System.out.println(evaluator.getId() + " - " + evaluator.getName());
		}
	}
	
	private Evaluator selectEvaluator(List<Evaluator> evaluators) {
		int evaluatorId = -1;
		
		Evaluator selectedEvaluator = null;
		while (selectedEvaluator == null || !evaluators.contains(selectedEvaluator)) {
			System.out.print("Entre com o id de um avaliador: ");
			evaluatorId = UIUtils.INSTANCE.readInteger();
			selectedEvaluator = evalOperationService.getEvaluatorById(evaluatorId);
		}
		
		return selectedEvaluator;
	}
	
	private List<Evaluator> getAllowedEvaluatorsForProduct(Product product) {
		List<Evaluator> allowedEvaluators = new LinkedList<Evaluator>();
		for (Evaluator evaluator : product.getEvaluationGroup().getMembers()) {
			for (Evaluation evaluation : evaluator.getEvaluations()) {
				if (evaluation.getProduct().equals(product) && evaluation.isPending()) {
					allowedEvaluators.add(evaluator);
					break;
				}
			}
		}
		
		return allowedEvaluators;
	}
	
	private int readRating() {
		System.out.print("Entre com a nota da avaliação (entre " + MIN_RATING + " e " + MAX_RATING + "): ");
		int rating = UIUtils.INSTANCE.readInteger(MIN_RATING, MAX_RATING);
		
		return rating;
	}
	
}
