package DCT.state.save_load;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SaverLoader {

	public static void save(Serializable data, String path) throws Exception{
		try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(path)))) {
			oos.writeObject(data);
			System.out.println("Game correctly saved!");
		}
	}
	
	public static Object load(String path) throws Exception {
		try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(path)))) {
			System.out.println("Game correctly loaded!");
			return ois.readObject();
		}
	}
}
