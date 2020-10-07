package org.beshelmek.core.api.network;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;
import org.beshelmek.core.api.network.packets.Packet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class PacketEncoder extends ProtocolEncoderAdapter {
    @Override
    public void encode(IoSession session, Object message, ProtocolEncoderOutput out) throws Exception {
        Packet packet = (Packet) message;
        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ObjectOutputStream oss = new ObjectOutputStream(os);
            oss.write(PacketFactory.getPacketId(packet));
            oss.writeLong(packet.getSync());
            packet.writeData(oss);
            if (os.size() < 28) {
                int r = 28 - os.size();
                for (int i = 0; i < r; i++)
                    oss.writeByte(0);
            }
            oss.flush();
            byte[] data = os.toByteArray();
            oss.reset();
            oss.close();
            os.close();
            IoBuffer buffer = IoBuffer.allocate(48);
            buffer.setAutoExpand(true);
            buffer.putInt(data.length);
            buffer.put(data);
            buffer.flip();
            out.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
