package Test.utility;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import DCT.utility.Vector;

class VectorTest {

	@Test
	void testVector() {
		Vector v = new Vector();

		assertEquals(v.getX(), 0);
		assertEquals(v.getY(), 0);
	}

	@Test
	void testVectorIntInt() {
		Vector v = new Vector(12, 22);

		assertEquals(v.getX(), 12);
		assertEquals(v.getY(), 22);
	}

	@Test
	void testVectorVector() {
		Vector vOrig = new Vector(12, 22);

		Vector v = new Vector(vOrig);

		assertEquals(v.getX(), vOrig.getX());
		assertEquals(v.getY(), vOrig.getY());
	}

	@Test
	void testNormalize() {
		Vector v00 = new Vector();
		Vector v01 = new Vector(0, 5);
		Vector v0_1 = new Vector(0, -2);
		Vector v10 = new Vector(2, 0);
		Vector v1_0 = new Vector(-2, 0);
		Vector v11 = new Vector(12, 22);
		Vector v1_1 = new Vector(-12, -2);

		v00.normalize();
		v01.normalize();
		v0_1.normalize();
		v10.normalize();
		v1_0.normalize();
		v11.normalize();
		v1_1.normalize();

		assertEquals(v00.getX(), 0);
		assertEquals(v00.getY(), 0);

		assertEquals(v01.getX(), 0);
		assertEquals(v01.getY(), 1);

		assertEquals(v0_1.getX(), 0);
		assertEquals(v0_1.getY(), -1);

		assertEquals(v10.getX(), 1);
		assertEquals(v10.getY(), 0);

		assertEquals(v1_0.getX(), -1);
		assertEquals(v1_0.getY(), 0);

		assertEquals(v11.getX(), 1);
		assertEquals(v11.getY(), 1);

		assertEquals(v1_1.getX(), -1);
		assertEquals(v1_1.getY(), -1);
	}

}
