package protocolsupport.protocol.transformer.mcpe.packet.mcpe.serverbound;

import io.netty.buffer.ByteBuf;

import java.util.Collections;
import java.util.List;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.PacketDataSerializer;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.PEPacketIDs;
import protocolsupport.protocol.transformer.mcpe.packet.mcpe.ServerboundPEPacket;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockPlace;

public class UseItemPacket implements ServerboundPEPacket {

	protected int againstX;
	protected int againstY;
	protected int againstZ;
	protected int face;
	protected float cursorX;
	protected float cursorY;
	protected float cursorZ;
	protected float playerPosX;
	protected float playerPosY;
	protected float playerPosZ;
	protected ItemStack itemstack;

	@Override
	public int getId() {
		return PEPacketIDs.USE_ITEM_PACKET;
	}

	@Override
	public ServerboundPEPacket decode(ByteBuf buf) throws Exception {
		PacketDataSerializer serializer = new PacketDataSerializer(buf, ProtocolVersion.MINECRAFT_PE);
		this.againstX = buf.readInt();
		this.againstY = buf.readInt();
		this.againstZ = buf.readInt();
		this.face = buf.readByte() & 0xFF;
		this.cursorX = buf.readFloat();
		this.cursorY = buf.readFloat();
		this.cursorZ = buf.readFloat();
		this.playerPosX = buf.readFloat();
		this.playerPosY = buf.readFloat();
		this.playerPosZ = buf.readFloat();
		this.itemstack = serializer.readItemStack();
		return this;
	}

	@Override
	public List<? extends Packet<?>> transfrom() throws Exception {
		PacketPlayInBlockPlace place = new PacketPlayInBlockPlace(new BlockPosition(againstX, againstY, againstZ), face, itemstack, cursorX, cursorY, cursorZ);
		place.timestamp = System.currentTimeMillis();
		return Collections.singletonList(place);
	}

}