package multiplayer.packet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import monopoly.Game;
import monopoly.Player;

public class Packet01PlayerJoin extends Packet
{

	public Packet01PlayerJoin()
	{
	};

	public Packet01PlayerJoin(String username, int color, byte id)
	{
		user = username;
		colorCode = color;
		this.id = id;
	}

	public void readData(DataInputStream d) throws IOException
	{
		user = d.readUTF();
		colorCode = d.readInt();
	}

	public void writeData(DataOutputStream d) throws IOException
	{
		d.writeUTF(user);
		d.writeInt(colorCode);
	}

	public void handle()
	{
		/*Player p = new Player(user, colorCode);
		Player[] ps = Game.Monopoly().getPlayers();
		for (int i = 0; i < ps.length; i++)
		{
			if (ps[i] == null)
			{
				Game.Monopoly().setPlayer(i, p);
				return;
			}
		}*/
	}

	String user;
	int colorCode;
	byte id;
}
