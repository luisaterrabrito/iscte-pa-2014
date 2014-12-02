package pt.iscte.pidesco.documentation.internal;

import java.util.List;

import pt.iscte.pidesco.documentation.service.ITagComment;

public class MethodDoc {

	private String methodName;
	private String comment;
	private List<ITagComment> tags;

	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
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

}
