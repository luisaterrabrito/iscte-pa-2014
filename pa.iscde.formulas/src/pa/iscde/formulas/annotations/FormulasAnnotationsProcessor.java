package pa.iscde.formulas.annotations;


import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.tools.Diagnostic.Kind;

import pa.iscde.formulas.Formula;

import java.util.Set;

@SupportedAnnotationTypes("pa.iscde.formulas.annotations.*")
@SupportedSourceVersion(SourceVersion.RELEASE_7)
public class FormulasAnnotationsProcessor extends AbstractProcessor {

	@Override
	public boolean process(Set<? extends TypeElement> set, RoundEnvironment env) {
        return formulaCreator(set, env);

	}

    public boolean formulaCreator(Set<? extends TypeElement> set, RoundEnvironment env){
        Set<? extends Element> elements = env.getElementsAnnotatedWith(FormulaCreator.class);

        for(Element e : elements) {
            if(!e.getModifiers().contains(Modifier.PUBLIC))
                processingEnv.getMessager().printMessage(Kind.ERROR,
                        "should be public", e);
            if(e.getModifiers().contains(Modifier.ABSTRACT))
                processingEnv.getMessager().printMessage(Kind.ERROR,
                        "cannot be abstract", e);
            if(!e.getClass().isAssignableFrom(Formula.class)){
            	processingEnv.getMessager().printMessage(Kind.ERROR,
                        "should extends Formula", e);
            }
         
        }
        return true;
    }


}
