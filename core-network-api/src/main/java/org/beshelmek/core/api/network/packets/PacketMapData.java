package org.beshelmek.core.api.network.packets;


import org.beshelmek.core.api.network.PacketUtils;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PacketMapData extends Packet {
    Map<String, Object> data = new HashMap<>();

    public Map<String, Object> getData() {
        return (this.data == null) ? Collections.EMPTY_MAP : this.data;
    }

    public void put(String key, Object value) {
        this.data.put(key, value);
    }

    public void putAll(Map<String, Object> map) {
        this.data.putAll(map);
    }

    public void writeData(ObjectOutputStream out) {
        try {
            out.writeInt(this.data.size());
            for (Map.Entry<String, Object> e : this.data.entrySet()) {
                out.writeUTF(e.getKey());
                PacketUtils.writeObject(out, (e.getValue() == null) ? "" : e.getValue());
            }
        } catch (Exception exception) {}
    }

    public void readData(ObjectInputStream in) {
        try {
            int size = in.readInt();
            this.data = new HashMap<>();
            for (int i = 0; i < size; i++)
                this.data.put(in.readUTF(), PacketUtils.readObject(in));
        } catch (Exception exception) {}
    }

    public String toString() {
        return String.valueOf(getClass().getName()) + ((this.data == null) ? "" : (" => " + this.data.toString()));
    }
}