package pa.iscde.formulas.basics;

import pa.iscde.formulas.Formula;

public class Volumes extends Formula{
	
	@Override
	public String[] inputs() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String result(String[] inputs) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String cubeVolume(String side){
		double a = Double.parseDouble(side);
		double aux= Math.pow(a, 3);
		return String.valueOf(aux);
	}
	
	public String rectangularPrismVolume(String[]inputs){
		double a = Double.parseDouble(inputs[0]); //lado maior do rectangulo da base
		double b = Double.parseDouble(inputs[1]); //lado menor do rectangulo da base
		double c = Double.parseDouble(inputs[2]); //altura do prisma
		double aux = a*b*c;
		return String.valueOf(aux);
	}
	
	public String triangularPrismVolume(String []inputs){
		double a = Double.parseDouble(inputs[0]); //lado do triangulo da base
		double b = Double.parseDouble(inputs[1]); //altura do triangulo da base
		double c = Double.parseDouble(inputs[2]); //altura do prisma
		double aux = ((a*b)/2)*c;
		return String.valueOf(aux);
	}
	
	public String cylinderVolume(String []inputs){
		double radius = Double.parseDouble(inputs[0]); 
		double height = Double.parseDouble(inputs[1]);
		double aux = ((Math.pow(radius, 2)*Math.PI)*height);
		return String.valueOf(aux);
	}
	

	public String coneVolume(String []inputs){
		double radius = Double.parseDouble(inputs[0]); 
		double height = Double.parseDouble(inputs[1]);
		double aux = ((Math.pow(radius, 2)*Math.PI)*(height/3));
		return String.valueOf(aux);
	}
	
	public String pyramidVolume (String [] inputs){
		double a = Double.parseDouble(inputs[0]); //lado do triangulo da base
		double b = Double.parseDouble(inputs[1]); //altura do triangulo da base
		double c = Double.parseDouble(inputs[2]); //altura da piramide
		double aux = (a*b*c)/6;
		return String.valueOf(aux);
	}

}
