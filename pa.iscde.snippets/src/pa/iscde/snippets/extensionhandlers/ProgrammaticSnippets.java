package pa.iscde.snippets.extensionhandlers;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

import pa.iscde.snippets.fileoperations.FileOperations;
import pa.iscde.snippets.interfaces.ProgrammaticSnippetsInterface;

public class ProgrammaticSnippets implements ProgrammaticSnippetsInterface {
	public static final String EXT_POINT_ID = "pa.iscde.createnewsnippet";

	@Override
	public void createNewSnippetsProgrammatically() {
		FileOperations f = new FileOperations();
		IExtensionRegistry reg = Platform.getExtensionRegistry();
//TODO: Should Update files with the same name
		for (IExtension ext : reg.getExtensionPoint(EXT_POINT_ID)
				.getExtensions()) {
			for (IConfigurationElement snippet : ext.getConfigurationElements()) {
				String name = snippet.getAttribute("name");
				if(name.equals("") || f.checkIfNameAlreadyExists(name))
					return;
				String code = snippet.getAttribute("code");
				String language = snippet.getAttribute("language");
				if(language == null || language.replaceAll("\\s", "").equals(""))
					language = "Unknown";
				f.save(name, code, language);
			}
		}
	}
}
