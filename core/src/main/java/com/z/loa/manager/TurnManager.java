package com.z.loa.manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
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
import java.util.function.Function;

public class TurnManager {
    private Array<BaseEntity> participants;
    private Array<BaseEntity> players;
    private BattleScene scene;
    private BattleActionManager actionManager;
    private EffectManager effectManager;
    private int turnIndex;
    //private int independentVariable;
    private BaseEntity activeParticipant;
    private boolean waitingForOperation;
    private boolean eventRound = false;//特效事件控制，处理单配置多特效引起的多个结束事件

    public TurnManager() {}

    public TurnManager(Array<BaseEntity> participants, Array<BaseEntity> players, BattleScene scene) {
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
        EventListener listener = new EventListener() {
            @Override
            public boolean handle(Event event) {
                if (event.getTarget() != activeParticipant) {
                    return false;
                }
                
                if (event instanceof EffectFinishEvent) {
                    if (eventRound) {
                    	scene.recoverLowerPart();
                        scene.clearTwinText();
                        scene.enableDialog(true);
                        for (BaseEntity aim : actionManager.getAims()) {
                        	aim.setBattleState(BaseEntity.BattleState.AWAIT);
                        }
                        eventRound = false;
                        endTurn();
                    }
                    return true;
                } else if (event instanceof EffectTriggerEvent) {
                    scene.preprocess((TextButton) scene.getButtonTable().getChild(0));
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
                    eventRound = true;
                    return true;
                }
                return false;
            }
        };
        for (BaseEntity participant : participants) {
            participant.addListener(listener);
        }
    }

    public void startBattle() {
        //independentVariable = 0;
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
        activeParticipant = participants.get(turnIndex);
        if (isPlayer(activeParticipant)) {
            waitingForOperation = true;
            scene.enablePlayerControl(activeParticipant);
        } else {
            waitingForOperation = false;
            excuteAIOperation();
        }
        scene.setStateImage();
    }

    private void endTurn() {
        if(activeParticipant != null) {
        	activeParticipant.setBattleState(BaseEntity.BattleState.AWAIT);
            activeParticipant.resetEventFire();
            activeParticipant.setActionConfig(null);
        }
        //independentVariable ++;
        //turnIndex = (independentVariable + 3) % 6;
        turnIndex ++;
        if (turnIndex == participants.size) {
        	turnIndex = 0;
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

    private void excuteAIOperation() {
        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                BattleActionConfig[] configs = BattleActionConfig.obtainConfigs(activeParticipant.getName());
                BattleActionConfig config = configs[MathUtils.random(0, configs.length - 1)];
                actionManager.selectAim(config, players);
                scene.enableConfig(activeParticipant, config, BaseEntity.BattleState.SKILL, true);
            }
            
        }, 0.3f);
        //...
    }

    public BaseEntity getActiveParticipant() {
        return this.activeParticipant;
    }
    
    public boolean isEventRound() {
    	return eventRound;
    }

    
}
