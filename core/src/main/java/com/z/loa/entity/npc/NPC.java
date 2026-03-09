package com.z.loa.entity.npc;

import com.badlogic.gdx.*;
import com.badlogic.gdx.ai.steer.*;
import com.badlogic.gdx.ai.steer.behaviors.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.*;
import com.z.loa.entity.*;
import com.z.loa.entity.player.*;
import com.z.loa.manager.*;
import com.z.loa.screen.*;
import java.util.*;

public class NPC extends BaseEntity {
	private String defaultOrientation;
	public String initialConversation;
	private ZhanTingyun zhanTingyun;

	private final ActorSteerable steerable;
	private final SteeringBehavior<Vector2> wander;
	private final SteeringAcceleration<Vector2> steering;
	private final Vector2 velocity = new Vector2();
	private float wanderTimer;

	private boolean isWander;
	private boolean collision = false;
	private boolean frozenFacingForDialog = false;

	public NPC(float x, float y, ZhanTingyun zhan_tingyun, boolean is_wander) {
		this.setPosition(x, y);
		this.zhanTingyun = zhan_tingyun;
		steerable = new ActorSteerable(this);
		Wander<Vector2> w = new Wander<Vector2>(steerable);
		w.setWanderOffset(8.0f).setWanderRadius(5.0f).setWanderRate(20.0f);
		w.setWanderOrientation(MathUtils.random(0, 360));
		wander = w;
		steering = new SteeringAcceleration<Vector2>(new Vector2());
		this.isWander = is_wander;
	}

	public void create(String name, int idle_index) {
		TextureRegion[][] split = TextureCache.getSplit(name);
		if (split == null) {
			Gdx.app.error("NPC", "纹理加载失败：" + name);
			return;
		}
		this.setSize(split[0][idle_index].getRegionWidth() * GameScreen.scale,
				split[0][idle_index].getRegionHeight() * GameScreen.scale);
		idleTextureArray = new TextureRegion[]{split[0][idle_index], split[1][idle_index], split[2][idle_index],
				split[3][idle_index]};

		if (isWander) {
			for (int i = 0; i < 4; i++) {
				TextureRegion[] temp = new TextureRegion[3];
				for (int j = 0; j < 3; j++) {
					temp[j] = split[i][j];
				}
				moveAnimation[i] = new Animation<TextureRegion>(0.25f, temp);
				moveAnimation[i].setPlayMode(Animation.PlayMode.LOOP);
			}
		}

	}

	public void initializeProperties(String initial_conversation, String sub_conversation, String default_orientation,
			boolean collision) {
		this.defaultOrientation = default_orientation;
		this.collision = collision;
		this.initialConversation = initial_conversation;
		defaultOrientation();
		entityState = EntityState.IDLE;
	}


	//前两传入的参数可以是null
	public static boolean isNotCharacterCollision(ZhanTingyun self_player, NPC self_npc, float x, float y) {
		Rectangle test_rect = new Rectangle(x / GameScreen.scale, y / GameScreen.scale, 16, 8);
		Set<NPC> NPCMap_key_set = GameScreen.NPCMap.keySet();
		Set<ZhanTingyun> playerMap_key_set = GameScreen.playerMap.keySet();
		for (ZhanTingyun key : playerMap_key_set) {
			if (key == self_player) {
				break;
			}
			Rectangle rectangle = GameScreen.playerMap.get(key);
			if (test_rect.overlaps(rectangle)) {
				return false;
			}
		}
		for (NPC key : NPCMap_key_set) {
			if (!key.collision || key == self_npc) {
				continue;
			}
			//每个地图NPC对象对应的矩形
			Rectangle rectangle = GameScreen.NPCMap.get(key);
			if (test_rect.overlaps(rectangle)) {
				if (self_player != null && self_player.buttons[4].isPressed()) {
					DialogManager.activeNPC = key;
				}
				return false;
			}
		}
		return true;
	}

	private void updateRectangle() {
		if (isWander && collision) {
			Rectangle rectangle = new Rectangle(this.getX() / GameScreen.scale, this.getY() / GameScreen.scale, 16, 8);
			GameScreen.NPCMap.put(this, rectangle);
		}
	}

	public void defaultOrientation() {
		if (defaultOrientation.equals("down")) {
			orientation = Orientation.DOWN;
		} else if (defaultOrientation.equals("up")) {
			orientation = Orientation.UP;
		} else if (defaultOrientation.equals("left")) {
			orientation = Orientation.LEFT;
		} else {
			orientation = Orientation.RIGHT;
		}
	}

	public void setFrozenFacingForDialog(boolean frozen) {
		this.frozenFacingForDialog = frozen;
	}
	public void facePlayer() {
		if (zhanTingyun == null) {
			return;
		}
		if (!frozenFacingForDialog) {
			defaultOrientation();
		}
		float dx = zhanTingyun.getX() - this.getX();
		float dy = zhanTingyun.getY() - this.getY();
		if (Math.abs(dx) < 0.1f && Math.abs(dy) < 0.1f) {
			return;
		}
		float angle = MathUtils.atan2(dy, dx) * MathUtils.radiansToDegrees;
		if (angle >= -45f && angle < 45f) {
			orientation = Orientation.RIGHT;
		} else if (angle >= 45f && angle < 135f) {
			orientation = Orientation.UP;
		} else if (angle >= 135f || angle < -135f) {
			orientation = Orientation.LEFT;
		} else {
			orientation = Orientation.DOWN;
		}
	}

	private void steeringOrientation() {
		if (frozenFacingForDialog && DialogManager.isTextPlaying && DialogManager.activeNPC == this) {
			return;
		}

		float angle = steerable.vectorToAngle(velocity) * MathUtils.radiansToDegrees;
		if (angle >= -45 && angle < 45) {
			orientation = Orientation.RIGHT;
		} else if (angle >= 45 && angle < 135) {
			orientation = Orientation.UP;
		} else if (angle >= 135 || angle < -135) {
			orientation = Orientation.LEFT;
		} else {
			orientation = Orientation.DOWN;
		}

	}

	private boolean isPlayerNearby() {
		if (zhanTingyun == null)
			return false;

		float distance = Vector2.dst(getX() + getWidth() / 2, getY() + getHeight() / 2,
				zhanTingyun.getX() + zhanTingyun.getWidth() / 2, zhanTingyun.getY() + zhanTingyun.getHeight() / 2);

		return distance < 75;
	}

	@Override
	public void act(float delta) {
		super.act(delta);
		if (isWander) {
			wanderTimer += delta;
			if (wanderTimer > 0.4f) {
				wander.calculateSteering(steering);
				wanderTimer = 0f;
			}

			if (entityState == EntityState.IDLE) {
				steering.linear.setZero();
				velocity.x = 0;
				velocity.y = 0;
				steering.angular = 0;
			}
			velocity.x += steering.linear.x * delta;
			velocity.y += steering.linear.y * delta;
			if (velocity.len() > steerable.getMaxLinearSpeed()) {
				velocity.setLength(steerable.getMaxLinearSpeed());
			}
			float new_x = this.getX() + velocity.x * delta;
			float new_y = this.getY() + velocity.y * delta;
			if (!isPlayerNearby()) {

				if (isNotCharacterCollision(null, this, new_x, new_y)
						&& GameScreen.isNotTerrainCollision(new_x, new_y)) {
					entityState = EntityState.MOVE;

					updateRectangle();
					this.setPosition(new_x, new_y);

				} else {

					if (wanderTimer > 0.3f) {
						velocity.rotateDeg(MathUtils.random(90, 270));
						wanderTimer = 0.0f;
					}
				}
			} else {
				entityState = EntityState.IDLE;
			}

		}

	}

	@Override
	public void draw(Batch batch, float parentAlpha) {
		if (isWander) {
			steeringOrientation();
		}

		if (DialogManager.isDialogueEnd) {
			if (!isWander) {
				defaultOrientation();
			}

			DialogManager.activeNPC = null;
		}
		//Gdx.app.error("current", (currentFrame == null) + "");
		batch.draw(currentFrame, this.getX(), this.getY(), this.getWidth(), this.getHeight());
	}

	public TextureRegion getCurrentFrame() {
		return currentFrame;
	}

}

