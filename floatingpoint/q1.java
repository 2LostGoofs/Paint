package ca.utoronto.utm.floatingpoint;

public class q1 {
	public static void main(String[] args) {
		q1 p = new q1();
		System.out.println(p.solve711());
	}
	
	/**
	 * This algorithm will not work simply because the floating point numbers are not accurate enough. A float is much less
	 * accurate than a double, it doesn't actually add 0.01 in line 17, what is added instead is 0.99..., therefore, it will
	 * never actually reach 7.11, but skip over it. An easy solution is to solve the problem in cents as compared to dollars.
	 * 
	 * 
	 * The possibility that a*b*c*d and a+b+c+d both result to 7.11.
	 * @return String
	 */
	public String solve711() {
		float a, b, c, d;
		for (a = 0.00f; a < 7.11f; a = a + .01f) {
			for (b = 0.00f; b < 7.11f; b = b + .01f) {
				for (c = 0.00f; c < 7.11f; c = c + .01f) {
					for (d = 0.00f; d < 7.11f; d = d + .01f) {
						if (a * b * c * d == 7.11f && a + b + c + d == 7.11f) {
							return (a + " " + b + " " + c + " " + d);
						}
					}
				}
			}
		}
		return "";
	}
}