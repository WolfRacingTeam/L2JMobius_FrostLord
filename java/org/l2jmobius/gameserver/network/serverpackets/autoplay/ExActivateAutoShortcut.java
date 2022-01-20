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
package org.l2jmobius.gameserver.network.serverpackets.autoplay;

import org.l2jmobius.commons.network.PacketWriter;
import org.l2jmobius.gameserver.model.ShortCuts;
import org.l2jmobius.gameserver.model.Shortcut;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author Mobius
 */
public class ExActivateAutoShortcut implements IClientOutgoingPacket
{
	private final int _position;
	private final boolean _active;
	
	public ExActivateAutoShortcut(Shortcut shortcut, boolean active)
	{
		_position = shortcut.getSlot() + (shortcut.getPage() * ShortCuts.MAX_SHORTCUTS_PER_BAR);
		_active = active;
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_ACTIVATE_AUTO_SHORTCUT.writeId(packet);
		packet.writeH(_position);
		packet.writeC(_active ? 0x01 : 0x00);
		return true;
	}
}
