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
package quests.Q00178_IconicTrinity;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.base.Race;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.model.quest.QuestState;
import com.l2jserver.gameserver.model.quest.State;

/**
 * Author: RobikBobik L2PS Team
 */
public class Q00178_IconicTrinity extends Quest
{
	private static final int KEKROPUS = 32138;
	private static final int ICONPAST = 32255;
	private static final int ICONPRESENT = 32256;
	private static final int ICONFUTURE = 32257;
	
	public Q00178_IconicTrinity(int questId, String name, String descr)
	{
		super(questId, name, descr);
		
		addStartNpc(KEKROPUS);
		addTalkId(KEKROPUS);
		addTalkId(ICONPAST);
		addTalkId(ICONPRESENT);
		addTalkId(ICONFUTURE);
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
		
		int passwrd = st.getInt("pass");
		
		if (event.equalsIgnoreCase("32138-03.htm"))
		{
			st.set("cond", "1");
			st.setState(State.STARTED);
			st.playSound("ItemSound.quest_accept");
		}
		else if (event.equalsIgnoreCase("32138-07.htm"))
		{
			st.giveItems(956, 1);
			st.addExpAndSp(20123, 976);
			st.playSound("ItemSound.quest_finish");
			st.exitQuest(false);
		}
		else if (event.equalsIgnoreCase("32255-03.htm"))
		{
			st.set("pass", "0");
		}
		else if (event.equalsIgnoreCase("32255-04a.htm"))
		{
			st.set("pass", String.valueOf(passwrd + 1));
			htmltext = "32255-04.htm";
		}
		else if (event.equalsIgnoreCase("32255-05a.htm"))
		{
			st.set("pass", String.valueOf(passwrd + 1));
			htmltext = "32255-05.htm";
		}
		else if (event.equalsIgnoreCase("32255-06a.htm"))
		{
			st.set("pass", String.valueOf(passwrd + 1));
			htmltext = "32255-06.htm";
		}
		else if (event.equalsIgnoreCase("32255-07a.htm"))
		{
			st.set("pass", String.valueOf(passwrd + 1));
			if (st.getInt("pass") != 4)
			{
				htmltext = "32255-07.htm";
			}
		}
		else if (event.equalsIgnoreCase("32255-12.htm"))
		{
			st.set("cond", "2");
			st.playSound("ItemSound.quest_middle");
			st.set("pass", "0");
		}
		else if (event.equalsIgnoreCase("32256-03.htm"))
		{
			st.set("pass", "0");
		}
		else if (event.equalsIgnoreCase("32256-04a.htm"))
		{
			st.set("pass", String.valueOf(passwrd + 1));
			htmltext = "32256-04.htm";
		}
		else if (event.equalsIgnoreCase("32256-05a.htm"))
		{
			st.set("pass", String.valueOf(passwrd + 1));
			htmltext = "32256-05.htm";
		}
		else if (event.equalsIgnoreCase("32256-06a.htm"))
		{
			st.set("pass", String.valueOf(passwrd + 1));
			htmltext = "32256-06.htm";
		}
		else if (event.equalsIgnoreCase("32256-07a.htm"))
		{
			st.set("pass", String.valueOf(passwrd + 1));
			if (st.getInt("pass") != 4)
			{
				htmltext = "32256-07.htm";
			}
		}
		else if (event.equalsIgnoreCase("32256-13.htm"))
		{
			st.set("cond", "3");
			st.playSound("ItemSound.quest_middle");
			st.set("pass", "0");
		}
		else if (event.equalsIgnoreCase("32257-03.htm"))
		{
			st.set("pass", "0");
		}
		else if (event.equalsIgnoreCase("32257-04a.htm"))
		{
			st.set("pass", String.valueOf(passwrd + 1));
			htmltext = "32257-04.htm";
		}
		else if (event.equalsIgnoreCase("32257-05a.htm"))
		{
			st.set("pass", String.valueOf(passwrd + 1));
			htmltext = "32257-05.htm";
		}
		else if (event.equalsIgnoreCase("32257-06a.htm"))
		{
			st.set("pass", String.valueOf(passwrd + 1));
			htmltext = "32257-06.htm";
		}
		else if (event.equalsIgnoreCase("32257-07a.htm"))
		{
			st.set("pass", String.valueOf(passwrd + 1));
			htmltext = "32257-07.htm";
		}
		else if (event.equalsIgnoreCase("32257-08a.htm"))
		{
			st.set("pass", String.valueOf(passwrd + 1));
			if (st.getInt("pass") != 5)
			{
				htmltext = "32257-08.htm";
			}
		}
		else if (event.equalsIgnoreCase("32257-11.htm"))
		{
			st.set("cond", "4");
			st.playSound("ItemSound.quest_middle");
			st.set("pass", "0");
		}
		return htmltext;
	}
	
	@Override
	public String onTalk(L2Npc npc, L2PcInstance player)
	{
		QuestState st = player.getQuestState(getName());
		String htmltext = getNoQuestMsg(player);
		if (st == null)
		{
			return htmltext;
		}
		
		int cond = st.getInt("cond");
		
		switch (st.getState())
		{
			case State.CREATED:
				if (player.getRace() != Race.Kamael)
				{
					htmltext = "32138-02.htm";
					st.exitQuest(true);
				}
				else if (player.getLevel() < 17)
				{
					htmltext = "32138-02a.htm";
					st.exitQuest(true);
				}
				else
				{
					htmltext = "32138-01.htm";
				}
				break;
			case State.STARTED:
				switch (npc.getNpcId())
				{
					case KEKROPUS:
						if (cond == 1)
						{
							htmltext = "32138-04.htm";
						}
						else if (cond == 4)
						{
							htmltext = "32138-05.htm";
						}
						break;
					case ICONPAST:
						if (cond == 1)
						{
							htmltext = "32255-01.htm";
						}
						else if (cond == 2)
						{
							htmltext = "32255-13.htm";
						}
						break;
					case ICONPRESENT:
						if (cond == 2)
						{
							htmltext = "32256-01.htm";
						}
						else if (cond == 3)
						{
							htmltext = "32256-14.htm";
						}
						break;
					case ICONFUTURE:
						if (cond == 3)
						{
							htmltext = "32257-01.htm";
						}
						else if (cond == 4)
						{
							htmltext = "32257-12.htm";
						}
						break;
				}
				break;
		}
		return htmltext;
	}
	
	public static void main(String[] args)
	{
		new Q00178_IconicTrinity(178, Q00178_IconicTrinity.class.getSimpleName(), "Iconic Trinity");
	}
}