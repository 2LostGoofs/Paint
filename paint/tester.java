package ca.utoronto.utm.paint;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class tester {

	

public static void main(String args[]) {
	String line = "			point:(87,82)   point:(87,84)   point:(87,85)   point:(87,86)   point:(87,87)  ";
	Pattern pattern = Pattern.compile("(\\s*point:\\(([1-9][0-9]?|[12][0-9][0-9]|300),([1-9][0-9]?|[12][0-9][0-9]|300)\\)+)");
	Matcher matcher = pattern.matcher(line);
	ArrayList <Point> points = new ArrayList <Point>();
	while (matcher.find()) {
		points.add(new Point(Integer.parseInt(matcher.group(2)),Integer.parseInt(matcher.group(3))));
	    System.out.println(matcher.group(3));
//	    System.out.println("group 2: " + matcher.group(3));
//	    System.out.println("group 3: " + matcher.group(3));
//	System.out.println(matcher.matches());
	}
	System.out.println(points);
}
}
