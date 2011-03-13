package net.slashware.eid.controller.level;

import net.slashie.serf.level.AbstractFeature;
import net.slashie.serf.level.AbstractLevel;
import net.slashie.serf.level.FeatureFactory;
import net.slashie.serf.levelGeneration.StaticGenerator;
import net.slashie.utils.Position;
import net.slashware.eid.data.ItemFactory;
import net.slashware.eid.data.NPCFactory;
import net.slashware.eid.entity.NPC;
import net.slashware.eid.entity.item.EIDItem;
import net.slashware.eid.entity.item.Weapon;
import net.slashware.eid.entity.player.DetectiveActor;

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
		}else if (cmds[1].equals("FEATURE")){
			AbstractFeature feature = FeatureFactory.getFactory().buildFeature(cmds[2]);
			feature.setPosition(where.x+x,where.y+y,where.z);
			l.addFeature(feature);
		}else if (cmds[1].equals("CRIMINAL_LEADER")){
			NPC npc = NPCFactory.createNPC(((DetectiveActor)l.getPlayer()).getCurrentMission().getCrime().getCriminal().getNPCID());
			npc.setPosition(where.x+x,where.y+y,where.z);
			l.addActor(npc);
		}else if (cmds[1].equals("CRIMINAL")){
			NPC npc = NPCFactory.createNPC("CRIMINAL");
			npc.setWeapon (Weapon.getWeaponByTier(((DetectiveActor)l.getPlayer()).getCurrentMission().getDifficulty()));
			npc.setPosition(where.x+x,where.y+y,where.z);
			l.addActor(npc);
		}else if (cmds[1].equals("WEAPON")){
			EIDItem item = Weapon.getWeaponByTier(((DetectiveActor)l.getPlayer()).getCurrentMission().getDifficulty());
			l.addItem(Position.add(where, new Position(x,y)), item);
		}
	}
}