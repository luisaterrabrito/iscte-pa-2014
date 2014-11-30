package pa.iscde.commands.features.actions;

import pa.iscde.commands.internal.services.Action;
import pa.iscde.commands.models.CommandDataDecorator;

public class EditCommad implements Action {

	@Override
	public void action(CommandDataDecorator data) {

		System.out.println("EditCommad.action()");

	}

}
