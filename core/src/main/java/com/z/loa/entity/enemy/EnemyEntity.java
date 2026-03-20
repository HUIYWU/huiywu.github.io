package com.z.loa.entity.enemy;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.z.loa.entity.event.battle.EffectTriggerEvent;
import com.z.loa.screen.GameScreen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.z.loa.entity.BaseEntity;

public class EnemyEntity extends BaseEntity {
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        // TODO: Implement this method
    }
    
    @Override
    protected void matchBattleAnimation() {
        super.currentFrame = super.battleAnimation[0].getKeyFrame(super.stateTime);
        Animation<TextureRegion> animation = battleAnimation[0];
        int index = animation.getKeyFrameIndex(super.stateTime);
        switch (battleState) {
            case AWAIT:
                break;
            case ATTACK:
                if (super.actionConfig != null) {
                    if (index == super.actionConfig.getEffectTriggerIndex() && !super.eventFire) {
                        this.fire(new EffectTriggerEvent(super.actionConfig));
                        super.eventFire = true;
                    }
                }
                if (animation.isAnimationFinished(super.stateTime)) {
                    super.battleState = BattleState.AWAIT;
                }

                break;
            case SKILL:
                if (super.actionConfig != null) {
                    if (index == super.actionConfig.getEffectTriggerIndex() && !super.eventFire) {
                        this.fire(new EffectTriggerEvent(super.actionConfig));
                        super.eventFire = true;
                    }
                }

                break;
            case DEFEND:
            case WEAK:
            case DEFEATED:
            case WON:
        }

    }
    
    
    
}
