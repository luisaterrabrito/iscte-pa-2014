package pa.iscde.snippets.extensionhandlers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

import pa.iscde.snippets.external.ContextDefinitionInterface;
import pa.iscde.snippets.external.CursorContext;
import pa.iscde.snippets.external.ContextDefinitionInterface.ValidateMessage;
import pa.iscde.snippets.fileoperations.FileOperations;
import activator.SnippetsActivator;

public class SnippetContextDefinitionManager {
	private static SnippetContextDefinitionManager instance;
	public static final String EXT_POINT_ID = "pa.iscde.snippets.contextdefinition";
	private HashMap<String, ContextDefinitionInterface> definitions = new HashMap<>();
	private FileOperations fileOP;

	public static SnippetContextDefinitionManager getInstance() {
		if (instance == null)
			instance = new SnippetContextDefinitionManager();
		return instance;
	}

	private SnippetContextDefinitionManager() {
	}

	public void addDefinition(ContextDefinitionInterface c) {
		definitions.put(c.getTargetSnippet().toLowerCase(), c);
	}

	public void removeDefinition(ContextDefinitionInterface c) {
		definitions.remove(c.getTargetSnippet().toLowerCase());
	}

	public void loadDefinitions() throws CoreException {
		IExtensionRegistry reg = Platform.getExtensionRegistry();
		for (IExtension ext : reg.getExtensionPoint(EXT_POINT_ID)
				.getExtensions()) {
			for (IConfigurationElement member : ext.getConfigurationElements()) {
				ContextDefinitionInterface definition = (ContextDefinitionInterface) member
						.createExecutableExtension("definition");
				addDefinition(definition);
			}
		}
	}

	public ValidateMessage validateSnippet(FileOperations fileOP) {
		this.fileOP = fileOP;
		File openFile = SnippetsActivator.getInstance().getOpenFile();
		if (definitions.containsKey(fileOP.getFileName().toLowerCase())
				&& openFile != null) {
			return definitions.get(fileOP.getFileName().toLowerCase())
					.validateContext(createContext(createVisitor(openFile)));
		} else {
			if (openFile != null)
				return new ValidateMessage("", true);
			else
				return new ValidateMessage(
						"There is no open file in the editor", false);
		}
	}

	private CursorContext createContext(ContextVisitor visitor) {
		String language = fileOP.getFileType();
		File openFile = SnippetsActivator.getInstance().getOpenFile();
		String[] splitName = openFile.getName().split("\\.");
		String extension = splitName[splitName.length - 1].toLowerCase();
		if (language != null) {
			language = language.toLowerCase();
		} else {
			language = "NewSnippet";
		}
		CursorContext context = visitor.buildCursorContext(extension, language);
		return context;
	}

	private ContextVisitor createVisitor(File openFile) {
		FileInputStream stream;
		ContextVisitor visitor = new ContextVisitor(SnippetsActivator
				.getInstance().getCursorPosition());
		try {
			stream = new FileInputStream(openFile);
			byte[] data = new byte[(int) openFile.length()];
			stream.read(data);
			stream.close();
			String str = new String(data, "UTF-8");
			ASTParser parser = ASTParser.newParser(AST.JLS8);
			parser.setSource(str.toCharArray());
			parser.setResolveBindings(true);
			CompilationUnit unit = (CompilationUnit) parser.createAST(null);
			unit.accept(visitor);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return visitor;
	}
}
