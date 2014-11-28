package pa.iscde.commands.internal.services;

import pa.iscde.commands.models.CommandDataDecorator;

public interface Action {

	public void action(CommandDataDecorator data);
}
