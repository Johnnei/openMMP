package server.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

public abstract class Packet
{

	static HashMap<Class, Integer> classToID = new HashMap<Class, Integer>();
	static HashMap<Integer, Class> IDToClass = new HashMap<Integer, Class>();

	public Packet()
	{
	}

	public abstract void readData(DataInputStream d) throws IOException;

	public abstract void writeData(DataOutputStream d) throws IOException;

	public abstract void handle();

	public final int getPacketID()
	{
		return classToID.get(getClass());
	}

	public static void sendPacket(Packet p, DataOutputStream d) throws IOException
	{
		d.writeShort(p.getPacketID());
		p.writeData(d);
	}

	public static Packet readPacket(DataInputStream d) throws Exception
	{
		if (d.available() <= 0)
			return null;
		Packet p = (Packet) IDToClass.get(d.readShort()).newInstance();
		p.readData(d);
		return p;
	}

	public static void registerClass(int id, Class c)
	{
		if (IDToClass.get(id) != null)
		{
			return;
		}
		classToID.put(c, id);
		IDToClass.put(id, c);
	}

	static
	{
		registerClass(0, Packet00SetTurn.class);
		registerClass(1, Packet01PlayerJoin.class);
		registerClass(2, Packet02GiveID.class);
	}

}
