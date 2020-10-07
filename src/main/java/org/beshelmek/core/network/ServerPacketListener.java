package org.beshelmek.core.network;


import org.beshelmek.core.api.network.packets.Packet;

public class ServerPacketListener implements IPacketListener {
    @Override
    public void onPacketReceived(ServerInfo paramServerInfo, Packet paramPacket) {

    }

    @Override
    public void sessionClosed(ServerInfo paramServerInfo) {

    }

    @Override
    public void sessionOpened(ServerInfo paramServerInfo) {

    }

    @Override
    public boolean isValid(Packet paramPacket) {
        return true;
    }
}
