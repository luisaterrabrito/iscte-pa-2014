package pa.iscde.formulas.gmsha;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pa.iscde.conventions.extensability.Cobject;
import pa.iscde.conventions.extensability.ConventionService;
import pa.iscde.conventions.extensability.TypeOf;

public class ConventionService2 implements ConventionService {

	public Cobject verificarConvencao(String name, TypeOf typeof) {
	        return new Cobject("Método contém caracteres especiais",typeof.equals(TypeOf.METHOD) && verifySpecialCharacters(name));
	}
	
	public boolean verifySpecialCharacters(String name){
		Pattern pattern = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(name);
		boolean condition = matcher.find();

		return condition;
	}
	
}
