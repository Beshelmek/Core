package org.beshelmek.core.api.network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.UUID;

public class PacketUtils {
    public static void writeUUID(ObjectOutputStream stream, UUID uuid) {
        try {
            stream.writeLong(uuid.getMostSignificantBits());
            stream.writeLong(uuid.getLeastSignificantBits());
        } catch (IOException iOException) {}
    }

    public static UUID readUUID(ObjectInputStream stream) {
        try {
            return new UUID(stream.readLong(), stream.readLong());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void writeObject(ObjectOutputStream stream, Object o) {
        try {
            if (o instanceof Integer) {
                stream.write(0);
                stream.writeInt((Integer) o);
            } else if (o instanceof Long) {
                stream.write(1);
                stream.writeLong((Long) o);
            } else if (o instanceof Double) {
                stream.write(2);
                stream.writeDouble((Double) o);
            } else if (o instanceof Float) {
                stream.write(3);
                stream.writeFloat((Float) o);
            } else if (o instanceof Boolean) {
                stream.write(4);
                stream.writeBoolean((Boolean) o);
            } else if (o instanceof String && getUTFLength((String)o) <= 65535L) {
                stream.write(5);
                stream.writeUTF((String)o);
            } else {
                stream.write(6);
                stream.writeObject(o);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Object readObject(ObjectInputStream stream) {
        try {
            int type = stream.read();
            switch (type) {
                case 0:
                    return stream.readInt();
                case 1:
                    return stream.readLong();
                case 2:
                    return stream.readDouble();
                case 3:
                    return stream.readFloat();
                case 4:
                    return stream.readBoolean();
                case 5:
                    return stream.readUTF();
            }
            return stream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static final char[] cbuf = new char[256];

    public static final long getUTFLength(String paramString) {
        int i = paramString.length();
        long l = 0L;
        int j = 0;
        while (j < i) {
            int k = Math.min(i - j, 256);
            paramString.getChars(j, j + k, cbuf, 0);
            for (int m = 0; m < k; m++) {
                int n = cbuf[m];
                if (n >= 1 && n <= 127) {
                    l++;
                } else if (n > 2047) {
                    l += 3L;
                } else {
                    l += 2L;
                }
            }
            j += k;
        }
        return l;
    }
}
