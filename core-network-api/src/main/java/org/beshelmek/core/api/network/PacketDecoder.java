package org.beshelmek.core.api.network;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;
import org.beshelmek.core.api.network.packets.Packet;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;

public class PacketDecoder extends CumulativeProtocolDecoder {
    @Override
    protected boolean doDecode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) {
        if (in.remaining() >= 32 && in.prefixedDataAvailable(4)){
            try {
                byte[] data = new byte[in.getInt()];
                in.get(data);
                ByteArrayInputStream bis = new ByteArrayInputStream(data);
                ObjectInputStream stream = new ObjectInputStream(new ByteArrayInputStream(data));

                Packet packet = PacketFactory.getPacket(stream.read());
                packet.setSync(stream.readLong());
                packet.readData(stream);
                stream.close();
                bis.close();
                out.write(packet);
                return true;
            } catch (Exception ignored) {}
        }

        return false;
    }
}
