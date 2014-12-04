package pa.iscde.commands.controllers;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.ui.PlatformUI;

import pa.iscde.commands.models.CommandKey;

final public class KeyPressDetector {

	private static KeyPressDetector instance;

	public static KeyPressDetector getInstance() {
		if (instance == null) {
			instance = new KeyPressDetector();
		}
		return instance;
	}

	private List<KeyStrokeListener> listeners;

	public interface KeyStrokeListener {
		public void keyPressed(CommandKey c);
	}

	private KeyPressDetector() {
		listeners = new LinkedList<KeyStrokeListener>();

		Display.getDefault().addFilter(SWT.KeyDown, new Listener() {

			@Override
			public void handleEvent(Event event) {

				boolean ctrl_clicked = (event.stateMask & SWT.CTRL) == SWT.CTRL;
				boolean alt_clicked = (event.stateMask & SWT.ALT) == SWT.ALT;
				int keyCode_lastKey = event.keyCode;
				
				
				String viewActive = PlatformUI.getWorkbench()
						.getActiveWorkbenchWindow().getActivePage()
						.getActivePartReference().getPartProperty("viewid");

				// Capture any keystroke combination that uses CTRL or ALT with
				// something else, or both and something else
				if (((event.stateMask & SWT.CTRL) == SWT.CTRL)
						|| ((event.stateMask & SWT.ALT) == SWT.ALT)) {
					if (Character.isLetterOrDigit(keyCode_lastKey)
							&& keyCode_lastKey != SWT.CTRL
							&& keyCode_lastKey != SWT.ALT) {
						CommandKey typed = new CommandKey(viewActive,
								ctrl_clicked, alt_clicked,
								(char) keyCode_lastKey);
						notifyListeners(typed);
					}
				}
			}

		});
	}

	public void addKeyPressListener(KeyStrokeListener listener) {
		listeners.add(listener);
	}

	public void removeKeyPressListener(KeyStrokeListener listener) {
		listeners.remove(listener);
	}

	public void notifyListeners(CommandKey c) {
		// for (KeyStrokeListener keyStrokeListener : listeners) {
		// notefica apenas o ultimo listener, para evitar chamar outros listeners individamente
		listeners.get(listeners.size() - 1).keyPressed(c);
		// }
	}

}
