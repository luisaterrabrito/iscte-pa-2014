package pa.iscde.umldiagram.extentionpoint;


import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;

import pa.iscde.umldiagram.CkickOption;

public class Example implements CkickOption{

	@Override
	public MouseListener getAction() {
		MouseListener m = new MouseListener(){

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				System.out.println("cu");
				
			}

			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		};
		return m;
	}


	

}
