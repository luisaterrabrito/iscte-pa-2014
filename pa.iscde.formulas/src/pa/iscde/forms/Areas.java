package pa.iscde.forms;

public class Areas implements Formula{

	@Override
	public String setName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String methodCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String result(String[] inputs) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String squareArea(String input){
		double aux = Double.parseDouble(input);
		double area = aux*aux; 
		return String.valueOf(area);
	}
	
	public String rectangleArea(String [] inputs){
		double a = Double.parseDouble(inputs[0]);
		double b = Double.parseDouble(inputs[1]);
		double area = a*b;
		return String.valueOf(area);
	}
	
	public String parallelogramArea(String []inputs){
		double side = Double.parseDouble(inputs[0]);
		double height = Double.parseDouble(inputs[1]);
		double area = side*height;
		return String.valueOf(area);
	}
	
	public String trapezoidArea(String []inputs){
		double biggestWidth = Double.parseDouble(inputs[0]);
		double smallestWidth = Double.parseDouble(inputs[1]);
		double height = Double.parseDouble(inputs[2]);
		double area = (height/2)*(biggestWidth+smallestWidth);
		return String.valueOf(area);
	}
	
	public String circleArea (String input){
		double r = Double.parseDouble(input);
		double area = r*Math.PI;
		return String.valueOf(area);
	}
	
	public String elipseArea(String []inputs){
		double rWidth = Double.parseDouble(inputs[0]);
		double rHeight = Double.parseDouble(inputs[1]);
		double area = Math.PI*rHeight*rWidth;
		return String.valueOf(area);
	}
	
	public String triangleArea(String[]inputs){
		double downSide = Double.parseDouble(inputs[0]);
		double height = Double.parseDouble(inputs[1]);
		double area = (downSide*height)/2;
		return String.valueOf(area);
	}
	
	public String equilateralTriangleArea(String input){
		double side = Double.parseDouble(input);
		double aux = (Math.sqrt(3))/4;
		double area = aux*(side*side);
		return String.valueOf(area);
	}

}


