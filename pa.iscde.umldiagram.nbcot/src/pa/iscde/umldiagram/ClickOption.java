
package pa.iscde.umldiagram;

import org.eclipse.swt.events.MouseListener;







/**
 * This extension point offers other plugins the ability to define the action performed when clicking a class in the diagram.

   Requirements: a class that implements our interface

 * @author Nuno & Diogo
 *
 */
public interface ClickOption {
	/**
	 * choose what happens in double click option to each graph node
	 * @return org.eclipse.swt.events.MouseListener()
	 */
	public MouseListener getAction();
	
	
	/**---------------------------------------------------------------------------------------
	 * EXAMPLE:
	 * 
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
								
								 //* exemplos:
								// * <enum>
								 //* <class>
								 //* <interface>
								 
							}
							
						}
							
					}
				//CGraphNode f = (CGraphNode)e.getSource();
				
				}catch(Exception ex){
					System.out.println("Erro ao extender a interface clickOption");
				}
				
			}
	 */
	
}
