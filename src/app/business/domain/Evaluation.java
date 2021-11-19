package app.business.domain;

public class Evaluation {
	
	private Evaluator evaluator;
	private Product product;
	private int rating;
	private Status status;
	
	public Evaluation(Evaluator evaluator, Product product, int rating) {
		this.evaluator = evaluator;
		this.product = product;
		this.rating = rating;
		this.status = Status.COMPLETED;
	}
	
	public Evaluation(Evaluator evaluator, Product product) {
		this.evaluator = evaluator;
		this.product = product;
		this.status = Status.PENDING;
	}

	public Evaluator getEvaluator() {
		return evaluator;
	}
	
	public Product getProduct() {
		return product;
	}
	
	public int getRating() {
		return rating;
	}
	
	public boolean isCompleted() {
		return status.equals(Status.COMPLETED);
	}
	
	public boolean isPending() {
		return status.equals(Status.PENDING);
	}

	public void rate(int rating) {
		this.rating = rating;
		this.status = Status.COMPLETED;
	}

}
