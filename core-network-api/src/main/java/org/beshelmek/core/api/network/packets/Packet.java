package org.beshelmek.core.api.network.packets;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public abstract class Packet {
    long sync;

    public abstract void writeData(ObjectOutputStream paramObjectOutputStream);

    public abstract void readData(ObjectInputStream paramObjectInputStream);

    int getSize() {
        return 0;
    }

    public boolean isSync() {
        return (this.sync != 0L);
    }

    public long getSync() {
        return this.sync;
    }

    public void setSync(long hash) {
        this.sync = hash;
    }
}
