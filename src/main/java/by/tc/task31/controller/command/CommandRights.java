package by.tc.task31.controller.command;

import by.tc.task31.entity.User;

import java.util.List;

public class CommandRights {
	private Command command;
	private List<User.Status> rights;

	CommandRights(List<User.Status> rights, Command command) {
		this.command = command;
		this.rights = rights;
	}

	public Command getCommand() {
		return command;
	}

	public void setCommand(Command command) {
		this.command = command;
	}

	public List<User.Status> getRights() {
		return rights;
	}

	public void setRights(List<User.Status> rights) {
		this.rights = rights;
	}
}