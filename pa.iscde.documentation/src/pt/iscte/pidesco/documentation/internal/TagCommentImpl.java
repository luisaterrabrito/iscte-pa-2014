package pt.iscte.pidesco.documentation.internal;

import pt.iscte.pidesco.documentation.service.ITagComment;

public class TagCommentImpl implements ITagComment {

	private String tagName;
	private String tagText;
	
	public TagCommentImpl(String tagName, String tagText) {
		this.tagName = tagName;
		this.tagText = tagText;
	}
	
	@Override
	public String getTagName() {
		return tagName;
	}

	@Override
	public String getTagText() {
		return tagText;
	}

	@Override
	public void setTagName(String tagName) {
		this.tagName = tagName;		
	}

	@Override
	public void setTagText(String tagText) {
		this.tagText = tagText;
	}
	
}
