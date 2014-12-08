package pa.iscde.commands.controllers;

import java.util.LinkedList;
import java.util.List;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

final public class KeyPressDetector {

	public interface KeyDownListener {
		public boolean keyPressed(Event c);
	}

	public interface KeyUpListener {
		public boolean keyRelease(Event c);
	}

	private static KeyPressDetector instance;

	public static KeyPressDetector getInstance() {
		if (instance == null) {
			instance = new KeyPressDetector();
		}
		return instance;
	}

	private List<KeyDownListener> downListeners;
	private List<KeyUpListener> upListeners;

	private KeyPressDetector() {
		downListeners = new LinkedList<KeyDownListener>();
		upListeners = new LinkedList<KeyUpListener>();

		Display.getDefault().addFilter(SWT.KeyDown, new Listener() {

			@Override
			public void handleEvent(Event event) {
				notifyDownListeners(event);
			}

		});

		Display.getDefault().addFilter(SWT.KeyUp, new Listener() {

			@Override
			public void handleEvent(Event event) {
				notifyUpListeners(event);
			}

		});
	}

	public void addKeyPressListener(KeyDownListener listener) {
		downListeners.add(listener);
	}

	public void removeKeyPressListener(KeyDownListener listener) {
		downListeners.remove(listener);
	}

	public void addKeyReleaseListener(KeyUpListener listener) {
		upListeners.add(listener);
	}

	public void removeKeyReleaseListener(KeyUpListener listener) {
		upListeners.remove(listener);
	}

	public void notifyDownListeners(Event event) {
		// notefica apenas o ultimo listener, para evitar chamar outros
		// listeners individamente
		if (downListeners.size() >= 1) {
			downListeners.get(downListeners.size() - 1).keyPressed(event);
		}

	}

	private void notifyUpListeners(Event event) {
		if (upListeners.size() >= 1) {
			upListeners.get(upListeners.size() - 1).keyRelease(event);
		}

	}

	public static void destroyInstance() {
		instance = null;
	}

}
