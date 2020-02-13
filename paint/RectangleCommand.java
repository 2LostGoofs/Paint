package ca.utoronto.utm.paint;
import java.awt.Graphics2D;
import java.io.PrintWriter;
import java.util.ArrayList;

public class RectangleCommand implements PaintCommand {
	private Rectangle rectangle;
	public RectangleCommand(Rectangle rectangle){
		this.rectangle = rectangle;
	}
	
	public String toString(){
		String s = "";
		s += "Rectangle" + "\n";
		s += this.rectangle.toString();
		s += "\tp1:" + this.rectangle.getP1() + "\n";
		s += "\tp2:" + this.rectangle.getP2() + "\n";
		s += "End Rectangle" + "\n";
		return s;
	}
	
	
	public void execute(Graphics2D g2d){
		g2d.setColor(rectangle.getColor());
		Point topLeft = this.rectangle.getTopLeft();
		Point dimensions = this.rectangle.getDimensions();
		if(rectangle.isFill()){
			g2d.fillRect(topLeft.x, topLeft.y, dimensions.x, dimensions.y);
		} else {
			g2d.drawRect(topLeft.x, topLeft.y, dimensions.x, dimensions.y);
		}
	}

	public void write(PrintWriter writer) {
		writer.printf(toString());
	}
}
