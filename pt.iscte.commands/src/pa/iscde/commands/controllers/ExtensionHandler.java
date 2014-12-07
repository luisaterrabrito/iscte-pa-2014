package pa.iscde.commands.controllers;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

/**
 * Is class aims to simplify the processing of extension points.
 * 
 * @author Fábio Martins
 * */
final public class ExtensionHandler {

	public interface Handler {

		/**
		 * This method indicates how the extension point will be processed. For
		 * each extension point of the same type as the extension ID, is called
		 * once.
		 * 
		 * @param extension
		 *            - the extension point class.
		 * 
		 * @throws CoreException
		 *             - if you try to get unknown classes from the extension
		 *             point
		 * */
		public abstract void processExtension(IConfigurationElement extension)
				throws CoreException;
	}

	private static ExtensionHandler instance;

	private String extensionID;
	private Handler handler;

	public static ExtensionHandler getInstance() {
		if (instance == null) {
			instance = new ExtensionHandler();
		}
		return instance;
	}

	private ExtensionHandler() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param extensionID
	 *            The id of the extension point
	 * @param handler
	 *            the extension handler
	 * 
	 * */
	public void setExtensionHandler(String extensionID, Handler handler) {
		this.extensionID = extensionID;
		this.handler = handler;
	}

	public void startProcessExtension() {
		IExtensionRegistry registry = Platform.getExtensionRegistry();
		IConfigurationElement[] config = registry
				.getConfigurationElementsFor(extensionID);

		for (IConfigurationElement e : config) {

			try {
				handler.processExtension(e);
			} catch (CoreException e1) {
				e1.printStackTrace();
			}
		}
	}

}
