package pt.iscte.pidesco.documentation.internal;

import pt.iscte.pidesco.documentation.service.ITagComment;

public class TagCommentImpl implements ITagComment {

	private String tagName;
	private String tagVariable;
	private String tagText;
	
	@Override
	public String getTagName() {
		return tagName;
	}

	@Override
	public String getTagVariable() {
		return tagVariable;
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
	public void setTagVariable(String tagVariable) {
		this.tagVariable = tagVariable;
	}

	@Override
	public void setTagText(String tagText) {
		this.tagText = tagText;
	}
	
	boolean isValidTag(String tagName, String type){
		
		
		
		boolean isValid = false;
		
		if (!tagName.trim().isEmpty()){
			if (tagName.startsWith("@")){
				tagName = tagName.trim().substring(1);
			}
						
		}
		
		return isValid;
	}
	
}
