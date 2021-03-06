package com.bergerkiller.bukkit.common.internal.network;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import com.bergerkiller.bukkit.common.protocol.PacketFields;
import com.bergerkiller.bukkit.common.utils.PacketUtil;

import net.minecraft.server.Connection;
import net.minecraft.server.Packet;

/**
 * Wraps around another packet to create an undetectable packet type to send to clients undetected
 */
class CommonSilentPacket extends Packet {

	static {
		PacketUtil.registerPacketToId(CommonSilentPacket.class, 0);
	}

	public final Packet packet;

	public CommonSilentPacket(Object packet) {
		this.packet = (Packet) packet;
	}

	/**
	 * This method is called NetworkManager.a(Packet packet, boolean flag) right before queuing.
	 * We expect this method to not be called before sending...if it is we have a BIG issue with compatibility here!
	 * 
	 * @return something, probably has to do with whether the packet can be ignored
	 */
	@Override
	public boolean e() {
		updatePacketId();
		return this.packet.e();
	}

	public void updatePacketId() {
		PacketFields.DEFAULT.packetID.transfer(packet, this);
	}
	
	@Override
	public int a() {
		return this.packet.a();
	}

	@Override
	public boolean a(Packet packet) {
		return this.packet.a(packet);
	}

	@Override
	public boolean a_() {
		return this.packet.a_();
	}

	@Override
	public String toString() {
		return this.packet.toString();
	}

	@Override
	public void a(DataInput in) throws IOException {
		throw new UnsupportedOperationException("Can not load a silent packet from a stream");
	}

	@Override
	public void a(DataOutput out) throws IOException {
		this.packet.a(out);
	}

	@Override
	public void handle(Connection arg0) {
		// Nothing happens here to avoid problems
	}
}
