package app.business.domain;

import java.util.List;

public class EvaluationGroup {

	private String name;
	private List<Evaluator> members;
	
	public EvaluationGroup(String name, List<Evaluator> members) {
		this.name = name;
		this.members = members;
	}
	
	public String getName() {
		return name;
	}
	
	public List<Evaluator> getMembers() {
		return members;
	}

}
