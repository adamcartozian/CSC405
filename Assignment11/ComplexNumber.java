// Name: Adam Cartozian   
// Date: 9/9/22
// Project: Complex Number HW

package Assignment11;

public class ComplexNumber {

	private double real;
	private double imaginary;
	
	public ComplexNumber() {
		real = 0;
		imaginary = 0;
	}
	
	public ComplexNumber(double _r, double _i) {
		real = _r;
		imaginary = _i;
	}
	
	public ComplexNumber(ComplexNumber rhs) {
		real= rhs.real;
		imaginary = rhs.imaginary;
	}
	
	public void setReal(double r) {
		real = r;
	}
	
	public void setImag(double i) {
		imaginary = i;
	}
	
	public double getReal() {
		return real;
	}
	
	public double getImag() {
		return imaginary;
	}
	
	public String toString() {
		String r = Double.toString(this.getReal());
		String i = Double.toString(this.getImag());
		String str;
		if (imaginary < 0) {
			str = r + " - " + i + "i";
		}
		else {
			str = r + " + " + i + "i";
		}
		return str;
	}
	public ComplexNumber add(ComplexNumber rhs) {
		double R = this.getReal();
		double I = this.getImag();
		double rhsR = rhs.getReal();
		double rhsI = rhs.getImag();
		
		double totalR = R + rhsR;
		double totalI = I + rhsI;
		
		return new ComplexNumber(totalR, totalI);
	}
	public ComplexNumber sub(ComplexNumber rhs) {
		double R = this.getReal();
		double I = this.getImag();
		double rhsR = rhs.getReal();
		double rhsI = rhs.getImag();
		
		double totalR = R - rhsR;
		double totalI = I - rhsI;
		
		return new ComplexNumber(totalR, Math.abs(totalI));
	}
	public ComplexNumber mult(ComplexNumber rhs) {
		double R = this.getReal();
		double I = this.getImag();
		double rhsR = rhs.getReal();
		double rhsI = rhs.getImag();
		
		double totalR = (R * rhsR) - (I * rhsI);
		double totalI = (R * rhsI) + (I * rhsR);
		
		return new ComplexNumber(totalR, totalI);
	}
	public ComplexNumber div(ComplexNumber rhs) {
		double R = this.getReal();
		double I = this.getImag();
		double rhsR = rhs.getReal();
		double rhsI = rhs.getImag();
		
		double totalnumR = ((R * rhsR) + (I * rhsI));
		double totaldenomR = ((rhsR * rhsR) + (rhsI * rhsI));
		double totalnumI = ((I * rhsR) - (R * rhsI));
		double totaldenomI =  ((rhsR * rhsR) + (rhsI * rhsI));
		
		return new ComplexNumber((totalnumR/totaldenomR), (totalnumI/totaldenomI));
	}
	public double mag() {
		double R = this.getReal();
		double I = this.getImag();
		
		double Mag = Math.sqrt((R * R) + (I * I));
		
		return Mag;
	}
	public ComplexNumber conj() {
		double R = this.getReal();
		double I = this.getImag();
		
		return new ComplexNumber(R, -I);
	}
	public ComplexNumber sqrt() {
		double R = this.getReal();
		double I = this.getImag();
		double totalR;
		double totalI;
		
		if (I != 0) {
			totalR = Math.sqrt((R + Math.sqrt((R *R) + (I * I)))/2);
			totalI = Math.sqrt((-R + Math.sqrt((R *R) + (I * I)))/2);
		}
		else {
			if (R < 0) {
				totalR = 0;
				totalI = Math.sqrt(-R);
			}
			else {
				totalR = Math.sqrt(R);
				totalI = 0;
			}
		}
		
		return new ComplexNumber(totalR, totalI);
	}
	public boolean equals(Object rhs) {
		double R = this.getReal();
		double I = this.getImag();
		double rhsR = ((ComplexNumber) rhs).getReal();
		double rhsI = ((ComplexNumber) rhs).getImag();
		
		if ((Math.abs(I - rhsI) < 0.00000001) && (Math.abs(R - rhsR) < 0.00000001)){
			return true;
		}
		else {
			return false;
		}
	}

	
	/*public static void main(String[] args) {
		ComplexNumber lhs = new ComplexNumber(1, 2);
		ComplexNumber rhs = new ComplexNumber(3, 4);
		
		ComplexNumber result;
		
		result = lhs.add(rhs);
		System.out.println(result);
		
		result = lhs.sub(rhs);
		System.out.println(result);

		result = lhs.mult(rhs);
		System.out.println(result);
		
		result = lhs.div(rhs);
		
		try {
			rhs = new ComplexNumber();
			result = lhs.div(rhs);
		}
		catch (ArithmeticException e) {
			System.out.println(e);
		}
		
		System.out.println(result);
		
		System.out.println(lhs.mag());
		System.out.println(lhs.conj());
		System.out.println(lhs.sqrt());
		System.out.println(lhs.equals(rhs));
		
		rhs.setReal(1);
		rhs.setImag(2);
		System.out.println(lhs.equals(rhs));

		System.out.println(lhs.getReal() + " " + lhs.getImag());
	
	}*/

}
