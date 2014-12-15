package pa.iscde.outline.utility;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;

import pa.iscde.outline.view.OutlineView;

public class OutlineServicesImplementation implements OutlineServices {

	@Override
	public ASTNode getSelectedNode() {
		return OutlineView.getSingleton().getSelectedTreeItem();
	}

	@Override
	public void selectNode(ASTNode node) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addListener(OutlineListener listener) {
		OutlineActivator.getInstance().addListener(listener);
	}

	@Override
	public void removeListener(OutlineListener listener) {
		OutlineActivator.getInstance().removeListener(listener);
	}

}
