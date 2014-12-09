package pa.iscde.packagediagram.model;


	import java.util.ArrayList;
import java.util.List;

	public class NodeModel {
	  private final String id;
	  private final String name;
	  private List<NodeModel> connections;

	  public NodeModel(String id, String name) {
	    this.id = id;
	    this.name = name;
	    this.connections = new ArrayList<NodeModel>();
	  }

	  public String getId() {
	    return id;
	  }

	  public String getName() {
	    return name;
	  }

	  public List<NodeModel> getConnectedTo() {
	    return connections;
	  }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (name.compareTo(other.name) !=0)
			return false;
		return true;
	}

	} 
	
	



