package pa.iscde.formulas.basics;

import pa.iscde.formulas.Formula;

public class QuadraticFormula extends Formula {
	
	@Override
	public String name() {
		return "Quadratic";
	}

	@Override
	public String result(String[] inputs) {
		int a,b,c;
		double root,x,y;
		String result = "";
		for (int i = 0; i < inputs.length; i++) {
			if(inputs[i].equals("")){
				inputs[i]="0";
			}
		}
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
