package pa.iscde.commands.features.search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;
import org.osgi.framework.ServiceReference;

import pa.iscde.commands.controllers.CommandsController;
import pa.iscde.commands.features.search.SearchWord.DialogInputData;
import pa.iscde.commands.utils.Labels;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

final class FileSearchResults extends Dialog {

	private Tree tree;
	private JavaEditorServices javaEditorService;
	private DialogInputData data;
	private File file;

	public FileSearchResults(Shell parentShell, DialogInputData data) {
		super(parentShell);
		this.data = data;
		ServiceReference<JavaEditorServices> ref = CommandsController
				.getContext().getServiceReference(JavaEditorServices.class);
		javaEditorService = CommandsController.getContext().getService(ref);
		setShellStyle(SWT.CLOSE | SWT.MODELESS | SWT.BORDER | SWT.TITLE);
		setBlockOnOpen(false);

	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);

		tree = new Tree(container, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL
				| SWT.FULL_SELECTION);

		GridData searchLayout = new GridData();
		searchLayout.horizontalAlignment = SWT.FILL;
		searchLayout.grabExcessHorizontalSpace = true;
		searchLayout.verticalAlignment = SWT.FILL;
		searchLayout.grabExcessVerticalSpace = true;

		tree.setLayoutData(searchLayout);
		tree.setLinesVisible(true);

		TreeColumn file = new TreeColumn(tree, SWT.LEFT);
		file.setText(Labels.DESCRIPTIONCOLUMN);
		file.setWidth(450);
		TreeColumn line = new TreeColumn(tree, SWT.LEFT);
		line.setText(Labels.LINECOLUMN);
		line.setWidth(50);

		tree.setHeaderVisible(true);

		tree.addListener(SWT.MeasureItem, new Listener() {

			@Override
			public void handleEvent(Event event) {
				event.height = 20;
			}
		});
		addSearchData();

		tree.addListener(SWT.Selection, new Listener() {
			public void handleEvent(Event e) {
				// So é permitido um item selecionado de cada vez
				TreeItem[] selection = tree.getSelection();

				if (selection[0].getData() instanceof SearchData) {
					SearchData searchData = (SearchData) selection[0].getData();
					javaEditorService.selectText(searchData.getFile(),
							searchData.getOffset(), searchData.getLength());
				}

			}
		});

		return super.createDialogArea(parent);

	}

	@Override
	public int open() {

		if (javaEditorService.getOpenedFile() != null) {
			return super.open();
		}
		return Dialog.CANCEL;
	}

	private void addSearchData() {
		file = javaEditorService.getOpenedFile();

		TreeItem fileName = new TreeItem(tree, SWT.NONE);
		fileName.setText(file.getName());

		scanFile(fileName);

	}

	private void scanFile(TreeItem fileName) {
		Scanner sc = null;

		try {
			sc = new Scanner(file);
			boolean found = processLine(fileName, sc);

			if (!found) {
				TreeItem item = new TreeItem(fileName, SWT.NONE);
				item.setText(new String[] { Labels.NOSEARCHRESULTS, "" });
				fileName.setExpanded(true);
			}

		} catch (FileNotFoundException e) {
			System.err
					.println("SearchResults.addData(). File error because file was closed before concluding analysis.");
		} finally {
			if (sc != null)
				sc.close();
		}
	}

	private boolean processLine(TreeItem fileName, Scanner sc) {
		int line = 0;
		int wordCount = 0;
		boolean found = false;
		int length = -1;

		String regex = "(?i:(.*)(" + data.getText() + ")(.*))";
		if (data.isCaseSensitive())
			regex = "(.*)(" + data.getText() + ")(.*)";

		String str = null;
		while (sc.hasNextLine()) {
			line++;
			str = sc.nextLine();

			if (str.matches(regex)) {
				length = str.indexOf(data.getText(), 0);
				// procurar na linha a string
				while (length != -1) {
					TreeItem item = new TreeItem(fileName, SWT.NONE);
					item.setText(new String[] { str, line + "" });
					item.setData(new SearchData(wordCount + length, data
							.getText().length(), file));
					fileName.setExpanded(true);
					found = true;
					length = str.indexOf(data.getText(), length + 1);
				}

			}
			// E necesario somar mais 2 por cada linha tem mais dois caracteres.
			// \n e \r
			wordCount = wordCount + str.length() + 2;
		}
		return found;
	}

	@Override
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText(Labels.SEARCHRESULTSTITLE_LBL + " \"" + data.getText()
				+ "\"");
	}

	@Override
	protected Point getInitialSize() {
		return new Point(550, 400);
	}

	private class SearchData {

		private int offset;
		private int length;
		private File file;

		public SearchData(int offset, int length, File file) {
			super();
			this.offset = offset;
			this.length = length;
			this.file = file;
		}

		public int getLength() {
			return length;
		}

		public int getOffset() {
			return offset;
		}

		public File getFile() {
			return file;
		}

	}

}
