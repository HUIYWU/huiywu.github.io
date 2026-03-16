package com.z.loa.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.z.loa.entity.BaseEntity;
import com.z.loa.entity.config.BattleActionConfig;
import com.z.loa.entity.event.battle.EffectFinishEvent;
import com.z.loa.entity.event.battle.EffectTriggerEvent;
import com.z.loa.screen.BattleScene;

public class TurnManager {
    private Array<BaseEntity> participants;
    private Array<BaseEntity> players;
    private BattleScene scene;
    private BattleActionManager actionManager;
    private EffectManager effectManager;
    private int turnIndex;
    private BaseEntity activeParticipant;
    private boolean waitingForOperation;

    private String mark;

    public TurnManager() {}

    public TurnManager(
            Array<BaseEntity> participants, Array<BaseEntity> players, BattleScene scene) {
        this.participants = participants;
        this.players = players;
        this.scene = scene;
    }

    public void init(BattleActionManager manager_1, EffectManager manager_2) {
        this.actionManager = manager_1;
        this.effectManager = manager_2;
        registerParticipantListener();
    }

    private void registerParticipantListener() {
        for (BaseEntity participant : participants) {
            participant.addListener(
                    new EventListener() {
                        //防止多个结束事件同时发生;
                        private boolean round = false;
                        @Override
                        public boolean handle(Event event) {
                            if (event.getTarget() != participant) {
                                return false;
                            }
                            
                            if (event instanceof EffectFinishEvent) {
                                if(round) {
                                	scene.recoverLowerPart();
                                    scene.clearTwinText();
                                    scene.enableDialog(true);
                                    endTurn();
                                    round = false;
                                }
                                return true;
                            } else if (event instanceof EffectTriggerEvent) {
                                round = true;
                                scene.preprocess((TextButton)scene.getButtonTable().getChild(0));
                                Timer.schedule(new Timer.Task() {
                                    @Override
                                    public void run() {
                                        scene.setStateImage();
                                    }
                                }, 0.1f);
                                EffectTriggerEvent trigger_event = (EffectTriggerEvent) event;
                                BaseEntity target = trigger_event.getTarget(); // target是事件发起对象
                                BattleActionConfig config = trigger_event.getActionConfig();
                                Array<BaseEntity> aims = actionManager.getAims(); // aim是选择的对象
                                effectManager.postEffect(config, target, aims);
                                if (config.isFlashFollow()) {
                                    Array<BaseEntity> target_1 = new Array<>();
                                    target_1.add(target);
                                    effectManager.postFlash(config, target_1);
                                } else {
                                    effectManager.postFlash(config, aims);
                                }
                                target.resetEventFire();
                                return true;
                            }
                            return false;
                        }
                    });
        }
    }

    public void startBattle() {
        turnIndex = 3;
        Timer.schedule(
                new Timer.Task() {
                    @Override
                    public void run() {
                        startTurn();
                    }
                },
                0.2f);
    }

    private void startTurn() {
        mark = "";
        activeParticipant = participants.get(turnIndex);
        if (isPlayer(activeParticipant)) {
            waitingForOperation = true;
            scene.enablePlayerControl(true, activeParticipant);
            scene.setStateImage();
            // 实现与BattleScene的交流
        } else {
            waitingForOperation = false;
            // 同上
            excuteAIOperation(); // ...
        }
    }

    private void endTurn() {
        if(activeParticipant != null) {
        	activeParticipant.setBattleState(BaseEntity.BattleState.AWAIT);
            activeParticipant.resetEventFire();
            activeParticipant.setActionConfig(null);
        }
        
        turnIndex ++;
        if(turnIndex == participants.size) {
        	turnIndex = 3;
        }
        startTurn();
    }

    private boolean isPlayer(BaseEntity participant) {
        // Array类的contains方法的boolean参数决定比较方式
        // 为true使用==运算符比较，为false使用存入对象的equals方法比较
        if (players.contains(participant, true)) {
            return true;
        }
        return false;
    }

    public boolean isWaitingForOperation() {
        return waitingForOperation;
    }

    private void excuteAIOperation() {
        BattleActionConfig config = BattleActionConfig.obtainConfigs(activeParticipant.getName())[0];
        //...
    }

    public BaseEntity getActiveParticipant() {
        return this.activeParticipant;
    }

    
}
