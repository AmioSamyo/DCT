package DCT.state.save_load;

import java.io.Serializable;
import java.util.ArrayList;

import DCT.entity.Entity;

public class StateSavingData implements Serializable {
	
	private ArrayList<Entity> entities;
	
	private static final long serialVersionUID = 1L;
	
	public void setEntities(ArrayList<Entity> list) {
		this.entities = list;
	}
	
	public ArrayList<Entity> getEntities() {
		return this.entities;
	}

}
