package pa.iscde.formulas.basics;


import pa.iscde.formulas.Formula;
import pa.iscde.formulas.annotations.FormulaCreator;

public class Areas extends Formula{
	
	
	private final static String[] INPUTS = {"Areas:","Square","Rectangle","Parallelogram","Trapezoid","Circle","Elipse","Triangle","Equilateral Triangle"};
	private String[] inptuss;
	private String areaSelector = null;
	
	public Areas() {
		inptuss = INPUTS;
	}
	
	
	@Override
	public String[] inputs() {
		String[] inputs = inptuss;
		return inputs;
	}

	@Override
	public String name() {
		return "Areas";
	}
	
	@Override
	public String result(String[] inputs) {
		double area,height,side;
		if(inputs==null){
			inptuss = INPUTS;
			areaSelector = null;
		}else if(areaSelector==null){
			switch(inputs[0]){
			case "Square":
				String[] in = {"Side"};
				inptuss = in;
				areaSelector = "Square";
				break;
			case "Rectangle":
				String[] in2 = {"A","B"};
				inptuss = in2;
				areaSelector = "Rectangle";
				break;
			case "Parallelogram":
				String[] in3 = {"Side","Height"};
				inptuss = in3;
				areaSelector = "Parallelogram";
				break;
			case "Trapezoid":
				String[] in8 = {"BiggestWidth","SmallestWidth","Height"};
				inptuss = in8;
				areaSelector = "Trapezoid";
				break;
			case "Circle":
				String[] in4 = {"Radius"};
				inptuss = in4;
				areaSelector = "Circle";
				break;
			case "Elipse":
				String[] in5 = {"RWidth","rHeight"};
				inptuss = in5;
				areaSelector = "Elipse";
				break;
			case "Triangle":
				String[] in6 = {"DownSide","Height"};
				inptuss = in6;
				areaSelector = "Triangle";
				break;
			case "Equilateral Triangle":
				String[] in7 = {"Side"};
				inptuss = in7;
				areaSelector = "Equilateral Triangle";
				break;
			}
		}else{
			switch(areaSelector){
			case "Square":
				double aux = Double.parseDouble(inputs[0]);
				area = aux*aux; 
				inptuss = INPUTS;
				areaSelector = null;
				return String.valueOf(area);
			case "Rectangle":
				double a = Double.parseDouble(inputs[0]);
				double b = Double.parseDouble(inputs[1]);
				area = a*b;
				inptuss = INPUTS;
				areaSelector = null;
				return String.valueOf(area);
			case "Parallelogram":
				side = Double.parseDouble(inputs[0]);
				height = Double.parseDouble(inputs[1]);
				area = side*height;
				inptuss = INPUTS;
				areaSelector = null;
				return String.valueOf(area);
			case "Trapezoid":
				double biggestWidth = Double.parseDouble(inputs[0]);
				double smallestWidth = Double.parseDouble(inputs[1]);
				height = Double.parseDouble(inputs[2]);
				area = (height/2)*(biggestWidth+smallestWidth);
				inptuss = INPUTS;
				areaSelector = null;
				return String.valueOf(area);
			case "Circle":
				double r = Double.parseDouble(inputs[0]);
				area = r*Math.PI;
				inptuss = INPUTS;
				areaSelector = null;
				return String.valueOf(area);
			case "Elipse":
				double rWidth = Double.parseDouble(inputs[0]);
				double rHeight = Double.parseDouble(inputs[1]);
				area = Math.PI*rHeight*rWidth;
				inptuss = INPUTS;
				areaSelector = null;
				return String.valueOf(area);
			case "Triangle":
				double downSide = Double.parseDouble(inputs[0]);
				height = Double.parseDouble(inputs[1]);
				area = (downSide*height)/2;
				inptuss = INPUTS;
				areaSelector = null;
				return String.valueOf(area);
			case "Equilateral Triangle":
				side = Double.parseDouble(inputs[0]);
				aux = (Math.sqrt(3))/4;
				area = aux*(side*side);
				inptuss = INPUTS;
				areaSelector = null;
				return String.valueOf(area);
			}
		
		}
		return null;
	}
	

	
	
	
	

}


