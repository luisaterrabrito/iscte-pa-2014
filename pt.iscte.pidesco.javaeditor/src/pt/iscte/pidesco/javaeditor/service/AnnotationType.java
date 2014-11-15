package pt.iscte.pidesco.javaeditor.service;

/**
 * Set of annotation types that can be used to annotate the java editor code.
 */
public enum AnnotationType {
		ERROR("org.eclipse.ui.workbench.texteditor.error"),
		WARNING("org.eclipse.ui.workbench.texteditor.warning"),
		INFO("org.eclipse.ui.workbench.texteditor.info");

		/**
		 * Id of the annotation (Eclipse-specific)
		 */
		public final String ID;

		private AnnotationType(String id) {
			this.ID = id;
		}
	}