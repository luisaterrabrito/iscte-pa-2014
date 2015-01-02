package pa.iscde.documentation.structure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class is responsible for the structure of a Construtor.
 * A Construtor will have a name, a signiture, a comment and various tags (if exists).
 * 
 * @author David Franco & João Gonçalves
 * @version 01.00
 */
public class ConstrutorDoc {

	private String name;
	private String signature;
	private String comment;
	private Map<String, List<String>> tags;
	
	/**
	 * Get Construtor Name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Set Construtor Name.
	 * 
	 * @param name - construtor name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Get Construtor Signiture.
	 * 
	 * @return the signiture
	 */
	public String getSignature() {
		return signature;
	}
	
	/**
	 * Set Construtor Signiture.
	 * 
	 * @param signature - construtor signature
	 */
	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	/**
	 * Get Construtor Comment
	 * 
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * Set Construtor Comment.
	 * 
	 * @param comment - construtor comment
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * Get a Map of the existing Tags in the Construtor
	 * 
	 * @return the tags grouped
	 */
	public Map<String, List<String>> getTags() {
		if (tags == null)
			tags = new HashMap<String, List<String>>();
		
		return tags;
	}
	
}
