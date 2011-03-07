package net.slashware.eid.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.slashware.eid.entity.NPC;

public class NPCFactory {
	private static Map<String, NPC> npcsMap = new HashMap<String, NPC>();
	private static List<NPC> npcsList = new ArrayList<NPC>();
	
	public static NPC createNPC(String id){
		return (NPC)npcsMap.get(id).clone();
	}
	

	public static void setNPCs(NPC[] npcs) {
		for (NPC npc: npcs){
			npcsMap.put(npc.getTypeId(), npc);
			npcsList.add(npc);
		}
	}
}
