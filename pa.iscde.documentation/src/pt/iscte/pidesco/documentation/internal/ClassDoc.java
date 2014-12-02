package pt.iscte.pidesco.documentation.internal;

import java.util.List;

import pt.iscte.pidesco.documentation.service.ITagComment;

public class ClassDoc {

	private String classFullName;
	private String comment;
	private List<ITagComment> tags;
	private List<MethodDoc> listMethods;

	public String getClassFullName() {
		return classFullName;
	}

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
		return tags;
	}
	
	public void setTags(List<ITagComment> tags) {
		this.tags = tags;
	}
	
	public List<MethodDoc> getListMethods() {
		return listMethods;
	}
	
	public void setListMethods(List<MethodDoc> listMethods) {
		this.listMethods = listMethods;
	}

}
