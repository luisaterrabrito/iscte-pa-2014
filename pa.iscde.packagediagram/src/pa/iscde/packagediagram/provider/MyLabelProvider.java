package pa.iscde.packagediagram.provider;

import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;
import org.eclipse.zest.core.viewers.EntityConnectionData;

import pa.iscde.packagediagram.internal.PackageDiagramView.ChangeColor;
import pa.iscde.packagediagram.model.ConnectionModel;
import pa.iscde.packagediagram.model.NodeModel;




public class MyLabelProvider extends LabelProvider implements IColorProvider{
	
	// todas as extensões carregadas
	private Map<String, ChangeColor> colorsMap;
	// as extensões ativadas?
	private Set<String> activeChangeColor = new HashSet<String>();

	
	public MyLabelProvider (Map<String, ChangeColor> colorsMap) {
		this.colorsMap=colorsMap;
			
		for(Entry<String, ChangeColor> entry : colorsMap.entrySet()) {
		    String key = entry.getKey();
		    activeChangeColor.add(key);
		    // do what you have to do here
		    // In your case, an other loop.
		}
			
	}
	
	// activa a mudança de cor
	public void activateChange(String id) {
		if(colorsMap.containsKey(id)) {
			activeChangeColor.add(id);
		}
		else {
			MessageDialog.openError(Display.getDefault().getActiveShell(), "Filter not found", "Filter with id=" + id + " not found");
		}
	}
	
	// desativa a mudança de cor
	public void deactivateChange(String id) {
		if(colorsMap.containsKey(id)) {
			activeChangeColor.remove(id);
		}
		else {
			MessageDialog.openError(Display.getDefault().getActiveShell(), "Filter not found", "Filter with id=" + id + " not found");
		}
	}
	
	// os textos que aparecem no view
	public String getText(Object element) {
		if (element instanceof NodeModel) {
		      NodeModel NodeModel = (NodeModel) element;
		      return NodeModel.getName();
		    }
		    // Not called with the IGraphEntityContentProvider
		    if (element instanceof ConnectionModel) {
		      ConnectionModel ConnectionModel = (ConnectionModel) element;
		      return ConnectionModel.getLabel();
		    }

		    if (element instanceof EntityConnectionData) {
		      EntityConnectionData test = (EntityConnectionData) element;
		      return "<<import>>";
		    }
		    throw new RuntimeException("Wrong type: "
		        + element.getClass().toString());
	}
	
	// Mudança de cor da letra
	@Override
	public Color getForeground(Object element) {
		for (String id : activeChangeColor) {
			ChangeColor changeColor = colorsMap.get(id);
			if (changeColor != null) {
				System.out.println("@@@@" + changeColor.getClass());
				if(element instanceof NodeModel) {
				Color color = changeColor.getForeground(((NodeModel)element).getName());
				if (color != null)
					System.out.println("@@@@" + color);
				return color;
				}
			}
		}

		return ColorConstants.black;
	}

	// Mudança de cor da classe, só no que estão ativos
	@Override
	public Color getBackground(Object element) {
		for (String id : activeChangeColor) {
			ChangeColor changeColor = colorsMap.get(id);
			if (changeColor != null) {
				System.out.println("@@@@" + changeColor.getClass());
				if(element instanceof NodeModel) {
				Color color = changeColor.getBackground(((NodeModel)element).getName());
				if (color != null)
					System.out.println("@@@@" + color);
				return color;
				}
			}
		}

		return ColorConstants.orange;
	}


}
