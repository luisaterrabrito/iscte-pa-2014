package pt.iscte.pidesco.documentation.service;

public interface ITagComment {

	public String getTagName();
	public String getTagVariable(); // Opcional
	public String getTagText();
	
	public void setTagName(String tagName);
	public void setTagVariable(String tagVariable);
	public void setTagText(String tagText);

}
