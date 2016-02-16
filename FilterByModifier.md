This extension point makes the teams choose the modifier they want to filter the annotation to the desired methods.


# Details #
> ## Example ##
```
public class testeMetodo implements FilterByModifier{

		@Override
		public int verificarModificadorMetodo() {
		return Modifier.PRIVATE;
			}
		}
```