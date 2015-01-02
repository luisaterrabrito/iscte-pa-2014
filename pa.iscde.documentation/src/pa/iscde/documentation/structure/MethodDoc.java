package pa.iscde.documentation.structure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is responsible for the structure of a Method.
 * A Method will have a name, a signiture, a comment and various tags (if exists).
 * 
 * @author David Franco & João Gonçalves
 * @version 01.00
 */
public class MethodDoc {
	
	private String name;
	private String signature;
	private String comment;
	private Map<String, List<String>> tags;
	
	/**
	 * Get Method Name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set Method Name.
	 * 
	 * @param name - method name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get Method Signiture.
	 * 
	 * @return the signiture
	 */
	public String getSignature() {
		return signature;
	}
	
	/**
	 * Set Method Signiture.
	 * 
	 * @param signature - method signature
	 */
	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	/**
	 * Get Method Comment
	 * 
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Set Method Comment.
	 * 
	 * @param comment - method comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * Get a Map of the existing Tags in the Method
	 * 
	 * @return the tags grouped
	 */
	public Map<String, List<String>> getTags() {
		if (tags == null)
			tags = new HashMap<String, List<String>>();
		
		return tags;
	}

}
