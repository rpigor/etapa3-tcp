package app.ui.command;

import app.data.Database;

public abstract class Command {
	
	protected final Database database;
	
	public Command(Database database) {
		this.database = database;
	}
	
	public abstract void execute();

}
