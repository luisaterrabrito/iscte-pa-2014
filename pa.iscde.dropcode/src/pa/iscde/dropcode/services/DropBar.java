package pa.iscde.dropcode.services;

import org.eclipse.swt.widgets.Composite;

public interface DropBar {
	/**
	 * @return the title of your bar
	 */
	public String getName();

	/**
	 * @param parent
	 */
	public void createContents(Composite parent);

}
