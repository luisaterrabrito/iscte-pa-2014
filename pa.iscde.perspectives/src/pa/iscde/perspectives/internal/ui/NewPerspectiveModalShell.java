package pa.iscde.perspectives.internal.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

public class NewPerspectiveModalShell extends Shell
{
	public NewPerspectiveModalShell(Shell parentShell)
	{
		super(parentShell, SWT.PRIMARY_MODAL | SWT.SHEET);
		this.setLayout(new FillLayout());
		this.setText("Create Perspective");

		final Tree tree = new Tree(this, 0);

		for (int i = 0; i < 10; i++)
		{
			TreeItem item = new TreeItem(tree, SWT.NONE);
			item.setText("Parent " + i);

			for (int j = 0; j < 3; j++)
			{
				TreeItem child = new TreeItem(item, SWT.NONE);
				child.setText("Child " + i + " " + j);
			}
		}

		final Menu menu = new Menu(tree);
		tree.setMenu(menu);
		menu.addMenuListener(new MenuAdapter()
		{
			public void menuShown(MenuEvent e)
			{
				MenuItem[] items = menu.getItems();
				for (int i = 0; i < items.length; i++)
				{
					items[i].dispose();
				}
				MenuItem newItem = new MenuItem(menu, SWT.NONE);
				newItem.setText("Menu for " + tree.getSelection()[0].getText());
			}
		});
		Button closeButton = new Button(this, SWT.PUSH);
		closeButton.setText("Close");
		closeButton.addSelectionListener(new SelectionListener()
		{

			public void widgetSelected(SelectionEvent e)
			{
				NewPerspectiveModalShell.this.dispose();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0)
			{

			}
		});
		this.setDefaultButton(closeButton);
		this.addDisposeListener(new DisposeListener()
		{

			public void widgetDisposed(DisposeEvent e)
			{
				System.out.println("Modal dialog closed");
			}
		});
		this.pack();
	}
	protected void checkSubclass()
	{
	}
}
