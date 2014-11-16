package pa.iscde.formulas.engineering;

import pa.iscde.formulas.Formula;

public class ElectronicsFormulas extends Formula {

	private final static String[] INPUTS = {"Formulas:","Voltage","Current","Resistance","Capacitor"};
	private String[] inptuss;
	private String electronicSelector = null;

	public ElectronicsFormulas() {
		inptuss = INPUTS;
	}

	@Override
	public String[] inputs() {
		String[] inputs = inptuss;
		return inputs;
	}

	@Override
	public String name() {
		return "Electronic Formulas";
	}

	@Override
	public String result(String[] inputs) {
		double voltage, current, resistance, result;
		if(inputs==null){
			inptuss = INPUTS;
			electronicSelector = null;
		}else if(electronicSelector==null){
			switch(inputs[0]){
			case "Voltage":
				String[] in = {"Resistance[Ohm]" , "Current[A]"};
				inptuss = in;
				electronicSelector = "Voltage";
				break;
			case "Current":
				String[] in2 = {"Voltage[V]","Resistance[Ohm]"};
				inptuss = in2;
				electronicSelector = "Current";
				break;
			case "Resistance":
				String[] in3 = {"Voltage[V]","Current[A]"};
				inptuss = in3;
				electronicSelector = "Resistance";
				break;
			case "Capacitor":
				String[] in4 = {"Electric Charge[Q]","Voltage[V]"};
				inptuss = in4;
				electronicSelector = "Capacitor";
				break;
			}
		}else{
			switch(electronicSelector){
			case "Voltage":
				resistance = Double.parseDouble(inputs[0]);
				current = Double.parseDouble(inputs[1]);
				voltage = resistance*current;
				inptuss = INPUTS;
				electronicSelector = null;
				return String.valueOf(voltage);
			case "Current":
				voltage = Double.parseDouble(inputs[0]);
				resistance = Double.parseDouble(inputs[1]);
				result = voltage/resistance;
				inptuss = INPUTS;
				electronicSelector = null;
				return String.valueOf(result);
			case "Resistance":
				voltage = Double.parseDouble(inputs[0]);
				current = Double.parseDouble(inputs[1]);
				result = voltage*current;
				inptuss = INPUTS;
				electronicSelector = null;
				return String.valueOf(result);
			case "Capacitor":
				double electricCharge = Double.parseDouble(inputs[0]);
				voltage = Double.parseDouble(inputs[1]);
				result = electricCharge/voltage;
				inptuss = INPUTS;
				electronicSelector = null;
				return String.valueOf(result);
			}
		}
			return null;
		}

	}
