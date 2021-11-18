package app.ui;

import java.util.LinkedHashMap;
import java.util.Map;

import app.ui.command.Command;
import app.business.EvaluationOperationService;
import app.ui.command.AllowProductsCommand;
import app.ui.command.EvaluateProductsCommand;
import app.ui.command.SelectProductsCommand;

public class TextInterface {
	
	public static final String EXIT_CODE = "E";
	
	private final Map<String, Command> commands;
	
	public TextInterface(EvaluationOperationService accountOperationService) {
		this.commands = new LinkedHashMap<>();
		this.commands.put("A", new AllowProductsCommand(accountOperationService));
		this.commands.put("S", new SelectProductsCommand(accountOperationService));
		this.commands.put("R", new EvaluateProductsCommand(accountOperationService));
	}
	
	public void createAndShowUI() {
		UIUtils uiUtils = UIUtils.INSTANCE;
		String commandKey = null;
		do {
			System.out.println();
			System.out.print(getMenu());
			commandKey = uiUtils.readString();
			Command command = (Command) commands.get(commandKey);
			if (command != null) {
				try {
					command.execute();
				} catch (Exception e) {
					uiUtils.handleUnexceptedError(e);
				}
			}
		} while (!EXIT_CODE.equals(commandKey));
	}
	
	private String getMenu() {
		StringBuffer sb = new StringBuffer();
		sb.append("Opções (ou " + EXIT_CODE + " para sair):\n");
		for (String key : commands.keySet()) {
			Command command = commands.get(key);
			sb.append(key)
					.append(" - ")
					.append(command.getClass().getSimpleName())
					.append("\n");
		}
		sb.append("Escolha uma opção: ");

		return sb.toString();
	}

}
