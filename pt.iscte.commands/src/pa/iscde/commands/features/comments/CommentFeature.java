package pa.iscde.commands.features.comments;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.osgi.framework.ServiceReference;

import pa.iscde.commands.controllers.CommandsController;
import pa.iscde.commands.internal.services.Command;
import pt.iscte.pidesco.javaeditor.service.JavaEditorServices;

public class CommentFeature implements Command {

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
			//int endOfLineCursorPosition = n.indexOf("\r\n", cursorPosition);
			
			int inicioDalinha = n.substring(0, cursorPosition).lastIndexOf("\r\n")+4;
			int fimDaLinha = n.substring(cursorPosition, n.length()).indexOf("\r\n");
			System.out.println("cursor: " + cursorPosition );
			System.out.println("tamanho texto: " + n.length());
			System.out.println("fim da linha: " + fimDaLinha);
			System.out.println("antes");
			System.out.println(n);
			System.out.println("depois");
			System.out.println(n.substring(0, inicioDalinha-4) + n.substring((inicioDalinha + (cursorPosition - inicioDalinha) + fimDaLinha), n.length()) );
			
			String p = n.substring(0, inicioDalinha-4) + n.substring((inicioDalinha + (cursorPosition - inicioDalinha) + fimDaLinha), n.length()) ;
			FileWriter writer = new FileWriter(f.getAbsoluteFile(),true);
			
			writer.write(p);
			writer.flush();
			writer.close();
			
			//System.out.println("linha: " + n.substring(inicioDalinha, (inicioDalinha + (cursorPosition - inicioDalinha) + fimDaLinha) ));
			
			
			
			//String textEdited = n.substring(0, inicioDalinha) + n.substring(fimDaLinha+4, n.length());
//			System.out.println("antes");
//			System.out.println(n);
//			System.out.println("depois 1");
//			System.out.println(n.substring(0, inicioDalinha));
//			System.out.println("depois 2");
//			System.out.println(n.substring(fimDaLinha+4, n.length()));
			//System.out.println("A linha: " + n.substring(cursorPosition, endOfLineCursorPosition));
				
			
			
			
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
