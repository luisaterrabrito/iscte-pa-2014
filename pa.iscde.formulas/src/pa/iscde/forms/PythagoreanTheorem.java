package pa.iscde.forms;

public class PythagoreanTheorem implements Formula{

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
		return  calculatePhythagoreanTheorem(inputs);
	}

	private String calculatePhythagoreanTheorem(String[] inputs) {
		return null;
		
	}
	
//	public String PitagorasHipotenusa(double ca,double cb){
//		double aux;
//		aux = (ca*ca)+(cb*cb);
//		double h = Math.sqrt(aux);
//		return String.valueOf(h);
//	}
//	
//	public String PitagorasCateto(double h, double ca){
//		if(h<ca){
//			return "Hipotenusa não pode ser menor que o cateto";
//		}else{
//		double aux = (h*h)-(ca*ca);
//		double cb = Math.sqrt(aux);
//		return String.valueOf(cb);
//		}
//	}

}
