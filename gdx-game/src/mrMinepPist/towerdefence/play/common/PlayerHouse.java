package org.mrMinepPist.towerdefence.play.common;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.*;
import org.w3c.dom.*;
import org.mrMinepPist.towerdefence.files.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.*;
import java.util.*;
//дом игрока
public class PlayerHouse extends GameObject {
	private int level;
	private int[] coordsStart;
	public Array<Sprite> blocks;
	private Node XMLMap;
	private Array<Node> XMLElem;
	private Array<Sprite> elems;
	public int indexForPlayer;
	public PlayerHouse(int level) {
		this.level = level;
		blocks = new Array<Sprite>();
		XMLMap = XML.getPlayerHouse(XML.getLevelNode(XML.getLevelDocument(level)));
		coordsStart = new int[2];
		coordsStart[0] = Integer.parseInt(XMLMap.getAttributes().getNamedItem("x").getNodeValue());
		coordsStart[1] = Integer.parseInt(XMLMap.getAttributes().getNamedItem("y").getNodeValue());
		XMLElem = new Array<Node>();
		for(int i = 0; i < XMLMap.getChildNodes().getLength(); i++) {
			Node blockSchema = XMLMap.getChildNodes().item(i);
			if(XMLMap.getChildNodes().item(i).getNodeName().equals("blockSchema")) {
				for(int j = 0; j < blockSchema.getChildNodes().getLength(); j++) {
					if(XMLMap.getChildNodes().item(i).getChildNodes().item(j).getNodeName().equals("schemaElement")) {
						XMLElem.add(XMLMap.getChildNodes().item(i).getChildNodes().item(j));
					}
				}
			}
		}
		int j = 0;
		for(int i = 0; i < XMLElem.size; i++) {
			if(XMLElem.get(i).getAttributes().getNamedItem("texture").getNodeValue().equals("dirt")) {
				Sprite s = new Sprite(new Texture(Gdx.files.internal("resources/images/blocks/dirt.png")));
				s.setSize(48, 48);
				s.setPosition(coordsStart[0] + Integer.parseInt(XMLElem.get(i).getAttributes().getNamedItem("x").getNodeValue()) - 5, coordsStart[1] + 
				  Integer.parseInt(XMLElem.get(i).getAttributes().getNamedItem("y").getNodeValue()) - 5);
				if(XMLElem.get(i).getAttributes().getNamedItem("player").getNodeValue().equals("true")) {
					indexForPlayer = j;
				}
				blocks.add(s);
				j += 1;
			}
		}
	}
	@Override
	public void draw(Batch batch) {
		for(int i = 0; i < blocks.size; i++) {
			blocks.get(i).draw(batch);
		}
	}
}
