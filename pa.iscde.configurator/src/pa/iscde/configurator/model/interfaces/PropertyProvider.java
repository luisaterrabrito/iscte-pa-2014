/*
 * Interface used to extend pa.iscde.configurator.propertyprovider extension Point
 * @author Joao Diogo Medeiros & Luis Dias
 * 
 */
package pa.iscde.configurator.model.interfaces;

import java.util.List;


public interface PropertyProvider {

	/*
	 * Get properties to insert on the view's information table.
	 * @return List of properties to insert on table
	 */
	List<String> getProperties();
	
	/*
	 * Get value associated with given property
	 * @param Property to get the value from
	 * @return Value of given property 
	 */
	String getValue(String property);
	
}
