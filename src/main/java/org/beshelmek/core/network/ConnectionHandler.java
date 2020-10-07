package org.beshelmek.core.network;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.beshelmek.core.ServersFactory;
import org.beshelmek.core.api.network.packets.Packet;

public class ConnectionHandler extends IoHandlerAdapter {
    private PacketWrapper pw = new PacketWrapper();

    public void messageReceived(IoSession session, Object message) throws Exception {
        if (message instanceof Packet) {
            Packet packet = (Packet)message;
            this.pw.retrievePacket(ServersFactory.getServer(session), packet);
        }
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        ServerInfo server = ServersFactory.getServer(session);
        this.pw.sessionClosed(server);

        ServersFactory.removeServer(session);
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        cause.printStackTrace();
    }
}
