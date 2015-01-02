package pa.iscde.documentation.structure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is responsible for the structure of a Class/Interface object.
 * A Class/Interface will have a name, a comment, various tags (if exists), and various Construtors/Methods (if exists).
 * 
 * @author David Franco & João Gonçalves
 * @version 01.00
 */
public class ObjectDoc {
	
	private String name;
	private String comment;
	private Map<String, List<String>> tags;
	
	private List<ConstrutorDoc> listConstrutors;
	private List<MethodDoc> listMethods;
	
	/**
	 * Get Class/Interface Name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set Class/Interface Name.
	 * 
	 * @param name - class/interface name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Get Class/Interface Comment
	 * 
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}
	
	/**
	 * Set Class/Interface Comment.
	 * 
	 * @param comment - class/interface comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	/**
	 * Get a Map of the existing Tags in the Class/Interface
	 * 
	 * @return the tags grouped
	 */
	public Map<String, List<String>> getTags() {
		if (tags == null)
			tags = new HashMap<String, List<String>>();
		
		return tags;
	}
	
	/**
	 * Get the list of existing Construtors in the Class/Interface
	 * 
	 * @return the list of construtors
	 */
	public List<ConstrutorDoc> getConstrutors() {
		if (listConstrutors == null)
			listConstrutors = new ArrayList<ConstrutorDoc>();
		
		return listConstrutors;
	}

	/**
	 * Get the list of existing Methods in the Class/Interface
	 * 
	 * @return the list of methods
	 */
	public List<MethodDoc> getMethods() {
		if (listMethods == null)
			listMethods = new ArrayList<MethodDoc>();
		
		return listMethods;
	}
	
}
