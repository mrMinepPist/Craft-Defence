package org.mrMinepPist.towerdefence.play.common;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.*;
//обьект раздатчика
public class DispenserObject extends GameObject {
	//перечисление
	public enum DispenserTypes {
		SLAB_WOODEN_DISPENSER,
		WOODEN_DISPENSER
	}
	public DispenserTypes type;
	public DispenserObject(DispenserTypes type) {
		super(new Texture(Gdx.files.internal("resources/images/dispensers/slab_wooden.jpeg")));
		this.type = type;
		setTexture(new Texture(Gdx.files.internal(getPath())));
	}
	//получить путь
	public String getPath() {
		if(type == DispenserTypes.SLAB_WOODEN_DISPENSER) {
			return "resources/images/dispensers/slab_wooden.jpeg";
		} else if(type == DispenserTypes.WOODEN_DISPENSER) {
			return "resources/images/dispensers/wooden.jpeg";
		}
		return "";
	}
}
