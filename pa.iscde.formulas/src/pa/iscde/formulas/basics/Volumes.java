package pa.iscde.formulas.basics;

import pa.iscde.formulas.Formula;

public class Volumes extends Formula{
	

		private final static String[] INPUTS = {"Volumes:","Cube","Rectangular Prism","Trinagular Prism","Cylinder","Pyramid"};

		private String[] inptuss;
		private String areaSelector = null;
		
		public Volumes() {
			inptuss = INPUTS;
		}
		
		
		@Override
		public String[] inputs() {
			String[] inputs = inptuss;
			return inputs;
		}

		@Override
		public String name() {
			return "Volumes";
		}
		
		@Override
		public String result(String[] inputs) {
			double volume,height,side,a,b,c,radius;
			if(inputs==null){
				inptuss = INPUTS;
				areaSelector = null;
			}else if(areaSelector==null){
				switch(inputs[0]){
				case "Cube":
					String[] in = {"Side"};
					inptuss = in;
					areaSelector = "Cube";
					break;
				case "Rectangular Prism":
					String[] in2 = {"A","B","C"};
					inptuss = in2;
					areaSelector = "Rectangular Prism";
					break;
				case "Trinagular Prism":
					String[] in3 = {"A","B","C"};
					inptuss = in3;
					areaSelector = "Trinagular Prism";
					break;
				case "Cylinder":
					String[] in8 = {"Radius","Height"};
					inptuss = in8;
					areaSelector = "Cylinder";
					break;
				case "Pyramid":
					String[] in4 = {"A","B","C"};
					inptuss = in4;
					areaSelector = "Pyramid";
					break;
				}
			}else{
				switch(areaSelector){
				case "Cube":
					side = Double.parseDouble(inputs[0]);
					volume = Math.pow(side, 3);
					inptuss = INPUTS;
					areaSelector = null;
					return String.valueOf(volume);
				case "Rectangular Prism":
					a = Double.parseDouble(inputs[0]); //lado maior do rectangulo da base
					b = Double.parseDouble(inputs[1]); //lado menor do rectangulo da base
					c = Double.parseDouble(inputs[2]); //altura do prisma
					volume = a*b*c;
					inptuss = INPUTS;
					areaSelector = null;
					return String.valueOf(volume);
				case "Trinagular Prism":
					a = Double.parseDouble(inputs[0]); //lado do triangulo da base
					b = Double.parseDouble(inputs[1]); //altura do triangulo da base
					c = Double.parseDouble(inputs[2]); //altura do prisma
					volume = ((a*b)/2)*c;
					inptuss = INPUTS;
					areaSelector = null;
					return String.valueOf(volume);
				case "Cylinder":
					radius = Double.parseDouble(inputs[0]); 
					height = Double.parseDouble(inputs[1]);
					volume = ((Math.pow(radius, 2)*Math.PI)*(height/3));
					inptuss = INPUTS;
					areaSelector = null;
					return String.valueOf(volume);
				case "Pyramid":
					a = Double.parseDouble(inputs[0]); //lado do triangulo da base
					b = Double.parseDouble(inputs[1]); //altura do triangulo da base
					c = Double.parseDouble(inputs[2]); //altura da piramide
					volume = (a*b*c)/6;
					inptuss = INPUTS;
					areaSelector = null;
					return String.valueOf(volume);
				}
			
			}
			return null;
		}
		
	

	
	
	


}
