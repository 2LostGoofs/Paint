package ca.utoronto.utm.paint;

public class Point {
	int x, y; // Available to our package
	public Point(int x, int y){
		this.x=x; this.y=y;
	}
	public String toString(){
		String s = "";
		s += "("+this.x+","+this.y+")";
		return s;
	}
}
