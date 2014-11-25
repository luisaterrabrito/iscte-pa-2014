package pa.iscde.commands.controllers;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class CommandsController implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {
		
		
		System.out.println("cenass");
		new Runnable() {
			public void run() {
				
				
				System.out.println("cenass 2");
				new KeyAdapter() {
					
					@Override
					public void keyPressed(KeyEvent e) {
						System.out.println("cenass 3");
						super.keyPressed(e);
						System.out.println("Estás a bombar: " + e.getKeyChar());
						
						
						
					}
				
				};
				
				
			}
		}.run();
		
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		

	}

}
