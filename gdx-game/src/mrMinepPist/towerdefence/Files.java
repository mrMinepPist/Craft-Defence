package org.mrMinepPist.towerdefence.files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
//файлы
public class Files {
	public static FileHandle[] files = Gdx.files.internal("resources/level/").list();
}
