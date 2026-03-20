package com.z.loa.manager;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.Timer;
import com.z.loa.entity.BaseEntity;
import com.z.loa.entity.config.BattleActionConfig;
import com.z.loa.screen.BattleScene;

public class EffectManager {
    private Group effectGroup;
    private ObjectMap<String, Animation<TextureRegion>> animationMap;
    private BattleScene battleScene;
    
    public EffectManager(Group effect, ObjectMap<String, Animation<TextureRegion>> animation, BattleScene scene) {
        this.effectGroup = effect;
        this.animationMap = animation;
        this.battleScene = scene;
    }
    
    //target用于发起特效结束事件
    public void postEffect(BattleActionConfig config, BaseEntity target, Array<BaseEntity> aims) {
    	String key = config.getEffectId();
        Animation<TextureRegion> animation = animationMap.get(key);
        for (BaseEntity aim : aims) {
            float[] size = config.getSize();
            float width = size[0];
            float height = size[1];
            float x, y;
            if (config.isSpecialPosition()) {
                float[] position = config.getPosition();
                x = position[0];
                y = position[1];
                BattleScene.EffectActor effect_actor = battleScene.new EffectActor(target, animation, x, y, width, height);
                effectGroup.addActor(effect_actor);
                break;
//            } else if (config.getSize()[0] >= aim.getWidth() * 1.8) {
//                x = aim.getX() - config.getSize()[0] * 0.2f;
//                y = aim.getY();
            }else {
                x = aim.getX() + (aim.getWidth() - width) / 2;
                y = aim.getY();
            }
            
            BattleScene.EffectActor effect_actor = battleScene.new EffectActor(target, animation, x, y, width, height);
            effectGroup.addActor(effect_actor);
            aim.setBattleState(BaseEntity.BattleState.DEFEND);
        }
    }
    //objectives是闪烁对象Array,不一定含事件发起对象
    public void postFlash(BattleActionConfig config, Array<BaseEntity> objectives) {
        for (BaseEntity objective : objectives) {
            int count = config.getFlashCount();
            float duration = count * 0.2f;
            Vector3 rgb = config.getFlashRgb();
            float delay_time = config.getFlashDelayTime();
            if (delay_time == 0) {
                objective.hitFlash(count, duration, rgb);
                return;
            }
            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    objective.hitFlash(count, duration, rgb);
                }
            },delay_time);
        }
    }
}
