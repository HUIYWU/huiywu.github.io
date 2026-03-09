package com.z.loa.data;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

public class ActionData {
    public ObjectMap<String, Player> players;
    public ObjectMap<String, Action> actions;
    
    public ActionData() {
        players = new ObjectMap<String, Player>();
        actions = new ObjectMap<String, Action>();
    }
    
    public static class Player{
        public String name;
        public String actionString;
        public Array<String> actionArray;
        
        public Player() {
            
        }
    }
    public static class Action {
        public String actionName;
        public String tips;
        public int effectTriggerIndex;
        public int stateIndex;
        public String aimType;
        public String actionType;
        public boolean specialPosition;
        public float[] position;
        public float[] size;
        public boolean flashFollow;
        public float flashDelayTime;
        public int flashCount;
        public Vector3 flashRgb;
        public int mpCost;
        
        public Action() {
            
        }
    }
}
