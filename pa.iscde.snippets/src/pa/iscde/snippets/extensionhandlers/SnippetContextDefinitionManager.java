package pa.iscde.snippets.extensionhandlers;

import java.util.HashMap;

import pa.iscde.snippets.interfaces.ContextDefinitionInterface;
import pa.iscde.snippets.interfaces.ContextDefinitionInterface.ValidateMessage;

public class SnippetContextDefinitionManager {
	private HashMap<String, ContextDefinitionInterface> definitions;
	
	public void addDefinition (ContextDefinitionInterface c){
		definitions.put(c.getTargetSnippet(), c);
	}
	
	public ValidateMessage validateSnippet(String snippet){
		if(definitions.containsKey(snippet)){
			return definitions.get(snippet).validateContext(createContext());
		} else {
			return new ValidateMessage("", true);
		}
	}

	private CursorContext createContext() {
		// TODO Auto-generated method stub
		return null;
	}
}
