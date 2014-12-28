package pt.iscte.pidesco.documentation.internal;

import java.util.HashMap;
import java.util.Map;

public class MethodDoc {
	
	private String name;
	private String signature;
	private String comment;
	
	private Map<String, String> tags;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSignature() {
		return signature;
	}
	
	public void setSignature(String signature) {
		this.signature = signature;
	}
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Map<String, String> getTags() {
		if (tags == null)
			tags = new HashMap<String, String>();
		
		return tags;
	}
	
	public void setTags(Map<String, String> tags) {
		this.tags = tags;
	}


}
