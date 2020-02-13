package ca.utoronto.utm.floatingpoint;
import java.util.concurrent.TimeUnit;
public class q2 {
	private final long createdMillis = System.currentTimeMillis(); //Timer--time started
	public static void main(String[] args) throws InterruptedException {
		q2 p = new q2();
		System.out.println(p.solve711());
	}
	public String solve711() throws InterruptedException{
			int A,B,C,D = 0;
			int Value = 711000000;
			int Spent_Val = 711;
			for (A = 0; A < (Spent_Val/2); A = A + 5) {
				for (B = 0; B < (Spent_Val/2); B = B + 5) {
					for (C = 0; C < (Spent_Val/2); C = C + 5) {
						for (D = 0; D < (Spent_Val/2); D = D + 1) {
							if (A * B * C * D == (Value) && A + B + C + D == (Spent_Val)) {
								long nowMillis = System.currentTimeMillis();//Timer -- time ended
								double a = ((double)(A))/100;
								double b = ((double)(B))/100;
								double c = ((double)(C))/100;
								double d = ((double)(D))/100;
									return (a +" "+ b +" "+ c +" "+ d +" ");
							}
			
		}}}}
		return "";
	}
}
