package pa.iscde.packagediagram.provider;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.zest.core.viewers.IGraphEntityContentProvider;

import pa.iscde.packagediagram.model.NodeModel;

public class ContentProvider extends ArrayContentProvider  implements IGraphEntityContentProvider{


	// ligações entre nós
	@Override
	public Object[] getConnectedTo(Object entity) {
	    if (entity instanceof NodeModel) {
	    	NodeModel node = (NodeModel) entity;
	        return node.getConnectedTo().toArray();
	      }
	      throw new RuntimeException("Type not supported");
	}

}
