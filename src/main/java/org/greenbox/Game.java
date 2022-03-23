package org.greenbox;

public class Game {
    enum GameState {
        X_PLAYING {
            @Override
            public String toString() {
                return "Turn: X";
            }
        },
        O_PLAYING {
            @Override
            public String toString() {
                return "Turn: O";
            }
        },
        X_WON {
            @Override
            public String toString() {
                return "X Won!";
            }
        },
        O_WON {
            @Override
            public String toString() {
                return "O Won!";
            }
        },
        DRAW {
            @Override
            public String toString() {
                return "Draw!";
            }
        }
    }
    private int size;
    private String[][] board;
    private GameState gameState;
    private int winCondition;
    private int turns;

    public Game() {
        this.size = 3;
        this.winCondition = 3;
        this.board = new String[size][size];
        initGame();
    }

    public void initGame() {
        initBoard();
        this.gameState = GameState.X_PLAYING;
        this.turns = 0;
    }

    public boolean isPlaying() {
        return gameState == GameState.X_PLAYING.X_PLAYING || gameState == GameState.O_PLAYING;
    }

    public boolean makeMove(int x, int y) {
        if (!this.isPlaying()) return false;
        if (setBoardElem(x, y)) {
            this.turns++;
            this.gameState = this.gameState == GameState.X_PLAYING ? GameState.O_PLAYING : GameState.X_PLAYING;
            checkWinner();
            return true;
        }
        return false;
    }

    public int getSize() {
        return size;
    }

    public String getGameState() {
        return this.gameState.toString();
    }

    public String getBoardElem(int x, int y) {
        return this.board[y][x];
    }

    private boolean setBoardElem(int x, int y) {
        String elem = this.board[y][x];
        if (!elem.isEmpty()) return false;
        if (this.gameState == GameState.X_PLAYING) {
            this.board[y][x] = "X";
        } else {
            this.board[y][x] = "O";
        }
        return true;
    }

    private void checkWinner() {
        // Check normal diagonal
        checkDiagonalLine();
        // Check reverse diagonal
        checkInvDiagonalLine();
        // Check horizontal
        checkHorizontalLine();
        // Check vertical
        checkVerticalLine();
        if (this.turns == size*size) this.gameState = GameState.DRAW;
    }

    private void checkVerticalLine() {
        String evaluatedPlayer = board[0][0];
        int sameCount = 1;
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (x == 0 && y == 0) continue;
                String currentPlayer = board[y][x];
                if (currentPlayer.isEmpty()) {
                    sameCount = 0;
                    continue;
                }
                if (evaluatedPlayer.equals(currentPlayer)) {
                    sameCount++;
                } else {
                    evaluatedPlayer = currentPlayer;
                    sameCount = 1;
                }
                if (sameCount == winCondition) {
                    this.gameState = evaluatedPlayer.equals("X") ? GameState.X_WON : GameState.O_WON;
                    return;
                }
            }
            sameCount = 0;
        }
    }

    private void checkHorizontalLine() {
        String evaluatedPlayer = board[0][0];
        int sameCount = 1;
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (y == 0 && x == 0) continue;
                String currentPlayer = board[y][x];
                if (currentPlayer.isEmpty()) {
                    sameCount = 0;
                    continue;
                }
                if (evaluatedPlayer.equals(currentPlayer)) {
                    sameCount++;
                } else {
                    evaluatedPlayer = currentPlayer;
                    sameCount = 1;
                }
                if (sameCount == winCondition) {
                    this.gameState = evaluatedPlayer.equals("X") ? GameState.X_WON : GameState.O_WON;
                    return;
                }
            }
            sameCount = 0;
        }
    }

    private void checkInvDiagonalLine() {
        String evaluatedPlayer = board[0][Math.floorMod(-1, size)];
        int sameCount = 1;
        for (int i = 1; i < size; i++) {
            String currentPlayer = board[i][Math.floorMod(-1-i, size)];
            if (currentPlayer.isEmpty()) {
                sameCount = 0;
                continue;
            }
            if (evaluatedPlayer.equals(currentPlayer)) {
                sameCount++;
            } else {
                evaluatedPlayer = currentPlayer;
                sameCount = 1;
            }
            if (sameCount == winCondition) {
                this.gameState = evaluatedPlayer.equals("X") ? GameState.X_WON : GameState.O_WON;
                return;
            }
        }
    }

    private void checkDiagonalLine() {
        String evaluatedPlayer = board[0][0];
        int sameCount = 1;
        for (int i = 1; i < size; i++) {
            String currentPlayer = board[i][i];
            if (currentPlayer.isEmpty()) {
                sameCount = 0;
                continue;
            }
            if (evaluatedPlayer.equals(currentPlayer)) {
                sameCount++;
            } else {
                evaluatedPlayer = currentPlayer;
                sameCount = 1;
            }
            if (sameCount == winCondition) {
                this.gameState = evaluatedPlayer.equals("X") ? GameState.X_WON : GameState.O_WON;
                return;
            }
        }
    }

    private void initBoard() {
        for (int row = 0; row < this.size; row++) {
            for (int col = 0; col < this.size; col++) {
                this.board[row][col]  = "";
            }
        }
    }
}
