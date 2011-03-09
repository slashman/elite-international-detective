package net.slashware.eid.controller;

import net.slashie.serf.level.AbstractLevel;
import net.slashie.serf.levelGeneration.StaticGenerator;
import net.slashie.utils.Position;
import net.slashware.eid.data.ItemFactory;
import net.slashware.eid.data.NPCFactory;
import net.slashware.eid.entity.NPC;
import net.slashware.eid.entity.item.EIDItem;

public class EIDStaticGenerator extends StaticGenerator{
	@Override
	public void handleSpecialRenderCommand(AbstractLevel l, Position where, String[] cmds, int x, int y) {
		if (cmds[1].equals("ITEM")){
			EIDItem item = ItemFactory.createItem(cmds[2]);
			l.addItem(Position.add(where, new Position(x,y)), item);
		} else if (cmds[1].equals("NPC")){
			NPC npc = NPCFactory.createNPC(cmds[2]);
			npc.setPosition(where.x+x,where.y+y,where.z);
			l.addActor(npc);
		}
	}
}