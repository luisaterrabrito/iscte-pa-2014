package pa.iscde.commands.features.removeline;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.osgi.framework.ServiceReference;
import pa.iscde.commands.controllers.CommandsController;
import pa.iscde.commands.internal.services.Command;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

public class RemoveLineFeature implements Command {

	@Override
	public void action() {
		
		JavaEditorServices editor;
		ServiceReference<JavaEditorServices> ref = CommandsController.getContext().getServiceReference(JavaEditorServices.class);
		editor = CommandsController.getContext().getService(ref);
		File f = editor.getOpenedFile();
		
		try {
			
			byte[] encoded = Files.readAllBytes(Paths.get(f.toURI()));
			String n = new String(encoded, Charset.defaultCharset());
			
			int cursorPosition = editor.getCursorPosition();
			int inicioDalinha = n.substring(0, cursorPosition).lastIndexOf("\r\n")+4;
			int fimDaLinha = n.substring(cursorPosition, n.length()).indexOf("\r\n");
	
			String p = n.substring(0, inicioDalinha-3) + n.substring((inicioDalinha + (cursorPosition - inicioDalinha) + fimDaLinha), n.length()) ;
			editor.setText(f, p);
			editor.selectText(f,inicioDalinha-3, 2);
			editor.saveFile(f);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
