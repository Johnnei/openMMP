package server;

import server.game.PlayerMP;

public class ServerGame
{
	
	public ServerGame(MMP server)
	{
		players = new PlayerMP[6];
		mmpServer = server;
	}
	
	public PlayerMP[] getPlayers()
	{
		return players;
	}
	
	public PlayerMP getPlayer(int i)
	{
		if(players[i] == null)
			return null;
		return players[i];
	}
	
	public void setPhase(int i)
	{
		phase = (byte)i;
	}
	
	public boolean isPhase(int i)
	{
		return (byte)i == phase;
	}
	
	private byte phase;
	private PlayerMP[] players;
	private MMP mmpServer;
	
}
