package net.slashware.eid.data;

import java.util.Hashtable;

import net.slashware.eid.entity.EIDItem;

public class ItemFactory {
	private static Hashtable<String, EIDItem> definitions = new Hashtable<String, EIDItem>();
	public static void init(EIDItem[] definitions_){
		for (int i = 0; i < definitions_.length; i++)
			definitions.put(definitions_[i].getFullID(), definitions_[i]);
	}
	
	public static EIDItem createItem(String itemId){
		EIDItem ret = definitions.get(itemId);
		if (ret == null){
			//ExpeditionGame.crash("Item "+itemId+" not found");
			/*
			return null;*/
		}
		return (EIDItem) ret.clone();
	}


}
