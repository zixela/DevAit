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
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package quests.Q00269_InventionAmbition;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.util.Rnd;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00269_InventionAmbition extends Quest
{
	public final int INVENTOR_MARU = 32486;
	public final int[] MOBS =
	{
		21124,
		21125,
		21126,
		21127,
		21128,
		21129,
		21130,
		21131,
	};
	
	public final int ENERGY_ORES = 10866;
	
	public Q00269_InventionAmbition(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(INVENTOR_MARU);
		addTalkId(INVENTOR_MARU);
		
		for (int mob : MOBS)
		{
			addKillId(mob);
		}
		
		questItemIds = new int[]
		{
			ENERGY_ORES
		};
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return null;
		}
		
		if (event.equals("32486-03.htm"))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equals("32486-05.htm"))
		{
			st.exitQuest(true);
			st.playSound("ItemSound.quest_finish");
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = Quest.getNoQuestMsg(player);
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		switch (st.getState())
		{
			case State.CREATED:
				if (player.getLevel() < 18)
				{
					htmltext = "32486-00.htm";
					st.exitQuest(true);
				}
				else
				{
					htmltext = "32486-01.htm";
				}
				break;
			case State.STARTED:
				long count = st.getQuestItemsCount(ENERGY_ORES);
				if (count > 0)
				{
					st.giveItems(57, (count * 50) + (2044 * (count / 20)));
					st.takeItems(ENERGY_ORES, -1);
					htmltext = "32486-07.htm";
				}
				else
				{
					htmltext = "32486-04.htm";
				}
				break;
		}
		return htmltext;
	}
	
	@Override
	public final String onKill(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return null;
		}
		
		if (st.getInt("cond") == 1)
		{
			if (Rnd.chance(60))
			{
				st.giveItems(ENERGY_ORES, 1);
				st.playSound("ItemSound.quest_itemget");
			}
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q00269_InventionAmbition(269, Q00269_InventionAmbition.class.getSimpleName(), "Invention Ambition");
	}
}