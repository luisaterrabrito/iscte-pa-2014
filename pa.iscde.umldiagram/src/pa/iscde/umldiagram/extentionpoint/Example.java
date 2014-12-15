package pa.iscde.umldiagram.extentionpoint;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.zest.core.widgets.CGraphNode;
import org.eclipse.zest.core.widgets.Graph;

import pa.iscde.umldiagram.ClickOption;



public class Example implements ClickOption{



	@Override
	public MouseListener getAction() {
		MouseListener m;
		m = new MouseListener() {

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				try{
					Graph g = (Graph)e.getSource();
					for (int i = 0; i < g.getNodes().size(); i++) {
						CGraphNode n = (CGraphNode)g.getNodes().get(i);
						Point p = n.getLocation();
						Point p2 = new Point(p.x+n.getSize().width, p.y + n.getSize().height);
						if(e.x >= p.x && e.x <= p2.x && e.y >= p.y && e.y <= p2.y){
							
							if(n.getText().contains("<class>")){
								
								// TODO fazer a acção desejada para a classe do tipo interface
								/**
								 * exemplos:
								 * <enum>
								 * <class>
								 * <interface>
								 */
							}
							
						}
							
					}
				//CGraphNode f = (CGraphNode)e.getSource();
				
				}catch(Exception ex){
					System.out.println("Erro ao extender a interface clickOption");
				}
				
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
