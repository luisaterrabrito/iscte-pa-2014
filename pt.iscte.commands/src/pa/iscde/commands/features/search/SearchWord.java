package pa.iscde.commands.features.search;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.widgets.Display;

import pa.iscde.commands.services.Command;

final public class SearchWord implements Command {

	private SearchInputDialog dialog;
	private FileSearchResults searchResult;

	@Override
	public void action() {
		DialogInputData data = new DialogInputData();
		dialog = new SearchInputDialog(Display.getCurrent().getActiveShell(),
				data);

		int result = dialog.open();

		if (result == Dialog.OK && data.getText().length() > 0) {
			searchResult = new FileSearchResults(Display.getCurrent()
					.getActiveShell(), data);

			searchResult.open();
		}
	}

	final class DialogInputData {

		private String text;

		private boolean isCaseSensitive = false;

		public void setCaseSensitive(boolean isCaseSensitive) {
			this.isCaseSensitive = isCaseSensitive;
		}

		public void setText(String text) {
			this.text = text;
		}

		public String getText() {
			return text;
		}

		public boolean isCaseSensitive() {
			return isCaseSensitive;
		}

	}

}
