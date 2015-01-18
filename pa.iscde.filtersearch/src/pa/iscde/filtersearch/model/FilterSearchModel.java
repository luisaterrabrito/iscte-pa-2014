package pa.iscde.filtersearch.model;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.swt.widgets.Text;

/* Classe que gere todo o modelo do text da SearchTool, bem como mantêm em memória todos os listeners. */ 
public class FilterSearchModel {
	
	public interface FilterSearchTextListener{
		void TextChanged(Text text);
	}
	
	private Set<FilterSearchTextListener> listeners;
	private Text text;
	
	public FilterSearchModel(){
		listeners = new HashSet<FilterSearchTextListener>();
	}
	
	public void addListener(FilterSearchTextListener listener){
		listeners.add(listener);
	}

	public void textChangedEvent(Text text) {
		for(FilterSearchTextListener l : listeners)
			l.TextChanged(text);
	}

	public Text getText() {
		return text;
	}

	public void setText(Text text) {
		this.text = text;
	}
	
	
}
