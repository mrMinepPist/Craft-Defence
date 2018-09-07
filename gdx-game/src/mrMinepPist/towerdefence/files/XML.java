package org.mrMinepPist.towerdefence.files;
import com.badlogic.gdx.Gdx;
import java.io.StringReader;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
//класс XML
public class XML {
	//получить документ уровня
	public static Document getLevelDocument(int i) {
		try {
			String xml = Gdx.files.internal("resources/level/level_" + Integer.toString(i) + ".xml").readString();
			DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder(); 
			InputSource is = new InputSource(); 
			is.setCharacterStream(new StringReader(xml)); 
			return db.parse(is);
		} catch(SAXException e) {
		} catch(ParserConfigurationException e) {
		} catch(IOException e) {}
		return null;
	}
	//получить Ноду уровня
	public static Node getLevelNode(Document doc) {
		return doc.getDocumentElement();
	}
	//получить Ноду карты
	public static Node getgameMap(Node levelNode) {
		NodeList levelElements = levelNode.getChildNodes();
		for (int i = 0; i < levelElements.getLength(); i++) {
			Node map = levelElements.item(i);
			if (levelElements.item(i).getNodeName().equals("gameMap")) {
				return map;
			}
		}
		return null;
	}
	//получить ноду Дома
	public static Node getPlayerHouse(Node levelNode) {
		NodeList levelElements = levelNode.getChildNodes();
		for (int i = 0; i < levelElements.getLength(); i++) {
			Node map = levelElements.item(i);
			if (levelElements.item(i).getNodeName().equals("house")) {
				return map;
			}
		}
		return null;
	}
	//получить ноду Волны
	public static Node getWaveSchema(Node levelNode) {
		NodeList levelElements = levelNode.getChildNodes();
		for (int i = 0; i < levelElements.getLength(); i++) {
			Node map = levelElements.item(i);
			if (levelElements.item(i).getNodeName().equals("waveSchema")) {
				return map;
			}
		}
		return null;
	}
}
