package com.z.loa.entity.event.battle;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.z.loa.entity.BaseEntity;
import com.z.loa.entity.config.BattleActionConfig;

public class EffectTriggerEvent extends Event {
    private BattleActionConfig actionConfig;

    public EffectTriggerEvent() {
        
    }
    
    public EffectTriggerEvent(BattleActionConfig config) {
        super();
        this.actionConfig = config;
    }

    public BattleActionConfig getActionConfig() {
        return this.actionConfig;
    }

    public void setActionConfig(BattleActionConfig config) {
        this.actionConfig = config;
    }

    @Override
    public BaseEntity getTarget() {
        return (BaseEntity) super.getTarget();
    }
}
