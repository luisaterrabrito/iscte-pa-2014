package pa.iscte.mycomp;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

import pa.iscde.configurator.model.interfaces.DependencyStyle;



public class DTeste implements DependencyStyle{

		@Override
		public Color getNodeColor(String bundleId) {
			// TODO Auto-generated method stub
			
			Display display=Display.getCurrent();
			return new Color(display,255,255,0);
		}


		@Override
		public Color getDependencyColor(String extensionPointId) {
			// TODO Auto-generated method stub

			Display display=Display.getCurrent();
			return new Color(display,0,255,255);
		}


		@Override
		public Color getSelectedNodeColor(String bundleId) {
			// TODO Auto-generated method stub
			Display display=Display.getCurrent();
			return new Color(display,255,0,255);
		}


		@Override
		public String getName() {
			// TODO Auto-generated method stub
			return "Teste";
		}

	}

