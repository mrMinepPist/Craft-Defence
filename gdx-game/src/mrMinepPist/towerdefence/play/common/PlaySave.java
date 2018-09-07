package org.mrMinepPist.towerdefence.play.common;
import com.badlogic.gdx.utils.*;
//сохранение
public class PlaySave {
	public static int level;
	private static Array<EntityObject> entities;
	private static Array<DispenserObject> dispensers;
	private PlaySave() {
		entities = new Array<EntityObject>();
		dispensers = new Array<DispenserObject>();
	}
	public static PlaySave INSTANCE = new PlaySave();
	public static void addEntity(EntityObject obj) {
		entities.add(obj);
	}
	public static void addDispenser(DispenserObject obj) {
		dispensers.add(obj);
	}
	public static Array<EntityObject> getEntities() {
		return entities;
	}
	public static Array<DispenserObject> getDispensers() {
		return dispensers;
	}
	public static EntityObject getEntity(int index) {
		return entities.get(index);
	}
	public static DispenserObject getDispenser(int index) {
		return dispensers.get(index);
	}
	public static void clearEntities() {
		entities.clear();
	}
	public static void clearDispensers() {
		dispensers.clear();
	}
}
