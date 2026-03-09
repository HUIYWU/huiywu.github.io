package com.z.loa.data;

import com.badlogic.gdx.utils.*;

public class DialogueData {
	public ObjectMap<String, Character> characters;
	public ObjectMap<String, Dialogue> dialogues;
	
	public DialogueData() {
		characters = new ObjectMap<>();
		dialogues = new ObjectMap<>();
	}
	public static class Character {
		public String name;
		public Array<String> avatar;
		
		public Character() {
			
		}
	}
	
	public static class Dialogue {
		public String character;
		public int avatarIndex;
		public String mood;
		public String layoutType;
		public String text;
		public Array<String> pages;
		public boolean first;
		public String next;
		public Array<Option> options;
		public Array<Condition> conditions;
		
		public Dialogue() {
			
		}
		
		public Array<String> getPages() {
			if (pages != null && pages.size > 0) {
				return pages;
			} else  {
				Array<String> single_page = new Array<>();
				single_page.add(text);
				return single_page;
			}
		}
	}
	
	public static class Option {
		public String next;
		
		public Option() {
			
		}
	}
	
	public static class Condition {
		public String type;
		public String target;
		public boolean require;
		public String jump;
		
		public Condition() {
			
		}
	}

}
