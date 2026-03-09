package com.z.loa.manager;

import java.util.*;
import com.z.loa.data.*;
import com.badlogic.gdx.utils.*;

public class DialogNode {
	private Set<String> dialogueIdSet = new HashSet<>();
	public String jumpTo = "";

	public void recordDialogueId(String id) {
		dialogueIdSet.add(id);
	}

	public boolean checkConditions(Array<DialogueData.Condition> conditions) {
		if (conditions == null || conditions.isEmpty()) {
			return true;
		}
		for (DialogueData.Condition condition : conditions) {
			boolean met = checkCondition(condition);
			if (met) {
				jumpTo = condition.jump;
				return false;
			}
			recordDialogueId(condition.target);
		}
		return true;
	}

	private boolean checkCondition(DialogueData.Condition condition) {
		if (condition.type.equals("dialogue_visited")) {
			boolean visited = dialogueIdSet.contains(condition.target);
			return visited;
		}
		return condition.require;
	}
}

