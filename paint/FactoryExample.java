package ca.utoronto.utm.paint;

public class FactoryExample {
	public interface Shape {
		void draw();
		}
	public class Rectangle implements Shape {
		@Override
		public void draw() {
		System.out.println("Inside Rectangle::draw() method.");
		}
	public class Square implements Shape {
		@Override
		public void draw() {
		System.out.println("Inside Square::draw() method.");
		}
}
