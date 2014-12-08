package pa.iscde.commands.features.actions;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import pa.iscde.commands.controllers.KeyPressDetector.KeyDownListener;


public class CommandKeyListener implements KeyDownListener {

	
	// This is a specialized listener to catch possible Command Keys ! 
	// It captures any keystroke combination that uses CTRL or ALT with something else, or both and something else
	@Override
	public boolean keyPressed(Event event) {
		
		int keyCode_lastKey = event.keyCode;
		
		if (((event.stateMask & SWT.CTRL) == SWT.CTRL)
				|| ((event.stateMask & SWT.ALT) == SWT.ALT)) 
			if (Character.isLetterOrDigit(keyCode_lastKey)
						&& keyCode_lastKey != SWT.CTRL
								&& keyCode_lastKey != SWT.ALT) 
									return true;
		
		return false;
	}

}
