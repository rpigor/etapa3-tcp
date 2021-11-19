package app.business.domain;

import java.util.List;

public class EvaluationGroup {

	private String name;
	private List<Evaluator> members;
	private Status status;
	
	public EvaluationGroup(String name, List<Evaluator> members) {
		this.name = name;
		this.members = members;
		this.status = Status.PENDING;
	}
	
	public String getName() {
		return name;
	}
	
	public List<Evaluator> getMembers() {
		return members;
	}

	public boolean isAllowed() {
		return status.equals(Status.COMPLETED);
	}
	
	public boolean isUnallowed() {
		return status.equals(Status.PENDING);
	}

	public void setAllowed() {
		status = Status.COMPLETED;
	}

}
