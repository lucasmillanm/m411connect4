package ch.bbw.m411.connect4;

import static ch.bbw.m411.connect4.Connect4ArenaMain.NOMOVE;
import static ch.bbw.m411.connect4.Connect4ArenaMain.isWinning;

public class GoodPlayer extends Connect4ArenaMain.DefaultPlayer {

  int bestMove = NOMOVE;
  int maxDepth;
  int minDepth;

  @Override
  int play() {
    minDepth = Math.min(countMovesAvailable(), maxDepth);
    getScore(myColor, minDepth, -10000, 10000);
    return bestMove;
  }

  public GoodPlayer(int depth) {
    super();
    maxDepth = depth;
  }

  int countMovesAvailable() {
    int moves = 0;
    for (Connect4ArenaMain.Stone stone : board) {
      if (stone == null) {
        moves++;
      }
    }
    return moves;
  }

  int getScore(Connect4ArenaMain.Stone currentPlayer, int depth, int alpha, int beta) {
    if (isWinning(board, currentPlayer.opponent())) {
      return -100;
    }
    if (depth == 0) {
      return evaluate(board, currentPlayer);
    }

    int max = alpha;
    var moves = getMoves();
    for (int i = moves.length - 1; i >= 0; i--) {
      var move = moves[i];
      board[move] = currentPlayer; // play to the position

      int currentValue = -getScore(currentPlayer.opponent(), depth - 1, -beta, -max);

      board[move] = null; // revert the last move
      if (depth == minDepth) {
        System.out.println(depth + "Index: " + move + " Value: " + currentValue + " p" + myColor);
      }

      if (currentValue > max) {
        max = currentValue;
        if (depth == minDepth) {
          bestMove = move; // return a position not a score
        }
        if (max >= beta) {
          break; // alpha beta
        }
      }
    }
    return max;
  }

  // gets possible moves
  int[] getMoves() {
    int[] moves = new int[7];
    int pointer = 0;
    for (int col = 0; col < 7; col++) { // iterates through
      for (int row = 0; row < 4; row++) {
        int i = col + row * 7;
        if (board[i] == null) {
          moves[pointer] = i;
          pointer++;
          break;
        }
      }
    }
    int[] moves2 = new int[pointer];
    for (int i = 0; i < moves2.length; i++) {
      moves2[i] = moves[i];
    }
    return moves2;
  }

  public static int evaluate(Connect4ArenaMain.Stone[] board, Connect4ArenaMain.Stone myColor) {
    var counter = 0;
    var counterValue = 0;

    for (Connect4ArenaMain.Stone stone : board) {
      counter++;
      if (stone == myColor) {

        //check low risk
        if (counter == 0 || counter == 7 || counter == 14 || counter == 21 || counter == 6 || counter == 13
            || counter == 20 || counter == 27) {
          counterValue += 3;
        }

        //check mid risk
        if (counter == 1 || counter == 8 || counter == 15 || counter == 22 || counter == 5 || counter == 12
            || counter == 19 || counter == 26) {
          counterValue += 4;
        }

        //check high risk
        if (counter == 2 || counter == 9 || counter == 16 || counter == 23 || counter == 4 || counter == 11
            || counter == 18 || counter == 25) {
          counterValue += 5;
        }

        //check no chance risk
        if (counter == 3 || counter == 10 || counter == 17 || counter == 24) {
          counterValue += 7;
        }
      }
    }
    return counterValue;
  }
}
