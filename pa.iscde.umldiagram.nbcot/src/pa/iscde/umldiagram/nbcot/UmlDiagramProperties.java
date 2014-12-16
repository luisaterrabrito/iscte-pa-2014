package pa.iscde.umldiagram.nbcot;

import java.util.List;
import java.util.Vector;

import org.eclipse.zest.core.widgets.Graph;

import pa.iscde.configurator.model.interfaces.PropertyProvider;

/**
 * this class implements PropertyProvider from configurator setting the umldiagram plugin properties
 * @author Nuno Coelho e Diogo Peres
 *
 */
public class UmlDiagramProperties implements PropertyProvider {

	private Vector<String> ret = new Vector<>();
	private String property = UmlView.getInstance().getProperties();
	private String description = "This class draws umldiagrams of the classes of the selected package. These are the class properties.";

	public UmlDiagramProperties() {
		ret.add(property);
	}

	@Override
	public List<String> getProperties() {
		return ret;
	}

	@Override
	public String getValue(String property) {
		return this.description;
	}

}
