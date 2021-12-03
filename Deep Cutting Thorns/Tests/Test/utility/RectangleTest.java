package Test.utility;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import DCT.utility.Rectangle;
import DCT.utility.Vector;

class RectangleTest {

	@DisplayName("Should check if a rectangle contains a point")
	@Test
	void testContains() {
		Rectangle rectangle = new Rectangle(0, 0, 10, 10);
		Vector pointInside = new Vector(5, 5);
		Vector pointOutside = new Vector(20, 20);
		
		assertTrue(rectangle.contains(pointInside.getX(), pointInside.getY()));
		assertFalse(rectangle.contains(pointOutside.getX(), pointOutside.getY()));
	}

	@DisplayName("Should check if a rectangle intersects another")
	@Test
	void testIntersects() {
		Rectangle rectangleBase = new Rectangle(0, 0, 10, 10);
		Rectangle rectangleIntersepting = new Rectangle(-5, -5, 5, 5);
		Rectangle rectangleNotIntersepting = new Rectangle(20, 20, 10, 10);
		
		assertTrue(rectangleBase.intersects(rectangleIntersepting));
		assertFalse(rectangleBase.intersects(rectangleNotIntersepting));
	}

}
