package com.z.loa.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.z.loa.entity.BaseEntity;
import com.z.loa.entity.config.BattleActionConfig;
import com.z.loa.entity.enemy.XuanyuanYin;

public class BattleActionManager {
    private Array<BaseEntity> entities;
    
    public BattleActionManager(Array<BaseEntity> entities) {
        this.entities = entities;
    }
    
    public void bindAction() {
    	
    }
    
    public Array<BaseEntity> selectAim(BaseEntity target, BattleActionConfig config) {
        Array<BaseEntity> aims = new Array<>();
        
        BattleActionConfig.AimType type = config.getAimType();
        for(BaseEntity entity : entities) {
            switch(type) {
                case FRIEND_SINGLE :
                    break;
                case FRIEND_ALL :
                    break;
                case ENEMY_SINGLE :
                    if(entity instanceof XuanyuanYin) {
                    	aims.add(entity);
                    }
                    break;
                case ENEMY_ALL :
                     if(entity instanceof XuanyuanYin) {
                        aims.add(entity);
                     }
                     break;
                default :
                    break;
            }
        	
        }
        return aims;
    }
    
    public void triggerAction(BaseEntity target, BattleActionConfig config) {
    	target.setBattleState(BaseEntity.BattleState.values()[config.getStateIndex()]);
        target.setStateTime(0);
    }
    
    
    
}
