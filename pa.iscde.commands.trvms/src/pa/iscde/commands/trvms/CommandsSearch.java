package pa.iscde.commands.trvms;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;
import pa.iscde.commands.*;

import pa.iscde.filtersearch.providers.SearchProvider;


public class CommandsSearch implements SearchProvider {

	public CommandsSearch() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Object> getResults(String text) {
		return new LinkedList<Object>(Arrays.asList(CommandWarehouse.getInstance().getAllCommandDefinitions().toArray()));
	}

	@Override
	public Image setImage(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void doubleClickAction(TreeViewer tree, Object object) {
		System.out.println("Cliquei: " + object);

	}

}
