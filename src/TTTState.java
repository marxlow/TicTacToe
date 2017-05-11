import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class TTTState {
    
    public static final int EMPTY = 2;
    public static final int PLAYER_ONE_MOVE = 0;
    public static final int PLAYER_TWO_MOVE = 1;
    public static final String SEPARATOR = "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~";
    public static final String DRAW_GAME = "DRAW";
    
    private String game_mode;
    private String player_one;
    private String player_two;
    private String current_player;
    private String game_winner;
    private int[][] game_state = new int[3][3];
    private int move;
    private int move_counts = 0;
    private ArrayList<Integer> available_moves = new ArrayList<Integer>();
    private boolean hasLost = false;
    private Scanner sc = new Scanner(System.in);
    
    public TTTState(String game_mode, String player_one, String player_two) {
        this.game_mode = game_mode;
        this.player_one = player_one;
        this.player_two = player_two;
        initGameState();
    }
    
    public void makeMove(int turn) {
        initPlayer(turn); // get the current player
        printState();
        // Bot's turn to play
        if (game_mode.equals(TTTPlayer.SINGLE_PLAYER_MODE) && turn == PLAYER_TWO_MOVE) {
            getBotMove();
        } else {
        // Normal player
            getMove();
        }
        recordMove(turn);
        updateState();
    }
    
    public boolean hasLost() {
        return hasLost;
    }
    
    public String getWinner() {
        return game_winner;
    }
    
    // Returns a move by the bot in single player mode
    private void getBotMove() {
        int random_index;
        System.out.print(TTTPlayer.BOT_NAME + " is thinking...");
        // Waiting is to make game more life-like. Bot is playing too quickly
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // randomly choose a move out of available moves
        Random rn = new Random();
        random_index = rn.nextInt(available_moves.size());
        move = available_moves.get(random_index);
        System.out.println("Bot made move: " + move);
       
    }
    
    // prompts the player to make a move based on the current game state
    private void getMove() {
        System.out.print("Bot: " + current_player + "'s turn, please make a move between [0-8]: ");
        try {
            move = sc.nextInt();
            checkMove();
        } catch (Exception e) {
            // ILLEGAL VALUES given by player, repeat prompt
            System.out.println(TTTPlayer.ERROR_MESSAGE);
            System.out.print("Invalid move, please only try numbers in current state\n");        
            System.out.println(TTTPlayer.ERROR_MESSAGE + "\n");
            getMove();
        }
        
    }
    
    // record down the move of a player in the game state
    private void recordMove(int turn) {
        int row = move / 3;
        int col = move % 3;
        if (turn == PLAYER_ONE_MOVE) {
            game_state[row][col] = PLAYER_ONE_MOVE;
        } else {
            game_state[row][col] = PLAYER_TWO_MOVE;
        }
        return;
    }
    
   // Checks for end game
    private void updateState() {
        available_moves.remove(Integer.valueOf(move));
        move_counts ++;
        
        if (checkWinner() == PLAYER_ONE_MOVE) {
            game_winner = player_one;
            hasLost = true;
        } else if (checkWinner() == PLAYER_TWO_MOVE) {
            game_winner = player_two;
            hasLost = true;
        } else if (move_counts == 9) {
            hasLost= true;
            game_winner = DRAW_GAME;
        }
    }
    
    // Returns the index of the winner(if any)
    // Returns EMPTY(2) if there are still no winners
    private int checkWinner() {
       // Check for horizontal wins.
        for (int row = 0; row < 3; row ++) {
            if (game_state[row][0] == game_state[row][1] && game_state[row][0] == game_state[row][2]) {
                return game_state[row][0];
            }
        }
        // Check for vertical wins.
        for (int col = 0; col < 3; col++) {
            if (game_state[0][col] == game_state[1][col] && game_state[0][col] == game_state[2][col]) {
                return game_state[0][col];
            }
        }
        // Check for "\" wins.
        if (game_state[0][0] == game_state[1][1] && game_state[0][0] == game_state[2][2]) {
            return game_state[0][0];
        }
        // Check for "/" wins.
        if (game_state[0][2] == game_state[1][1] && game_state[0][2] == game_state[2][0]) {
            return game_state[0][2];
        }
        return EMPTY;
    }
    
    // Returns true if move is valid
    private void checkMove() throws Exception {
        int row = move / 3;
        int col = move % 3;
        
        if (game_state[row][col] != EMPTY) {
            throw new Exception();
        }
    }
    
    // Prints the current image of the TTT state
    private void printState() {
        StringBuilder sb = new StringBuilder();
        int position;
        
        sb.append("\n" + SEPARATOR + "\n\n");
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (game_state[row][col] == EMPTY) {
                    position = (row * 3) + col;
                    sb.append(" " + String.valueOf(position) + " ");
                } else if (game_state[row][col] == PLAYER_ONE_MOVE) {
                        sb.append(" O ");
                } else if (game_state[row][col] == PLAYER_TWO_MOVE) {
                        sb.append(" X ");
                }
                sb.append("|");
            }
            sb.append("\n------------\n");
        }
        
        System.out.println(sb.toString());
    }
    
    // initialize gameState to all be empty cells
    private void initGameState() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                game_state[row][col] = EMPTY;
            }
        }
        for (int i = 0; i < 9; i ++) {
            available_moves.add(i);
        }
    }
    // initialize who is the current player making a move
    private void initPlayer(int turn) {
        if (turn == PLAYER_ONE_MOVE) {
            this.current_player = player_one;
        } else {
            this.current_player = player_two;
        }
    }
}
