package monopoly;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

import multiplayer.PlayerMP;

public class Game
{
	PlayerMP socket;
	
	public static void Main(String[] args)
	{
		new Game();
	}
	
	public Game()
	{
		System.out.print("Username: ");
		String username = readInput();
		System.out.print("Color Code: 0x");
		String code = readInput();
		int colorCode = Integer.parseInt(code);
		System.out.print("Server IP: ");
		String ip = readInput();
		Log("Connecting to Server...");
		try
		{
			Socket pSocket = new Socket(ip, 27960);
			socket = new PlayerMP(username, colorCode, pSocket);
		} catch (Exception e)
		{
			Log("Failed!");
			e.printStackTrace();
		}
	}
	
	private static String readInput()
	{
		Scanner input = new Scanner(System.in);
		return input.nextLine();
	}
	
	public static void Log(String line)
	{
		System.out.println("[" + new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()) + "] " + line);
	}
}
