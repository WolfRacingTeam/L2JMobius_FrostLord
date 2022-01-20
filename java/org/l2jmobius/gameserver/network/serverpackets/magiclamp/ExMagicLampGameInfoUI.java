/*
 * This file is part of the L2J Mobius project.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.l2jmobius.gameserver.network.serverpackets.magiclamp;

import java.util.List;

import org.l2jmobius.Config;
import org.l2jmobius.commons.network.PacketWriter;
import org.l2jmobius.gameserver.data.xml.MagicLampData;
import org.l2jmobius.gameserver.enums.LampMode;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.holders.GreaterMagicLampHolder;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author L2CCCP
 */
public class ExMagicLampGameInfoUI implements IClientOutgoingPacket
{
	private final PlayerInstance _player;
	private final byte _mode;
	private final int _count;
	
	public ExMagicLampGameInfoUI(PlayerInstance player, byte mode, int count)
	{
		_player = player;
		_mode = mode;
		_count = count;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_MAGICLAMP_GAME_INFO.writeId(packet);
		packet.writeD(Config.MAGIC_LAMP_MAX_GAME_COUNT); // nMagicLampGameMaxCCount
		packet.writeD(_count); // cMagicLampGameCCount
		switch (LampMode.getByMode(_mode))
		{
			case NORMAL:
			{
				packet.writeD(Config.MAGIC_LAMP_REWARD_COUNT);// cMagicLampCountPerGame
				break;
			}
			case GREATER:
			{
				packet.writeD(Config.MAGIC_LAMP_GREATER_REWARD_COUNT); // cMagicLampCountPerGame
				break;
			}
		}
		packet.writeD(_player.getLampCount()); // cMagicLampCount
		packet.writeC(_mode); // cGameMode
		final List<GreaterMagicLampHolder> greater = MagicLampData.getInstance().getGreaterLamps();
		packet.writeD(greater.size()); // costItemList
		greater.forEach(lamp ->
		{
			packet.writeD(lamp.getItemId()); // nItemClassID
			packet.writeQ(lamp.getCount()); // nItemAmountPerGame
			packet.writeQ(_player.getInventory().getInventoryItemCount(lamp.getItemId(), -1)); // nItemAmount
		});
		return true;
	}
}