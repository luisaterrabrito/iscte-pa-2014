package pt.iscte.pidesco.documentation.internal;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import pt.iscte.pidesco.documentation.service.ITagComment;

public class ClassDoc {
	
	private static ClassDoc instance;

	private String classFullName;
	private String comment;
	private List<ITagComment> tags;
	private List<MethodDoc> listMethods;
	
	public String getClassFullName() {
		return classFullName;
	}

	/**
	 * 
	 * @param classFullName nome da classe
	 */
	public void setClassFullName(String classFullName) {
		this.classFullName = classFullName;
	}

	public String getComment() {
		return comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public List<ITagComment> getTags() {
		if (tags == null)
			tags = new ArrayList<ITagComment>();
		
		return tags;
	}
	
	public void setTags(List<ITagComment> tags) {
		this.tags = tags;
	}
	
	public List<MethodDoc> getListMethods() {
		if (listMethods == null)
			listMethods = new ArrayList<MethodDoc>();
		
		return listMethods;
	}
	
	public void setListMethods(List<MethodDoc> listMethods) {
		this.listMethods = listMethods;
	}
	
	public static ClassDoc getInstance() {
		return instance;
	}

	public StringBuilder toHTML() {
		StringBuilder sb = new StringBuilder(); 
		
		sb.append("<br><b>Classe: </b>" + comment + "\n");
		
		for (ITagComment tag : tags) {
			sb.append("<b>" + tag.getTagName() + ": </b>" + tag.getTagText() + "\n"); 
		}
		
		return sb;
	}
}
