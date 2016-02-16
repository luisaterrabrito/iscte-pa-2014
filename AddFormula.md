# Introduction #

Este ponto de extensão permite criar uma nova formula para juntar às formulas já existentes.
Para o efeito deve-se selecionar a categoria, e associar uma class que extenda Formula e deve-se dar um ficheiro com o codigo para inserir na classe.
A formula inserida, posteriormente pode ser utilizada no plugin para inserir código na classe aberta ou em modo calculadora.

# Details #
Tem que se extender a class de Formula

```
public abstract class Formula{
	/**
	 * Set formula inputs number
	 * @return inputs
	 */
	public abstract String[] inputs();
	/**
	 * Set formula name
	 * @return name
	 */
	public abstract String name();
	
	/**
	 * Set the code for the calculator mode
	 * 
	 * @param inputs
	 * @return result
	 */
	public abstract String result(String[] inputs);


}


```