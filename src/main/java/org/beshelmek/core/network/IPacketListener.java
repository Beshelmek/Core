package org.beshelmek.core.network;


import org.beshelmek.core.api.network.packets.Packet;

public interface IPacketListener {
    void onPacketReceived(ServerInfo paramServerInfo, Packet paramPacket);

    void sessionClosed(ServerInfo paramServerInfo);

    void sessionOpened(ServerInfo paramServerInfo);

    boolean isValid(Packet paramPacket);
}
