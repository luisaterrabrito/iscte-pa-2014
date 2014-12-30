package pa.iscde.documentation.extension;

import pa.iscde.documentation.struture.ObjectDoc;

public interface IDocumentationExportProvider {

	public String getFilterName();
	public String getFilterExtension();
	public void saveToFile(String fullfileName, ObjectDoc doc) throws Exception;
	
}
