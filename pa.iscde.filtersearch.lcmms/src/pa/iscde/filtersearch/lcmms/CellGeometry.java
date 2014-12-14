package pa.iscde.filtersearch.lcmms;

import pa.iscde.formulas.Formula;

public class CellGeometry extends Formula {

	@Override
	public String[] inputs() {
		String[] inputs = {"G (Geometry)", "Iown (Interferance own cell)", "Iothers (Interferance other cells", "Pn (AWGN power)"};		
		return inputs;
	}

	@Override
	public String name() {
		return "Cell Geometry";
	}

	@Override
	public String result(String[] inputs) {

		if(inputs[0].equals("")){
			return "Inputs not valid";
		}else{
			double g = Double.parseDouble(inputs[0]);
			double i_own = Double.parseDouble(inputs[1]);
			double i_others = Double.parseDouble(inputs[2]);
			double pn = Double.parseDouble(inputs[3]);
			
			double result = (i_own / (i_others + pn))*g; 
			return String.valueOf(result);
		}
	}
}
