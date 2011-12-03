package multiplayer;

import java.io.DataInputStream;
import java.util.List;

import multiplayer.packet.Packet;

public class ServerHandler extends Thread
{

	public ServerHandler()
	{
		setDaemon(true);
	}

	public void run()
	{
		while (true)
		{
			List<PlayerMP> clients = Connection.Get().clients;
			for (int i = 0; i < clients.size(); i++)
			{
				try
				{
					PlayerMP playerSocket = clients.get(i);
					DataInputStream input = new DataInputStream(playerSocket.socket.getInputStream());
					Packet p;
					while ((p = Packet.readPacket(input)) != null)
					{
						p.handle();
					}
					playerSocket.sendAllPackets();

				} catch (Exception e)
				{
					try
					{
						clients.get(i).socket.close();
					} catch (Exception ex)
					{
					}
				}
			}
		}
	}

}
