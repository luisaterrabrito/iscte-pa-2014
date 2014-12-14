package pa.iscde.commands.features.comments;



import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.eclipse.jface.text.ITextSelection;
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
		
			ITextSelection selection = editor.getTextSelected(f);
			
			
			if(selection.getLength() == 0){
				//Comment single line
				
				int cursorPosition = editor.getCursorPosition();
				int inicioDalinha = n.substring(0, cursorPosition).lastIndexOf("\r\n")+4;
				
				String p = n.substring(0, inicioDalinha) + "//" + n.substring(inicioDalinha, n.length()) ;
				editor.setText(f, p);
				
				editor.selectText(f,inicioDalinha-2, ((p.substring(inicioDalinha, p.length()).indexOf("\r\n")))+4 );
				editor.saveFile(f);
				
				
			}else{
				//Comment block
				
				int positionSelection = selection.getOffset();
				int lastPositionSelection = selection.getOffset() + selection.getLength();
				
				int firstLineOfBlock = n.substring(0, positionSelection).lastIndexOf("\r\n")+4;
				int lastLineOfBlock = lastPositionSelection + n.substring(lastPositionSelection, n.length()).indexOf("\r\n")+4;

				
				String commentedBlock = "";
				for(String line : n.substring(firstLineOfBlock, lastLineOfBlock).split("\r\n")){
					commentedBlock = commentedBlock + commentLine(line);
				}
				
				String finalResult = n.substring(0, firstLineOfBlock);
				finalResult = finalResult + commentedBlock;
				finalResult = finalResult + n.substring(lastLineOfBlock, n.length());
				
				editor.setText(f, finalResult);
				editor.saveFile(f);
				
				//Esta linha esta preparada para seleccionar todo o bloco que foi comentado,
				//No entanto, parece que internamente não é possível utilizar o selectAndReveal para multiple lines,
				//Procurou-se no google e não se encontrou solução. Sendo que aparece apenas highlighted a primeira do bloco
				editor.selectText(f,firstLineOfBlock - 2, (lastLineOfBlock-firstLineOfBlock) );
				
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	private static String commentLine(String line){
		String resultLine = "";
		
		boolean found = false;
		for(char c : line.toCharArray()){
			if(found == false && Character.isLetterOrDigit(c)){
				found = true;
				resultLine = resultLine + "//" + c;
			}
			else{
				resultLine = resultLine + c;
			}
		}
		return resultLine + "\r\n";
	}

}
