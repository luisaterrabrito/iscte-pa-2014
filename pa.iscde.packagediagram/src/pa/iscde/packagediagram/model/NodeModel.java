package pa.iscde.packagediagram.model;


	import java.util.ArrayList;
import java.util.List;

	public class NodeModel {
	  private final String name;
	  private List<NodeModel> connections;

	  public NodeModel(String name) {

	    this.name = name;
	    this.connections = new ArrayList<NodeModel>();
	  }

	  public String getName() {
	    return name;
	  }

	  public List<NodeModel> getConnectedTo() {
	    return connections;
	  }

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NodeModel other = (NodeModel) obj;

		if (name == null) {
			if (other.name != null)
				return false;
		} else if (name.compareTo(other.name) !=0)
			return false;
		return true;
	}

	} 
	
	



