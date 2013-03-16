/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program.
 */
package ai.npc.Teleports.TeleportCube;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;

/**
 * Author: RobikBobik
 */
public class TeleportCube extends Quest
{
	private static final String qn = "TeleportCube";
	
	private final static int TELECUBE_S = 32107;
	
	public TeleportCube(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(TELECUBE_S);
		addTalkId(TELECUBE_S);
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = "";
		QuestState st = player.getQuestState(qn);
		int npcId = npc.getNpcId();
		
		if (npcId == TELECUBE_S)
		{
			player.teleToLocation(10468, -24569, -3650);
			return null;
		}
		st.exitQuest(true);
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new TeleportCube(-1, qn, "teleports");
	}
}