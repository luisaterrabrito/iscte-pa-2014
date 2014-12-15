package pa.iscde.dropcode.services;

import org.eclipse.swt.widgets.Composite;

public interface DropBar {
	/**
	 * This method is used to define the title of the DropBar that is being created.
	 * @return the title of your bar
	 */
	public String getName();

	/**
	 * This method receives a Composite and adds it to the DropCode view.
	 * @param parent
	 */
	public void createContents(Composite parent);

}
