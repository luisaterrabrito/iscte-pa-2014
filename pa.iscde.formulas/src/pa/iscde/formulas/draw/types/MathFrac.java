package pa.iscde.formulas.draw.types;

import pa.iscde.formulas.extensibility.DrawEquationsProvider;

public class MathFrac implements DrawEquationsProvider{

	@Override
	public String setJavaOperation() {
		return "/";
	}
	
    @Override
	public  String setOperationLatexFormat(String line) {
//		System.out.println(line);
//		if(!line.contains("/"))
//			return line;
//		boolean hasParenteses = false;
//		int indexOfDiv = line.indexOf("/");
//		int endOfDiv = 0;
//		int beginOfDiv = 0;
//		for (int i = indexOfDiv; i < line.length(); i++) {
//			if(line.charAt(i)=='}'){
//				endOfDiv = i;
//				hasParenteses = true;
//				break;
//			}
//		}
//		for (int i = indexOfDiv; i >= 0; i--) {
//			if(line.charAt(i)=='{'){
//				beginOfDiv = i;
//				hasParenteses = true;
//				break;
//			}
//		}
//		if(!hasParenteses){
//			for (int i = indexOfDiv; i < line.length(); i++) {
//				if(line.charAt(i)==' '){
//					endOfDiv = i;
//					hasParenteses = true;
//					break;
//				}
//			}
//			if(endOfDiv==0)
//				endOfDiv=line.length();
//			for (int i = indexOfDiv; i >= 0; i--) {
//				if(line.charAt(i)=='='){
//					beginOfDiv = i;
//					hasParenteses = true;
//					break;
//				}
//			}
//			String aux1 = line.substring(beginOfDiv+1,indexOfDiv);
//			String aux2 = line.substring(indexOfDiv+1,endOfDiv);
//			String result = line.substring(0,beginOfDiv+1)+"\\frac{"+aux1+"}{"+aux2+"}";
//			return result;
//		}
//		String result = "\\frac"+line.substring(beginOfDiv, indexOfDiv)+"}{"+line.substring(indexOfDiv+1,endOfDiv+1);
//		return line.replace(line.substring(beginOfDiv,endOfDiv+1),result);
//	}
    
    	int indexOfDiv = line.indexOf(setJavaOperation());
    	int counter = 0;
    	boolean foundParenteses = false;
    	int indexBeginFrac = -1;

    	for (int i = indexOfDiv; i > 0; i--) {
    		System.out.println(line.charAt(i));
    		if(line.charAt(i) == '}'){
    			counter++;
    			foundParenteses = true;
    		}

    		if(line.charAt(i) == '{')
    			counter--;

    		if(foundParenteses && counter == 0){
    			indexBeginFrac = i;
    			return line.substring(0, indexBeginFrac) + "\\frac" + line.substring(indexBeginFrac, indexOfDiv) + line.substring(indexOfDiv + 1, line.length());
    		}
    		
    		
    	}
    	return line;
    	
    }

}



