package Test.utility;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import DCT.utility.Utils;

class UtilsTest {

	@DisplayName("Should convert a String to Int")
	@Test
	void testParseInt() {
		assertEquals(1, Utils.parseInt("1"));
		assertEquals(89, Utils.parseInt("89"));
		assertEquals(-3, Utils.parseInt("-3"));
	}

}
