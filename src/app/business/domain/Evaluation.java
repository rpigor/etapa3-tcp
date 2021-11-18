package app.business.domain;

public class Evaluation {

	private Evaluator evaluator;
	private int rating;
	
	public Evaluation(Evaluator evaluator, int rating) {
		this.evaluator = evaluator;
		this.rating = rating;
	}
	
	public Evaluator getEvaluator() {
		return evaluator;
	}
	
	public int getRating() {
		return rating;
	}

}
