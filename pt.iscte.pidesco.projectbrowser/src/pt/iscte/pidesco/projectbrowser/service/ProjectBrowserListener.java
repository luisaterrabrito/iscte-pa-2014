package pt.iscte.pidesco.projectbrowser.service;

import java.util.Collection;

import pt.iscte.pidesco.projectbrowser.model.SourceElement;

/**
 * Represents a listener to Project Browser events.
 */
public interface ProjectBrowserListener {

	/**
	 * Invoked whenever a mouse double-click is performed on a source element of the tree.
	 * @param element (non-null) element under the double-click
	 */
	void doubleClick(SourceElement element);
	
	/**
	 * Invoked whenever the selection of elements in the tree changes.
	 * @param selection (non-null) collection of elements of the new selection, which may be empty
	 */
	void selectionChanged(Collection<SourceElement> selection);
	
	
	/**
	 * Interface adapter for convenience. Default implementations do nothing.
	 */
	public class Adapter implements ProjectBrowserListener {

		/**
		 * Does nothing.
		 */
		@Override
		public void doubleClick(SourceElement element) {
			
		}

		/**
		 * Does nothing.
		 */
		@Override
		public void selectionChanged(Collection<SourceElement> selection) {
			
		}
	}
	
}
