package org.beshelmek.core.network;

import org.apache.mina.core.session.IoSession;

import java.net.SocketAddress;

public class ServerInfo {
    private IoSession session;
    private SocketAddress socketAddress;

    public ServerInfo(IoSession session) {
        this.session = session;
        this.socketAddress = session.getRemoteAddress();
    }
}
