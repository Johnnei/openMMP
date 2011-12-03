package server.game;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.InputStream;

public class ThreadDataReader extends Thread
{
	private BufferedInputStream inStream;
	
	public ThreadDataReader(InputStream socketIn)
	{
		setDaemon(true);
		inStream = new BufferedInputStream(new DataInputStream(socketIn));
	}
	
	public void run()
	{
		//TODO: Packet Reading
	}
}
