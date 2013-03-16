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
package quests.Q00286_FabulousFeathers;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;
import com.l2jserver.util.Rnd;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00286_FabulousFeathers extends Quest
{
	private static int ERINU = 32164;
	private static final int[] MOBS =
	{
		22251,
		22253,
		22254,
		22255,
		22256
	};
	
	private static int FEATHER = 9746;
	
	public Q00286_FabulousFeathers(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(ERINU);
		addTalkId(ERINU);
		
		for (int mob : MOBS)
		{
			addKillId(mob);
		}
		
		questItemIds = new int[]
		{
			FEATHER
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
		
		if (event.equalsIgnoreCase("32164-03.htm"))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("32164-06.htm"))
		{
			st.takeItems(FEATHER, -1);
			st.giveItems(57, 4160);
			st.playSound("ItemSound.quest_finish");
			st.unset("cond");
			st.exitQuest(true);
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
		
		int cond = st.getInt("cond");
		
		switch (st.getState())
		{
			case State.CREATED:
				if (player.getLevel() >= 17)
				{
					htmltext = "32164-01.htm";
				}
				else
				{
					htmltext = "32164-02.htm";
					st.exitQuest(true);
				}
				break;
			case State.STARTED:
				if (cond == 1)
				{
					htmltext = "32164-04.htm";
				}
				else if (cond == 2)
				{
					htmltext = "32164-05.htm";
				}
				break;
		}
		return htmltext;
	}
	
	@Override
	public final String onKill(L2Npc npc, L2PcInstance player, boolean isSummon)
	{
		final QuestState st = player.getQuestState(getName());
		if ((st == null) || !st.isStarted())
		{
			return null;
		}
		
		if (Rnd.chance(70))
		{
			st.giveItems(FEATHER, 1);
			st.playSound("ItemSound.quest_itemget");
			if (st.getQuestItemsCount(FEATHER) == 80)
			{
				st.set("cond", "2");
				st.playSound("ItemSound.quest_middle");
			}
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		new Q00286_FabulousFeathers(286, Q00286_FabulousFeathers.class.getSimpleName(), "Fabulous Feathers");
	}
}