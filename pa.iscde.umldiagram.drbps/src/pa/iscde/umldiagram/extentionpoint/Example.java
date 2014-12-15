package pa.iscde.umldiagram.extentionpoint;





import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.swt.graphics.Color;

import pa.iscde.umldiagram.ClickOption;
import pa.iscde.umldiagram.UMLClassFigure;

public class Example implements ClickOption{

	@Override
	public MouseListener getAction() {
		MouseListener m;
		m = new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent me) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent me) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseDoubleClicked(MouseEvent me) {
				try{
				UMLClassFigure f = (UMLClassFigure) me.getSource();
				f.getNode().setBackgroundColor(new Color(null, 0, 255, 255));
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		};
		return m;
	}


	

}
