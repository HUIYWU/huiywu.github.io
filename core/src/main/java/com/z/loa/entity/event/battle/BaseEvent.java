package com.z.loa.entity.event.battle;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.z.loa.entity.BaseEntity;

public class BaseEvent extends Event {
    private BaseEntity source;

    public BaseEvent(BaseEntity source) {
        this.source = source;
    }

    public BaseEntity getSource() {
        return this.source;
    }

    public void setSource(BaseEntity source) {
        this.source = source;
    }
}
