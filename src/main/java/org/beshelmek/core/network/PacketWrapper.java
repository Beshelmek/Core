package org.beshelmek.core.network;

import org.beshelmek.core.api.network.packets.Packet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PacketWrapper {
    ExecutorService service = Executors.newFixedThreadPool(8);

    private List<IPacketListener> listeners = new ArrayList<>();

    public PacketWrapper() {
        listeners.add(new ServerPacketListener());
    }

    public void addListener(IPacketListener listener) {
        this.listeners.add(listener);
    }

    public IPacketListener getListener(Packet packet) {
        for (IPacketListener listener : this.listeners) {
            if (listener.isValid(packet))
                return listener;
        }
        return null;
    }

    public void retrievePacket(final ServerInfo server, final Packet packet) {
        this.service.execute(() -> {
            IPacketListener listener = PacketWrapper.this.getListener(packet);
            if (listener != null)
            try {
                listener.onPacketReceived(server, packet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void sessionOpened(ServerInfo server) {
        for (IPacketListener listener : this.listeners)
            listener.sessionOpened(server);
    }

    public void sessionClosed(ServerInfo server) {
        for (IPacketListener listener : this.listeners)
            listener.sessionClosed(server);
    }
}
