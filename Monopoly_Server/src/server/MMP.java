package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MMP
{
	
	public static void main(String[] args)
	{
		serverAcces = new MMP();
		getServer().Host();
	}
	
	private MMP()
	{
	}

	public void Host()
	{
		System.out.println("Launching Server...");
		try
		{
			System.out.print("Preparing OpenMMP Server...");
			game = new ServerGame(this);
			System.out.println("Done");
			
			System.out.print("Binding to port 27960... ");
			hostSocket = new ServerSocket(27960);
			System.out.println("Succeed");
			
			System.out.print("Launching Console Command Handler...");
			conComm = new ConsoleCommand(this);
			Thread t = new Thread(conComm);
			t.start();
			System.out.println("Succeed");
			
			playGame();
			
			System.out.print("Stopping Server...");
			hostSocket.close();
			System.out.println("Succeed");
		}
		catch(Exception e)
		{
			System.out.println("failed");
			e.printStackTrace();
		}
	}
	
	public void addPlayer(Socket player)
	{
	}
	
	public void startGame()
	{
		if(game.isPhase(1))
		{
			System.out.println("Stopping Player Acception");
			game.setPhase(2);
		}
	}
	
	private void playGame()
	{
		// Phase 1 - Waiting for players
		System.out.println("Phase 1 - Waiting for players...");
		ThreadPlayerAccept pAccepter = new ThreadPlayerAccept();
		while(game.isPhase(1))
		{
			try
			{
				Thread.sleep(100);
			}
			catch(InterruptedException e)
			{
			}
		}
		pAccepter.interrupt();
		System.out.println("Sharing Players...");
		// Send player data to all players!
		System.out.println("Starting Game...");
		// Phase 2 - Let's Play
		System.out.println("Game has ended...");
		try
		{
			hostSocket.close();
		} catch (IOException e)
		{
			System.out.println("Failed to close port!");
			e.printStackTrace();
		}
	}
	
	//Game
	private ServerGame game;
	
	//Server
	private ServerSocket hostSocket;
	private ConsoleCommand conComm;
	
	//Getter
	public ServerSocket getSocket() { return hostSocket; }
	
	//Public Server Acces
	private static MMP serverAcces;
	public static MMP getServer() { return serverAcces; }

}
