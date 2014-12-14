package pa.iscde.filtersearch.providers;

import java.util.List;

import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.graphics.Image;

public interface SearchProvider {
	
	/**
	 * Consoante o texto introduzida no campo de pesquisa (a String text), serão devolvidos os
	 * resultados pretendidos em forma de uma lista de objectos. Esses resultados serão definidos
	 * pela classe que implementa a interface.
	 * @param text
	 * @return lista de resultados definidos pela classe que implementa a interface
	 */
	List<Object> getResults(String text);
	
	/**
	 * Define o ícone (do tipo Image) de um Object recebido como argumento (object). Essa atribuição 
	 * está a cargo da classe que implementa a interface. O ícone relativo ao projecto não é definido 
	 * aqui, este deve ser atribuída aquando da extensão ao ponto de extensão no campo "iconName".
	 * @param object
	 * @return do icon do tipo Image referente ao object
	 */
	Image setImage(Object object);
	
	/**
	 * Método onde é definida acção a executar aquando um duplo clique no object (tipo Object) de uma
	 * referida árvore (Treeviewer tree), ambos passados como argumentos. 
	 * @param tree
	 * @param object
	 */
	void doubleClickAction(TreeViewer tree, Object object); 
}
