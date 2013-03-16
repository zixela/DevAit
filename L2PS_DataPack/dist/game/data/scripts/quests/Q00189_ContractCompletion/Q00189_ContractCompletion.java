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
package quests.Q00189_ContractCompletion;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Author: RobikBobik L2PS Team
 */
public final class Q00189_ContractCompletion extends Quest
{
	private static int Kusto = 30512;
	private static int Lorain = 30673;
	private static int Luka = 31437;
	private static int Shegfield = 30068;
	
	private static int Metal = 10370;
	
	public Q00189_ContractCompletion(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addTalkId(Kusto);
		addTalkId(Lorain);
		addTalkId(Luka);
		addTalkId(Shegfield);
		addFirstTalkId(Luka);
		
		questItemIds = new int[]
		{
			Metal
		};
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		String htmltext = event;
		
		QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		if (event.equalsIgnoreCase("31437-02.htm"))
		{
			st.set("cond", "1");
			st.playSound("ItemSound.quest_accept");
			st.giveItems(Metal, 1);
		}
		else if (event.equalsIgnoreCase("30673-02.htm"))
		{
			st.playSound("ItemSound.quest_middle");
			st.set("cond", "2");
			st.takeItems(Metal, -1);
		}
		else if (event.equalsIgnoreCase("30068-03.htm"))
		{
			st.set("cond", "3");
			st.playSound("ItemSound.quest_middle");
		}
		else if (event.equalsIgnoreCase("30512-02.htm"))
		{
			if (player.getLevel() < 50)
			{
				st.addExpAndSp(309467, 20614);
			}
			st.giveItems(57, 121527);
			st.playSound("ItemSound.quest_finish");
			st.exitQuest(false);
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		String htmltext = getNoQuestMsg(player);
		final QuestState st = player.getQuestState(getName());
		if (st == null)
		{
			return htmltext;
		}
		
		int npcId = npc.getNpcId();
		int cond = st.getInt("cond");
		
		switch (st.getState())
		{
			case State.STARTED:
				if (npcId == Luka)
				{
					if (cond == 0)
					{
						if (player.getLevel() < 42)
						{
							htmltext = "31437-00.htm";
						}
						else
						{
							htmltext = "31437-01.htm";
						}
					}
					else if (cond == 1)
					{
						htmltext = "31437-03.htm";
					}
				}
				else if (npcId == Lorain)
				{
					if (cond == 1)
					{
						htmltext = "30673-01.htm";
					}
					else if (cond == 2)
					{
						htmltext = "30673-03.htm";
					}
					else if (cond == 3)
					{
						htmltext = "30673-04.htm";
						st.set("cond", "4");
						st.playSound("ItemSound.quest_middle");
					}
					else if (cond == 4)
					{
						htmltext = "30673-05.htm";
					}
				}
				else if (npcId == Shegfield)
				{
					if (cond == 2)
					{
						htmltext = "30068-01.htm";
					}
					else if (cond == 3)
					{
						htmltext = "30068-04.htm";
					}
				}
				else if (npcId == Kusto)
				{
					if (cond == 4)
					{
						htmltext = "30512-01.htm";
					}
				}
				break;
			case State.COMPLETED:
				htmltext = getAlreadyCompletedMsg(player);
				break;
		}
		return htmltext;
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		QuestState qs = player.getQuestState("Q00186_ContractExecution");
		
		if ((npc.getNpcId() == Luka) && (qs != null) && qs.isCompleted() && (st == null))
		{
			st = newQuestState(player);
			st.setState(State.STARTED);
		}
		else
		{
			npc.showChatWindow(player);
		}
		return "";
	}
	
	public static void main(String[] args)
	{
		new Q00189_ContractCompletion(189, Q00189_ContractCompletion.class.getSimpleName(), "Contract Completion");
	}
}