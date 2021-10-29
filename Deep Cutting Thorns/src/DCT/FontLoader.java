package DCT;

import  java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;

public class FontLoader {

	public static Font fontLoad(String path,float size) {
		Font font;
		try {
			font=Font.createFont(Font.TRUETYPE_FONT, new File(path));
			font.deriveFont(Font.PLAIN, size);
			return font;
		}catch(FontFormatException e) {
			 e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-2);
		}
		return null;
		
	}
}
