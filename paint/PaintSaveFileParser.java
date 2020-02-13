package ca.utoronto.utm.paint;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Parse a file in Version 1.0 PaintSaveFile format. An instance of this class
 * understands the paint save file format, storing information about
 * its effort to parse a file. After a successful parse, an instance
 * will have an ArrayList of PaintCommand suitable for rendering.
 * If there is an error in the parse, the instance stores information
 * about the error. For more on the format of Version 1.0 of the paint 
 * save file format, see the associated documentation.
 * 
 * @author 
 *
 */
public class PaintSaveFileParser {
	private int lineNumber = 0; // the current line being parsed
	private String errorMessage =""; // error encountered during parse
	private ArrayList<PaintCommand> commands; // created as a result of the parse
	boolean isFilled = false; 
	Point centre = null; 
	int radius = 0;
	Point P1 = null; 
	Point P2 = null;
	String currentShape = "";
	Color color = null;
	
	
	/**
	 * Below are Patterns used in parsing 
	 */
	private Pattern pNewLine=Pattern.compile("^\\n*$");
	private Pattern pAnythingElse=Pattern.compile(".*");
	private Pattern pFileStart=Pattern.compile("^PaintSaveFileVersion1.0$");
	private Pattern pFileEnd=Pattern.compile("^EndPaintSaveFile$");
	
	private Pattern pShapeStart=Pattern.compile("(^Circle$|^Squiggle$|^Rectangle$)");
	private Pattern pCircleEnd=Pattern.compile("^EndCircle$");
	private Pattern pRectangleEnd=Pattern.compile("^EndRectangle$");
	private Pattern pSquiggleEnd=Pattern.compile("^EndSquiggle$");
	
	//Color and Fill checks (applicable to all shapes)
	private Pattern pColor = Pattern.compile("color:(([0-9]|[1-9][0-9]|1[0-9][0-9]|25[0-5]|2[0-4][0-9]),)(([0-9]|[1-9][0-9]|1[0-9][0-9]|25[0-5]|2[0-4][0-9]),)(([0-9]|[1-9][0-9]|1[0-9][0-9]|25[0-5]|2[0-4][0-9]))$");
	private Pattern pFilled = Pattern.compile("filled:((\\btrue\\b|\\bfalse\\b))");
	
	//Circle Checks
	private Pattern pCenter = Pattern.compile("center:\\((-?\\d+),(\\d+)\\)+");
	private Pattern pRadius = Pattern.compile("radius:(-?\\d+)");
	
	//Rectangle Checks
	private Pattern pPoint1Rec = Pattern.compile("p1:\\((-?\\d+),(\\d+)\\)+");
	private Pattern pPoint2Rec = Pattern.compile("p2:\\((-?\\d+),(\\d+)\\)+"); 
	//Squiggle Checks
	private Pattern pPointsStart = Pattern.compile("points");
	private Pattern pPointsEnd = Pattern.compile("end\\s*points");
	private Pattern pPoint = Pattern.compile("point:\\((-?\\d+),(\\d+)\\)+");
	private Pattern pAllPoints = Pattern.compile("point:\\((-?\\d+),(\\d+)\\)+");
	
	
	
	/**
	 * Store an appropriate error message in this, including 
	 * lineNumber where the error occurred.
	 * @param mesg
	 */
	private void error(String mesg){
		this.errorMessage = "Error in line "+lineNumber+" "+mesg;
		
	}
	/**
	 * 
	 * @return the PaintCommands resulting from the parse
	 */
	public ArrayList<PaintCommand> getCommands(){
		return this.commands;
	}
	/**
	 * 
	 * @return the error message resulting from an unsuccessful parse
	 */
	public String getErrorMessage(){
		return (this.errorMessage);
	}
	
	/**
	 * Parse the inputStream as a Paint Save File Format file.
	 * The result of the parse is stored as an ArrayList of Paint command.
	 * If the parse was not successful, this.errorMessage is appropriately
	 * set, with a useful error message.
	 * 
	 * @param inputStream the open file to parse
	 * @return whether the complete file was successfully parsed
	 */
	public boolean parse(BufferedReader inputStream) {
		this.commands = new ArrayList<PaintCommand>();
		this.errorMessage="";
		
		// During the parse, we will be building one of the 
		// following shapes. As we parse the file, we modify 
		// the appropriate shape.
		
		Circle circle = null; 
		Rectangle rectangle = null;
		Squiggle squiggle = new Squiggle();
	
		try {	
			int state=0; Matcher m; String l;
			
			this.lineNumber=0;
			while ((l = inputStream.readLine()) != null) {
				l = l.replaceAll(" ", "");
				l = l.replaceAll("\t", "");
				this.lineNumber++;
				m=pNewLine.matcher(l);
				if(m.matches()){
					l = inputStream.readLine();
					l = l.replaceAll(" ", "");
				}
				System.out.println(lineNumber+" "+l+" "+state);
				switch(state){
					case 0:
						m=pFileStart.matcher(l);
						if(m.matches()){
							state=1;
							break;
						} else {
						error("Expected Start of Paint Save File");
						return false;
						}
					case 1: // Looking for the start of a new object or end of the save file
						m=pShapeStart.matcher(l);
						if(m.matches()){
							state=2;
							currentShape = m.group(1);
							break;
						} else if (pFileEnd.matcher(l).matches()){
							state=14;
							break;
						} else{
						error("Expected Start of Shape Object");
						return false;
						}
					case 2: //Checking Color
						m=pColor.matcher(l);
						ArrayList<Integer> RGBValues = new ArrayList<Integer>();
						if(m.matches()){
								RGBValues.add(Integer.parseInt(m.group(2)));
								RGBValues.add(Integer.parseInt(m.group(4)));
								RGBValues.add(Integer.parseInt(m.group(6)));
//							}
							System.out.println(RGBValues);
							color = new Color(RGBValues.get(0),RGBValues.get(1),RGBValues.get(2));
							state=3; 
							break;
						} else {
						error("Expected Start of Color");
						return false;
						}
					case 3: //Checking Filled and Shape Choice
						m=pFilled.matcher(l);
						if(m.matches()){
							isFilled = Boolean.parseBoolean(m.group(1));
							if(currentShape.equals("Circle")){
								state = 5;
								break;
							} else if(currentShape.equals("Rectangle")){
								state = 8;
								break;
							} else if(currentShape.equals("Squiggle")){
								state = 11;
								break;
							} else {
							error("No correct shape was chosen!");
							return false;
							}
						} else {
						error("Expected Start of Filled");
						return false;
						}

					case 5: //Checking center for Circle class
						m=pCenter.matcher(l);
						if(m.matches()){
							int p1 = Integer.parseInt(m.group(1));
							int p2 = Integer.parseInt(m.group(2));
							centre = new Point(p1,p2);
							state=6; 
							break;
						} else {
						error("Expected Start of Center");
						return false;
						}
					case 6: //Checking Radius for the Circle class
						m=pRadius.matcher(l);
						if(m.matches()){
							radius = Integer.parseInt(m.group(1));
							state=7; 
							break;
						} else {
						error("Expected Start of Radius");
						return false;
						}	
					case 7: //Checking End Circle for Circle Class
						m=pCircleEnd.matcher(l);
						if(m.matches()){
							circle = new Circle(centre, radius);
							circle.setColor(color);
							circle.setFill(isFilled);
							CircleCommand newCircle = new CircleCommand(circle);
							commands.add(newCircle);
							state = 1;
							break;
						} else {
						error("Expected End of Points");
						return false;
						}	
					case 8: //Checking Point 1 for the Rectangle Class
						m=pPoint1Rec.matcher(l);
						if(m.matches()){
							int p1 = Integer.parseInt(m.group(1));
							int p2 = Integer.parseInt(m.group(2));
							P1 = new Point(p1,p2);
							state=9; 
							break;
						} else {
						error("Expected Start of Point 1");
						return false;
						}		
					case 9: //Checking Point 2 for the Rectangle Class
						m=pPoint2Rec.matcher(l);
						if(m.matches()){
							int p1 = Integer.parseInt(m.group(1));
							int p2 = Integer.parseInt(m.group(2));
							P2 = new Point(p1,p2);
							state=10; 
							break;
						} else {
						// ADD CODE
						error("Expected Start of Point 2");
						return false;
						}	
					case 10: //Checking End Rectangle for Rectangle Class
						m=pRectangleEnd.matcher(l);
						if(m.matches()){
							rectangle = new Rectangle(P1,P2);
							rectangle.setColor(color);
							rectangle.setFill(isFilled);
							RectangleCommand newRectangle = new RectangleCommand(rectangle);
							commands.add(newRectangle);
							state = 1;
							break;
						} else {
						// ADD CODE
						error("Expected End of Points");
						return false;
						}	
					case 11: //Checking Points for Squiggle class
						m=pPointsStart.matcher(l);
						if(m.matches()){
							state=12; 
							break;
						} else {
						error("Expected Start of Point");
						return false;
						}		
					case 12: //Checking All Array points for Squiggle Class
						m=pPoint.matcher(l);
						if(m.matches()){
							m=pAllPoints.matcher(l);
							while (m.find()){
								squiggle.add(new Point(Integer.parseInt(m.group(1)),Integer.parseInt(m.group(2))));
							}
							state=12;
							break;
						} else if (pPointsEnd.matcher(l).matches()){ state = 13; break;}
						else{
						// ADD CODE
						error("Expected Start of Points");
						return false;
						}		
					case 13: //Checking End Point for Squiggle Class
						m=pSquiggleEnd.matcher(l);
						if(m.matches()){
							squiggle.setColor(color);
							squiggle.setFill(isFilled);
							System.out.println(squiggle.getPoints());
							SquiggleCommand newSquiggle = new SquiggleCommand(squiggle);
							commands.add(newSquiggle);
							state = 1;
							break;
						} else {
						error("Expected End of Points");
						return false;
						}
					case 14: //Checking End Point for Squiggle Class
						m=pAnythingElse.matcher(l);
						if(m.matches()){
							error("Nothing supposed to be here");
							return false;
						} else {
							return true;
						}
					
					// ...
				}
			}
		}  catch (Exception e){
			e.printStackTrace();
		}
		return true;
	}
}
