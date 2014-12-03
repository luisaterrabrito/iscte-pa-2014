package pa.iscde.snippets.extensionhandler;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import pa.iscde.snippets.fileoperations.FileOperations;
import pa.iscde.snippets.interfaces.ProgrammaticSnippetsInterface;

public class ProgrammaticSnippets implements ProgrammaticSnippetsInterface {
	public static final String EXT_POINT_ID = "pa.iscde.snippets.createnewsnippet";

	@Override
	public void createNewSnippetProgrammatically() {
		FileOperations f = new FileOperations();
		IExtensionRegistry reg = Platform.getExtensionRegistry();

		for (IExtension ext : reg.getExtensionPoint(EXT_POINT_ID)
				.getExtensions()) {
			for (IConfigurationElement snippet : ext.getConfigurationElements()) {
				String name = snippet.getAttribute("name");
				if(name.equals("") || f.checkIfNameAlreadyExists(name))
					return;
				String code = snippet.getAttribute("code");
				String language = snippet.getAttribute("language");
				if(language.replaceAll("\\s", "").equals("") || language == null)
					language = "Unknown";
				f.save(name, code, language);
			}
		}
	}
}
