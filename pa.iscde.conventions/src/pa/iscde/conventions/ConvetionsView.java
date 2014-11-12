package pa.iscde.conventions;

import java.awt.TextArea;
import java.util.Map;













import javax.swing.text.StyledEditorKit.ForegroundAction;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.PopupList;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import pt.iscte.pidesco.extensibility.PidescoView;

public class ConvetionsView implements PidescoView {

	public ConvetionsView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createContents(Composite viewArea, Map<String, Image> imageMap) {
		
		Shell shell = new Shell(); //duvida na shell
		RowLayout rowLayout = new RowLayout();
		viewArea.setLayout(rowLayout);
		
		
		final Text texto = new Text(viewArea, SWT.BORDER);
		texto.setSize(10, 20);
		
		final MessageBox dialog = 
					new MessageBox(shell, SWT.ICON_WARNING | SWT.OK);
		
		
		//Verificar classes.
		Button botao = new Button(viewArea, SWT.PUSH);
		botao.setSize(10, 20);
		botao.setText("CheckFirstLetter");
		
		botao.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				if(checkFirstLetter(texto.getText())){
					
							dialog.setText("Warning");
							dialog.setMessage("O texto tem uma minuscula no inicio!");
							dialog.open();
				};
				
				
			}
		});
		
		//Verificar Metodos
		Button botaoMaior = new Button(viewArea, SWT.PUSH);
		botaoMaior.setSize(10, 20);
		botaoMaior.setText("CheckFirstLetterUpper");
		
		botaoMaior.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				if(!checkFirstLetter(texto.getText())){
					
							dialog.setText("Warning");
							dialog.setMessage("O texto tem uma maiuscula no inicio!");
							dialog.open();
				};
				
				
			}
		});
		
		
		Button botaoProtectedWords = new Button(viewArea, SWT.PUSH);
		botaoProtectedWords.setSize(10, 20);
		botaoProtectedWords.setText("ProtectedWords");
		
		botaoProtectedWords.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event event) {
				if(!checkFirstLetter(texto.getText())){
					
							dialog.setText("Warning");
							dialog.setMessage("O texto é uma palavra protegida");
							dialog.open();
				};
				
				
			}
		});

	}
	
	public boolean checkFirstLetter(String name){ //temporarimente boolean
		
		if(Character.isLowerCase(name.charAt(0))){
			return true;
		}
		
		return false;
		
	}
	
	public boolean protectedWords(String word){
		String[] protectedwords = {"private", "protected", "public", "abstract", "class", "extends", "final", 
				"implements", "interface", "new", "static", "strictfp", "synchronized", "transient", "volatile", "break", "case", 
				"continue", "default", "do", "else", "for", "if", "instanceof", "return", "switch", "while", "assert", "catch", "finally",
				"throw", "throws", "try", "import", "package", "boolean", "byte", "char", "double", "float", "int", "long", "short", 
				"super", "this", "void", "const", "goto", "null", "true", "false"};
		
		
		
		for(String s : protectedwords){
			if(word.equals((s))){
				return true;
			}
		}
		
		return false;
		
		
		
	}
	

}
