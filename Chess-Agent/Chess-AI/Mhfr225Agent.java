package edu.uky.ai.chess.ex;
import java.util.List; 
import java.util.ArrayList;
import java.util.Random;

import edu.uky.ai.SearchBudgetExceededException;
import edu.uky.ai.chess.Agent;
import edu.uky.ai.chess.state.State;

import edu.uky.ai.chess.state.Outcome;
import edu.uky.ai.chess.state.Player;
import edu.uky.ai.chess.state.Piece;

import edu.uky.ai.chess.state.Bishop;
import edu.uky.ai.chess.state.King;
import edu.uky.ai.chess.state.Knight;
import edu.uky.ai.chess.state.Pawn;
import edu.uky.ai.chess.state.Queen;
import edu.uky.ai.chess.state.Rook;




/**
 * This agent chooses a next move randomly from among the possible legal moves.
 * 
 * @author Thea Francis
 */
public class Mhfr225Agent extends Agent {

	private static final int MAX_DEPTH = 3; // Define MAX_DEPTH
	/** A random number generator */
	private final Random random = new Random(0);
	

	public Mhfr225Agent() {
		super("mhfr225");
	}


    @Override
    protected State chooseMove(State current) {
        // Start the Minimax algorithm and return the best move
        try {
            return minimax(current);
        } catch (SearchBudgetExceededException e) {
            e.printStackTrace();
            // In case of search budget exceeded, fall back to random move
            return getRandomMove(current);
        }
    }
    
    private State minimax(State current) throws SearchBudgetExceededException {
        State bestState = null;
        double bestValue = Double.NEGATIVE_INFINITY;
        
        for (State child : current.next()) {
            double value = minValue(child, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, 1);
            if (value > bestValue) {
                bestValue = value;
                bestState = child;
            }
        }
        return bestState;
    }

    private boolean isTerminal(State state) {
        return state.outcome != null;
    }
    
    private double maxValue(State state, double alpha, double beta, int depth) throws SearchBudgetExceededException {
        if (depth == MAX_DEPTH || isTerminal(state)) {
            return evaluate(state);
        }
        double value = Double.NEGATIVE_INFINITY;
        for (State child : state.next()) {
            value = Math.max(value, minValue(child, alpha, beta, depth + 1));
            if (value >= beta) {
                return value;
            }
            alpha = Math.max(alpha, value);
        }
        return value;
    }

    private double minValue(State state, double alpha, double beta, int depth) throws SearchBudgetExceededException {
        if (depth == MAX_DEPTH || isTerminal(state)) {
            return evaluate(state);
        }
        double value = Double.POSITIVE_INFINITY;
        for (State child : state.next()) {
            value = Math.min(value, maxValue(child, alpha, beta, depth + 1));
            if (value <= alpha) {
                return value;
            }
            beta = Math.min(beta, value);
        }
        return value;
    }
    
    /*    private double evaluate(State state) {
        // Implement your evaluation logic here
        // For simplicity, let's return a random value
        return random.nextDouble();
    }
    */
    
    
    private double evaluate(State state) {
        if (state.outcome == Outcome.WHITE_WINS && state.player == Player.WHITE) {
            return Double.POSITIVE_INFINITY;
        } else if (state.outcome == Outcome.BLACK_WINS && state.player == Player.BLACK) {
            return Double.NEGATIVE_INFINITY;
        } else if (state.outcome == Outcome.DRAW) {
            return 0;
        }
        
        double materialScore = materialBalance(state);
        //double positionScore = positionBalance(state);
       // double mobilityScore = mobilityBalance(state);
        // double pawnStructureScore = pawnStructureBalance(state);
        // double kingSafetyScore = kingSafetyBalance(state);

        return materialScore; // plus other heuristic positionScore + pawnStructureScore + kingSafetyScore  + mobilityScore
    }

    private double materialBalance(State state) {
        double score = 0.0;
        for (Piece piece : state.board) {
            if (piece.player == Player.WHITE) {
                score += getPieceValue(piece);
            } else {
                score -= getPieceValue(piece);
            }
        }
        return score;
    }

    private double getPieceValue(Piece piece) {
        if (piece instanceof Pawn) {
            return 1.0;
        } else if (piece instanceof Knight) {
            return 3.0;
        } else if (piece instanceof Bishop) {
            return 3.0;
        } else if (piece instanceof Rook) {
            return 5.0;
        } else if (piece instanceof Queen) {
            return 9.0;
        } else if (piece instanceof King) {
            return 1000.0; 
        } else {
            return 0.0; 
        }
    }
    
    
    private State getRandomMove(State current) {
        List<State> children = new ArrayList<>();
        for (State child : current.next()) {
            children.add(child);
        }
        return children.get(random.nextInt(children.size()));
    }
}