package edu.uky.ai.chess.ex;

import java.io.BufferedWriter;
import java.io.FileWriter;

import edu.uky.ai.chess.Agent;
import edu.uky.ai.chess.Main;
import edu.uky.ai.chess.Settings;
import edu.uky.ai.chess.agent.BeginnerAgent;
import edu.uky.ai.chess.agent.GreedyAgent;
//import edu.uky.ai.chess.agent.HumanAgent;
import edu.uky.ai.chess.agent.IntermediateAgent;
import edu.uky.ai.chess.agent.NoviceAgent;
import edu.uky.ai.chess.agent.RandomAgent;


/**
 * An UNOFFICIAL way to test an agent from inside an IDE like Eclipse.
 * 
 * @author Stephen G. Ware
 */
public class Test {

	/**
	 * The agents who will play in the tournament. You can comment some out if
	 * you only want to test against certain ones.
	 */
	public static final Agent[] AGENTS = new Agent[] {
		//new HumanAgent(),
		new Mhfr225Agent(),
		new RandomAgent(),
		new GreedyAgent(),
		new NoviceAgent(),
		new BeginnerAgent(),
		new IntermediateAgent(),
	};
	
	/**
	 * Play a tournament where each ordered pair of agents plays 1 game (in
	 * other words, each pair of agent plays two games: one where the first is
	 * white and one where the second is white).
	 * 
	 * @param args ignored
	 * @throws Exception if an uncaught exception is thrown
	 */
	public static void main(String[] args) throws Exception {
		try(BufferedWriter output = new BufferedWriter(new FileWriter("results.html"))) {
			Main.play(AGENTS, 1, Settings.MOVE_LIMIT, Settings.TIME_LIMIT, output);
		}
	}
}
