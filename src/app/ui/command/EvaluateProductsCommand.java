package app.ui.command;

import java.util.LinkedList;
import java.util.List;

import app.business.domain.Evaluation;
import app.business.domain.Evaluator;
import app.business.domain.Product;
import app.data.Database;
import app.ui.UIUtils;

public class EvaluateProductsCommand extends Command {
	
	private static final int MIN_RATING = -3;
	private static final int MAX_RATING = 3;
	
	public EvaluateProductsCommand(Database database) {
		super(database);
	}
	
	@Override
	public void execute() {
		List<Product> products = new LinkedList<Product>(database.getAllProducts());
		
		if (products.isEmpty()) {
			System.out.println("N�o h� produtos para avaliar.");
			return;
		}
		
		printProducts(products);
		
		Product selectedProduct = selectProduct();
		System.out.println("Produto selecionado: " + selectedProduct.getName());
		
		List<Evaluator> allowedEvaluators = selectedProduct.getAllowedEvaluators();
		
		if (allowedEvaluators.isEmpty()) {
			System.out.println("N�o h� avaliadores alocados para este produto.");
			return;
		}
		
		printEvaluators(allowedEvaluators);
		
		Evaluator selectedEvaluator = selectEvaluator(allowedEvaluators);
		System.out.println("Avaliador selecionado: " + selectedEvaluator.getName());
		
		int rating = readRating();
		
		Evaluation evaluation = selectedEvaluator.getPendingEvaluationForProduct(selectedProduct);
		evaluation.rate(rating);
		
		System.out.println("Opera��o efetuada com sucesso.");
		System.out.println("Avalia��o: nota " + evaluation.getRating()
				+ " para o produto " + selectedProduct.getName() + " (id " + selectedProduct.getId() + ")"
				+ " pelo(a) avaliador(a) " + selectedEvaluator.getName() + " (id " + selectedEvaluator.getId() + ").");	
	}
	
	@Override
	public String toString() {
		return "Avaliar Produto";
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
			selectedProduct = database.getProductById(productId);
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
			selectedEvaluator = database.getEvaluatorById(evaluatorId);
		}
		
		return selectedEvaluator;
	}
	
	private int readRating() {
		System.out.print("Entre com a nota da avalia��o (entre " + MIN_RATING + " e " + MAX_RATING + "): ");
		int rating = UIUtils.INSTANCE.readInteger(MIN_RATING, MAX_RATING);
		
		return rating;
	}
	
}
