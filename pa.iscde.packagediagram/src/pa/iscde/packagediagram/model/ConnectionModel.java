package pa.iscde.packagediagram.model;

/**
 * Represents the connections model
 */

public class ConnectionModel {

 
	  private final String label; 
	  private final NodeModel source;
	  private final NodeModel destination;
	  
	  public ConnectionModel(String label, NodeModel source, NodeModel destination) {

	    this.label = label;
	    this.source = source;
	    this.destination = destination;
	    source.getConnectedTo().add(destination);
	    
	  }
	  
	  /**
	   * 
	   * @return label
	   */
	  public String getLabel() {
	    return label;
	  }
	  
	  /**
	   * 
	   * @return source
	   */
	  public NodeModel getSource() {
	    return source;
	  }
	  
	  /**
	   * 
	   * @return destination
	   */
	  public NodeModel getDestination() {
	    return destination;
	  }
	  
}
