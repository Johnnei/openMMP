package monopoly;

import java.util.ArrayList;
import java.util.List;

public class Turn
{

	private Player player;
	private Game game;

	private boolean rolledDice;
	boolean finished;
	List<GameEvent> events;

	public Turn(Game game, Player player)
	{
		this.game = game;
		this.player = player;
		rolledDice = false;
		finished = false;
		events = new ArrayList<GameEvent>();
	}

	public boolean myTurn(String user)
	{
		return (player.Username().equals(user)) ? true : false;
	}

	public void addEvent(GameEvent s)
	{
		events.add(s);
	}

	public GameEvent getEvent()
	{
		if (events.size() > 0)
		{
			return events.remove(0);
		}
		else
		{
			return GameEvent.none;
		}
	}

	public void rollDice()
	{
		if (rolledDice)
			return;
		rolledDice = true;
		//TODO: Send request to roll dices
	}

}
