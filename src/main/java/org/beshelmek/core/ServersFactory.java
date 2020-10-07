package org.beshelmek.core;

import org.apache.mina.core.session.IoSession;
import org.beshelmek.core.network.ServerInfo;

import java.util.Map;

public class ServersFactory {
    private static Map<IoSession, ServerInfo> serversMap;


    public static ServerInfo getServer(IoSession session) {
        return serversMap.get(session);
    }

    public static void removeServer(IoSession session) {
        serversMap.remove(session);
    }
}
