package pa.iscde;

import java.util.Map;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;

import pt.iscte.pidesco.extensibility.PidescoUI;
import pt.iscte.pidesco.extensibility.PidescoView;

public class TeamView implements PidescoView {

	private static final String EXT_POINT_ID = "pa.iscde.team";

	@Override
	public void createContents(Composite area, Map<String, Image> imageMap) {
		Display display = area.getDisplay();
		Color white = new Color(display, 255,255,255);
		Font teamFont = new Font(display, new FontData("arial", 16, SWT.BOLD));
		Font quoteFont = new Font(display, new FontData("arial", 12, SWT.ITALIC));

		area.setLayout(new FillLayout());

		ScrolledComposite scroll = new ScrolledComposite(area, SWT.H_SCROLL | SWT.V_SCROLL);

		Composite comp = new Composite(scroll, SWT.NONE);
		comp.setLayout(new GridLayout(2, false));
		comp.setBackground(white);

		new Label(comp, SWT.NONE);
		new Label(comp, SWT.NONE).setImage(imageMap.get("iscde.png"));

		IExtensionRegistry reg = Platform.getExtensionRegistry();

		for(IExtension ext : reg.getExtensionPoint(EXT_POINT_ID).getExtensions()) {
			Label team = new Label(comp, SWT.NONE);
			team.setText(ext.getLabel()); 
			team.setFont(teamFont);

			new Label(comp, SWT.HORIZONTAL | SWT.SEPARATOR).setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

			for(IConfigurationElement member : ext.getConfigurationElements()) {
				Image img = imageMap.get("unknown.png");
						
				String fileName = member.getAttribute("photo");
				if(fileName != null) {
					Image i = PidescoUI.getImageFromPlugin(ext.getContributor().getName(), fileName);
					if(i != null)
						img = i;
				}
				
				Label photo = new Label(comp, SWT.NONE);
				photo.setImage(img);
				photo.setLayoutData(new GridData(100, 100));

				Composite data = new Composite(comp,SWT.NONE);
				data.setLayout(new RowLayout(SWT.VERTICAL));
				data.setBackground(white);

				Label name = new Label(data, SWT.NONE);
				name.setText(member.getAttribute("name") );
				name.setBackground(white);

				String quoteText = member.getAttribute("quote");
				if(quoteText != null) {
					Label quote = new Label(data, SWT.NONE);
					quote.setText(quoteText);
					quote.setFont(quoteFont);
					quote.setBackground(white);
				}

				String address = member.getAttribute("user") + "@iscte-iul.pt";
				Link email = new Link(data, SWT.NONE);
				email.setText("<a href=\"mailto:" + address + "\">" + address + "</a>");
				email.setBackground(white);
			}
			// line break
			new Label(comp, SWT.NONE).setLayoutData(new GridData(50,50));
			new Label(comp, SWT.NONE);
		}	

		scroll.setContent(comp);
		scroll.setMinSize(300, comp.computeSize(SWT.DEFAULT, SWT.DEFAULT).y);
		scroll.setExpandHorizontal(true);
		scroll.setExpandVertical(true);
	}
}
