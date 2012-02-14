package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import server.packet.Packet11BuyStreet;
import server.packet.Packet12OwnerStreet;
import server.packet.Packet15SetIndex;

import monopoly.Street;

public class ConsoleCommand extends Thread
{
	public ConsoleCommand(MMP mmp)
	{
		this.mmp = mmp;
		setDaemon(false);
	}
	
	public void run()
	{
		String cmd = "";
		while(!cmd.equals("stop"))
		{
			try
			{
				cmd = in.readLine().toLowerCase();
				String cmdbase = cmd.split(" ")[0];
				if(cmd.equals("start"))
				{
					mmp.startGame();
				} else if(cmdbase.equals("givetown")) {
					String[] args = cmd.split(" ");
					if(args.length == 2) {
						if(!isInt(args[1])) {
							return;
						}
						mmp.Monopoly().getCurrentPlayer().addIndex((byte)Integer.parseInt(args[1]));
						new Packet11BuyStreet().handle();
						mmp.Monopoly().getCurrentPlayer().addIndex((byte)-Integer.parseInt(args[1]));
						System.out.println("Town has been given");
					}
				}
				
			} catch (IOException e)
			{
				System.err.println("CCError: " + e.getMessage());
			}
		}
	}
	
	private boolean isInt(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	private static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
	private MMP mmp;
}
