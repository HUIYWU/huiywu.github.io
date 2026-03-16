package com.z.loa.entity.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.ObjectMap;
import com.z.loa.Constants;
import com.z.loa.data.ActionCarrier;
import com.z.loa.data.ActionData;
import com.z.loa.manager.FontManager;

public class BattleActionConfig {
    private static ActionData data;
    private static ObjectMap<String, BattleActionConfig[]> configsMap;
    private String effectId;
    private String actionName;
    private String tips;
    private int effectTriggerIndex;
    private int stateIndex;
    private AimType aimType;
    private ActionType actionType;
    private boolean specialPosition;
    private float[] position;
    private float[] size;
    private boolean flashFollow;
    private float flashDelayTime;
    private int flashCount;
    private float flashDuration;
    private Vector3 flashRgb;
    private int mpCost;

    public enum AimType {
        FRIEND_SINGLE,
        FRIEND_ALL,
        ENEMY_SINGLE,
        ENEMY_ALL;
    }

    public enum ActionType {
        DAMAGE,
        BUFF,
        HEAL;
    }
    
    static {
        configsMap = new ObjectMap<String, BattleActionConfig[]>();
    }

    private BattleActionConfig() {}

    public static void loadConfig() {
        FileHandle file = Gdx.files.internal("data/action/action.json");
        Json json = new Json();
        data = json.fromJson(ActionData.class, file);
    }

    public static BattleActionConfig[] obtainConfigs(String name) {
        if(configsMap.containsKey(name)) {
        	return configsMap.get(name);
        }
        ActionData.Player player = data.players.get(name);
        String text = player.actionString;
        FontManager.updateFont(text);
        Array<String> actions = player.actionArray;
        BattleActionConfig[] configs = new BattleActionConfig[actions.size];
        for (int i = 0; i < configs.length; i++) {
            String id = actions.get(i);
            BattleActionConfig config = new BattleActionConfig();
            config.setParameter(config, id);
            configs[i] = config;
        }
        configsMap.put(name, configs);
        return configs;
    }
    
    @Deprecated
    public static ActionData.Player getPlayerData(String name) {
    	return data.players.get(name);
    }
    @Deprecated
    public static ActionData.Enemy getEnemyData(String name) {
    	return data.enemies.get(name);
    }
    
    public static ActionCarrier getCarrierData(String id) {
    	if(data.players.containsKey(id)) {
    		return data.players.get(id);
    	} else if (data.enemies.containsKey(id)){
    		return data.enemies.get(id);
    	}
        return null;
    }
    
    @Deprecated
    public static BattleActionConfig createAttackConfig() {
        BattleActionConfig config = new BattleActionConfig();
        config.setEffectId("animation07003");
        config.setActionName("战千屻普通攻击");
        config.setEffectTriggerIndex(3);
        config.setStateIndex(1);
        config.setAimType(AimType.ENEMY_SINGLE);
        config.setSize(453.4f, 342.0f);
        config.setFlashDelayTime(0.2f);
        config.setFlashCount(1);
        config.setFlashDuration(0.2f);
        config.setFlashRgb(new Vector3(1.0f, 1.0f, 1.0f));
        return config;
    }
    
    @Deprecated
    public static BattleActionConfig createQiongQiConfig() {
        BattleActionConfig config = new BattleActionConfig();
        config.setEffectId("animation07001");
        config.setActionName("[RED]穷奇招唤");
        config.setTips("[RED]凝聚凶兽力量，发出猛烈咆哮，给予敌方全体毁灭打击。");
        config.setStateIndex(2);
        config.setAimType(AimType.ENEMY_ALL);
        config.setSpecialPosition(true);
        config.setPosition(0.0f, 56.5f);
        config.setSize(790.0f, 540.0f);
        config.setFlashDelayTime(0.8f);
        config.setFlashCount(3);
        config.setFlashDuration(0.6f);
        config.setFlashRgb(new Vector3(0.235f, 0.0f, 0.392f));
        return config;
    }

    private void setParameter(BattleActionConfig config, String id) {
        ActionData.Action action = data.actions.get(id);
        config.setEffectId(id);
        config.setActionName(action.actionName);
        config.setTips(action.tips);
        config.setEffectTriggerIndex(action.effectTriggerIndex);
        config.setStateIndex(action.stateIndex);
        config.setAimType(AimType.valueOf(action.aimType));
        config.setActionType(ActionType.valueOf(action.actionType));
        config.setSpecialPosition(action.specialPosition);
        config.setPosition(action.position);
        config.setSize(action.size);
        config.setFlashDelayTime(action.flashDelayTime);
        config.setFlashCount(action.flashCount);
        config.setFlashRgb(action.flashRgb);
        config.setMpCost(action.mpCost);
    }

    public String getEffectId() {
        return this.effectId;
    }

    public void setEffectId(String id) {
        this.effectId = id;
    }

    public String getActionName() {
        return this.actionName;
    }

    public void setActionName(String name) {
        this.actionName = name;
    }

    public String getTips() {
        return this.tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public int getEffectTriggerIndex() {
        return this.effectTriggerIndex;
    }

    public void setEffectTriggerIndex(int index) {
        this.effectTriggerIndex = index;
    }

    public int getStateIndex() {
        return this.stateIndex;
    }

    public void setStateIndex(int index) {
        this.stateIndex = index;
    }

    public AimType getAimType() {
        return this.aimType;
    }

    public void setAimType(AimType type) {
        this.aimType = type;
    }

    public boolean isSpecialPosition() {
        return this.specialPosition;
    }

    public void setSpecialPosition(boolean special) {
        this.specialPosition = special;
    }

    public float[] getSize() {
        return this.size;
    }

    public void setSize(float width, float height) {
        if (size == null) {
            size = new float[2];
        }
        size[0] = width;
        size[1] = height;
    }

    public void setSize(float[] size) {
        size[0] *= Constants.WIDTH_RATIO;
        size[1] *= Constants.HEIGHT_RATIO;
        this.size = size;
    }

    public boolean isFlashFollow() {
        return this.flashFollow;
    }

    public void setFlashFollow(boolean follow) {
        this.flashFollow = follow;
    }

    public float[] getPosition() {
        return this.position;
    }

    public void setPosition(float x, float y) {
        if (position == null) {
            position = new float[2];
        }
        position[0] = x;
        position[1] = y;
    }

    public void setPosition(float[] position) {
        if (position.length == 0) {
            return;
        }
        position[0] *= Constants.WIDTH_RATIO;
        position[1] *= Constants.HEIGHT_RATIO;
        this.position = position;
    }

    public float getFlashDelayTime() {
        return this.flashDelayTime;
    }

    public void setFlashDelayTime(float time) {
        this.flashDelayTime = time;
    }

    public int getFlashCount() {
        return this.flashCount;
    }

    public void setFlashCount(int count) {
        this.flashCount = count;
    }

    public float getFlashDuration() {
        return this.flashDuration;
    }

    public void setFlashDuration(float duration) {
        this.flashDuration = duration;
    }

    public Vector3 getFlashRgb() {
        return this.flashRgb;
    }

    public void setFlashRgb(Vector3 rgb) {
        this.flashRgb = rgb;
    }

    public ActionType getActionType() {
        return this.actionType;
    }

    public void setActionType(ActionType actionType) {
        this.actionType = actionType;
    }

    public int getMpCost() {
        return this.mpCost;
    }

    public void setMpCost(int mpCost) {
        this.mpCost = mpCost;
    }
}
