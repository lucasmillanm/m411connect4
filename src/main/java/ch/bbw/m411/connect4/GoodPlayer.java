package ch.bbw.m411.connect4;

import static ch.bbw.m411.connect4.Connect4ArenaMain.isWinning;

public class GoodPlayer extends Connect4ArenaMain.DefaultPlayer {

  @Override
  int play() {
    return 0;
  }

  int getScore(Connect4ArenaMain.Stone currentPlayer, int freeCount, int depth) {
    if (isWinning(board, currentPlayer.opponent())) {
      return -100;
    }
    if (freeCount == 0) {
      return 0;
    }
    if (depth == 0) {
      return evaluate(board, currentPlayer);
    }

    while (getMoves().length >= 1) {
    }
    return 0;
  }

  int[] getMoves() {
    int[] moves = new int[7];
    int pointer = 0;
    for (int col = 0; col < 7; col++) {
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
