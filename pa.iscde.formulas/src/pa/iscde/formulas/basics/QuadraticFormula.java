package pa.iscde.formulas.basics;

import pa.iscde.formulas.Formula;

public class QuadraticFormula implements Formula{
	
	@Override
	public String name() {
		return "quadratic";
	}

	@Override
	public String methodCode() {
		String methodCode = "";
	
		return methodCode;
	}

	@Override
	public String result(String[] inputs) {
		int a,b,c;
		double root,x,y;
		String result = "";
		a = Integer.parseInt(inputs[0]);
		b = Integer.parseInt(inputs[1]);
		c= Integer.parseInt(inputs[2]);
		root =(b*b-((4*a*c)));	
		if(root>0){
			x = (-b+(Math.sqrt(root)))/(2*a);
			y = (-b-(Math.sqrt(root)))/(2*a);
			result = "Solution: x="+x+" and x="+y;
		}else{
			String i = (Math.sqrt(-root)/(2*a))+"i";
			result = (-b/(2*a))+"+("+i+")"+" and "+(-b/(2*a))+"-("+i+")";
		}
		return result;
	}
	
	

	@Override
	public String[] inputs() {
		String[] inputs = {"a","b","c"};
		return inputs;
	}
	

}
