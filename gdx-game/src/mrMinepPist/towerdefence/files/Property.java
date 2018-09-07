package org.mrMinepPist.towerdefence.files;
import java.util.*;
import java.io.*;
import com.badlogic.gdx.*;
import com.badlogic.gdx.utils.*;
//Properties локализаций
public class Property extends Properties {
	//map
	public static ArrayMap<String, Property> map = new ArrayMap<String, Property>();
	private Property() {}
	public static void load() throws IOException {
		Property en = new Property();
		if(Gdx.files.internal("resources/lang/en_US.lang").exists()) {
			en.load(new StringReader(Gdx.files.internal("resources/lang/en_US.lang").readString("UTF-8")));
			map.put("en_US", en);
		}
		Property ru = new Property();
		if(Gdx.files.internal("resources/lang/ru_RU.lang").exists()) {
			ru.load(new StringReader(Gdx.files.internal("resources/lang/ru_RU.lang").readString("UTF-8")));
			map.put("ru_RU", ru);
		}
	}
	//локализованный Property
	public static Property getDefaultLocaleProperty() {
		Array<String> arr = new Array<String>(map.keys().toArray());
		for(int i = 0; i < arr.size; i++) {
			if(arr.get(i).equals(Locale.getDefault().toString())) {
				return map.get(Locale.getDefault().toString());
			}
		}
		return map.get("en_US");
	}
	//получить String по key
	public static String getValueString(Property property, String key) {
		return property.getProperty(key);
	}
	//получить String по key
	public static String getDefaultValueString(String key) {
		return getValueString(getDefaultLocaleProperty(), key);
	}
}
