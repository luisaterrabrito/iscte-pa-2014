package pa.iscde.packagediagram.model;

public class ConnectionModel {

	  final String id; 
	  final String label; 
	  final NodeModel source;
	  final NodeModel destination;
	  
	  public ConnectionModel(String id, String label, NodeModel source, NodeModel destination) {
	    this.id = id;
	    this.label = label;
	    this.source = source;
	    this.destination = destination;
	    source.getConnectedTo().add(destination);
	    
	  }

	  public String getLabel() {
	    return label;
	  }
	  
	  public NodeModel getSource() {
	    return source;
	  }
	  public NodeModel getDestination() {
	    return destination;
	  }
	  
}
