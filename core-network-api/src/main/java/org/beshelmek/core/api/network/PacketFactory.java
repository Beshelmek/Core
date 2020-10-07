package org.beshelmek.core.api.network;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.beshelmek.core.api.network.packets.Packet;

import java.util.HashMap;
import java.util.Map;

public class PacketFactory implements ProtocolCodecFactory {
    public static Map<Integer, Class<Packet>> packetsByID = new HashMap<>();
    public static Map<Class<Packet>, Integer> idsByClass = new HashMap<>();

    private ProtocolEncoder encoder = new PacketEncoder();
    private ProtocolDecoder decoder = new PacketDecoder();

    static {

    }

    public static void registerPacket(Integer id, Class<Packet> packetClass){
        packetsByID.put(id, packetClass);
        idsByClass.put(packetClass, id);
    }

    @Override
    public ProtocolEncoder getEncoder(IoSession session) throws Exception {
        return this.encoder;
    }

    @Override
    public ProtocolDecoder getDecoder(IoSession session) throws Exception {
        return this.decoder;
    }

    public static Packet getPacket(int id) {
        try {
            return packetsByID.get(id).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getPacketId(Packet packet) {
        return idsByClass.get(packet.getClass());
    }
}
