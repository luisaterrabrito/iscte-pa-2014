# Introduction #

With this extension point, we can define contexts where a certain snippet should not be used.

The context is given by the cursor position, and it contains a lot of information such as: if the cursor is inside a method, the visibility of the method, if the cursor is inside the top class, the visibility of the inner/static class if the cursor is inside it, if the cursor is inside a try or a catch block, etc.

The context is defined by implementing the correspondent interface. The snippet plugin will then check, when a user tries to use a snippet, if the current context matches the defined snippet context. If so, the program will insert the snippet at the cursor position. If not, the user will be informed by an error message defined in the correspondent context implementations.

# Details #

To create a new context definition, the correspondent extension point must be extendend and an implementation of the interface ContextDefinitionInterface must be provided.

This interface has 3 methods:

The first, getIdentifier(), it's a method with a return type string. This string should help identify the context and what it does, however it is not mandatoty (it can be an empty String), and it doesn't have to be unique among all the context definitions.

The second, getTargetSnippet(), it's a method that has to return the name of the snippet whose context will be defined. The name is not case sensitive.

The third, validateContext(CursorContext e), is a method that receives a cursor Context, which is a data object that contains a lot of information about the context of the cursor. This will be the method that defines the context. To do that the interface implementation can define the need for some boolean (or all of them) attributes of the context to be false, or true, the need for the visibility to be private, etc. The method must return a ValidateMessage, which is an object that has a boolean valid that says if the snippet is valid in that context or not, and a error message to display to the user in case the context is not valid. So, as long as the return type is respected, the implementation has a lot of freedom to define the context.

## Examples ##

### The snippet Append text to file must be inside a method ###

```

public class AppendTextInsideProcedure implements ContextDefinitionInterface {

	@Override
	public String getIdentifier() {
		return "AppendTextInsideProcedure";
	}

	@Override
	public String getTargetSnippet() {
		return "appendTextToFile";
	}

	@Override
	public ValidateMessage validateContext(CursorContext e) {
		return new ValidateMessage("This snippet must be inside a procedure or function.", e.isInsideMethod());
	}

}

```

### The snippet Java Date to SQL Date must be inside a try block ###

```

public class JavaDateToSQLDateInsideTry implements ContextDefinitionInterface {

	@Override
	public String getIdentifier() {
		return "JavaDateToSQLDateInsideTry";
	}

	@Override
	public String getTargetSnippet() {
		return "JavaDateToSQLDate";
	}

	@Override
	public ValidateMessage validateContext(CursorContext e) {
		return new ValidateMessage("This snippet must be inside a try block.", e.isInsideTry());
	}

}

```

### The snippet Create Zip must not be inside a final declaration (method or class) ###

Note:
The modifiers given by the cursor context refer to the most especific modifiers possible according to possition or hierarquical modifiers. For example, if the top class is not final, and the cursor is inside a final method, the cursor isFinal() will return true.

```

public class CreateZipNotFinal implements ContextDefinitionInterface {

	@Override
	public String getIdentifier() {
		return "CreateZipNotFinal";
	}

	@Override
	public String getTargetSnippet() {
		return "CreateZip";
	}

	@Override
	public ValidateMessage validateContext(CursorContext e) {
		return new ValidateMessage("This snippet can't be inside a final class or method.", !e.isFinal());
	}

}

```

### A more extensive context to illustrate the possibilities ###

```

public class DirectoryListingOverlyComplex implements ContextDefinitionInterface {

	@Override
	public String getIdentifier() {
		return "DirectoryListingOverlyComplex";
	}

	@Override
	public String getTargetSnippet() {
		return "DirectoryListing";
	}

	@Override
	public ValidateMessage validateContext(CursorContext e) {
		boolean valid = false;
		if (e.isSynchronized() && e.isStatic() && e.isInsideTry()
		&& !e.isInterface() && e.getVisibility().equals("public")
		&& e.getOpenedFileExtension().equals(e.getSnippetLanguage()))
			valid = true;
		return new ValidateMessage(
				"This is too complicated to explain", valid);
	}
}

```