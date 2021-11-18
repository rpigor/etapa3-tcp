package app.business.domain;

import java.util.List;

public class Evaluator {

	private int id;
	private String name;
	private String state;
	private List<Category> interestCategories;
	
	public Evaluator(int id, String name, String state,
			List<Category> interestCategories) {
		this.id = id;
		this.name = name;
		this.state = state;
		this.interestCategories = interestCategories;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getState() {
		return state;
	}
	
	public List<Category> getInterestCategories() {
		return interestCategories;
	}

}
