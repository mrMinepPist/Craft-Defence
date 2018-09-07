package org.mrMinepPist.towerdefence.play.common;
import com.badlogic.gdx.graphics.*;
//обьект сущности
public class EntityObject extends GameObject {
	public enum motionState {
		IDLE, 
		MOTION {
			public void setMotionStepsCount(float f) {
				motionSteps = f;
			}
		};
		public float motionSteps = 0;
		public float stepSize = 0;
	}
	public enum motionSide {
		NORD,
		SOUTH,
		WEST,
		EAST
	}
	//перечисления оружия
	public final enum entityTYPE {
		ZOMBIE,
		SKELETON,
		SPIDER,
		CREEPER,
		ZOMBIE_BOSS
	}
	public static final class ToolTYPE {
		public interface toolTYPE {}
		public final enum swordTYPE implements toolTYPE {
			WOODEN,
			STONE,
			IRON,
			BRONZE,
			DIAMOND
		}
		public final enum axeTYPE implements toolTYPE {
			WOODEN,
			STONE,
			IRON,
			BRONZE,
			DIAMOND
		}
		public final enum hammerTYPE implements toolTYPE {
			molotok,
			HAMMER,
			PIKO,
		}
	}
	public motionState moveState = motionState.IDLE;
	public motionSide moveSide;
	public entityTYPE entitytype;
	public ToolTYPE.toolTYPE tooltype;
	public EntityObject() {
		super();
	}
	public EntityObject(Texture texture) {
		super(texture);
	}
	public EntityObject(Texture texture, int srcX, int srcY, int srcWidth, int srcHeight) {
		super(texture, srcX, srcY, srcWidth, srcHeight);
	}
	public void setMotionState(motionState s, motionSide t) {
		if(s == motionState.IDLE) {
			s.motionSteps = 0;
		}
		moveState = s;
		moveSide = t;
	}
	public void moveByMotionState() {
		if(moveState.motionSteps != 0) {
			if(moveSide == motionSide.EAST) {
				this.setPosition(this.getPosition()[0] + moveState.stepSize, this.getPosition()[1]);
			} else if(moveSide == motionSide.NORD) {
				this.setPosition(this.getPosition()[0], this.getPosition()[1] + moveState.stepSize);
			} else if(moveSide == motionSide.WEST) {
				this.setPosition(this.getPosition()[0] - moveState.stepSize, this.getPosition()[1]);
			} else if(moveSide == motionSide.SOUTH) {
				this.setPosition(this.getPosition()[0], this.getPosition()[1] - moveState.stepSize);
			} 
			moveState.motionSteps = moveState.motionSteps - 1;
		} 
	}
}
