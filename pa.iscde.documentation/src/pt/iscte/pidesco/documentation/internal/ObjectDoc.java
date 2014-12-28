package pt.iscte.pidesco.documentation.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectDoc {
	
	private String fullName;
	private String comment;
	
	private Map<String, String> tags;
	
	//private List<ConstrutorDoc> listConstrutors;
	private List<MethodDoc> listMethods;
	
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
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
	
	public List<MethodDoc> getMethods() {
		if (listMethods == null)
			listMethods = new ArrayList<MethodDoc>();
		
		return listMethods;
	}
	
	public void setMethods(List<MethodDoc> listMethods) {
		this.listMethods = listMethods;
	}
	
}
