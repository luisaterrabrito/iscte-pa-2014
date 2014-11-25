package pa.iscde.commands.controllers;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Scanner;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class CommandsController implements BundleActivator {

	@Override
	public void start(BundleContext context) throws Exception {

//		Shell [] n = Display.getDefault().getShells();
//		for(Shell p : n){
//			p.addListener(SWT.KeyDown, new Listener() {
//			
//				@Override
//				public void handleEvent(Event event) {
//					System.out.println("ups");
//					
//				}
//			});
//		}
//		
		
		
		Display.getDefault().addFilter(SWT.KeyDown, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
//				System.out.println(event.character);
//				System.out.println(event.stateMask);
				
				if(((event.stateMask & SWT.CTRL) == SWT.CTRL) && ((event.stateMask & SWT.ALT) == SWT.ALT) )
                {
					System.out.println("Consigo detectar CTRL + ALT + qualquer tecla");
                }
				
//				if(((event.stateMask & SWT.ALT) == SWT.ALT) )
//                {
//                    System.out.println("Key down ALT: " + event.keyCode);
//                }
//				
//				if(((event.stateMask & SWT.SHIFT) == SWT.SHIFT) )
//                {
//                    System.out.println("Key down SHIFT: " + event.keyCode);
//                }
				
			}
		});
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
