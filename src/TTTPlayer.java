import java.util.Scanner;

public class TTTPlayer {
    public static final String WELCOME_MESSAGE = "Bot: Hi, welcome to TicTacToe!";
    public static final String MODE_MESSAGE = "Bot: Choose your mode! Single player(S), Multi-player(M)\nS/M: ";
    public static final String BEGIN_MESSAGE = "~~~~~~~~~~~~~~~~~~BEGIN~~~~~~~~~~~~~~~~~~";
    public static final String ERROR_MESSAGE = "~~~~~~~~~~~~~~~~~~ERROR~~~~~~~~~~~~~~~~~~";
    public static final String GAMEOVER_MESSAGE = "~~~~~~~~~~~~~~~~GAME OVER~~~~~~~~~~~~~~~~";
    public static final String MULTI_PLAYER_MODE = "M";
    public static final String SINGLE_PLAYER_MODE = "S";
    public static final String REPLAY_YES = "Y";
    public static final String REPLAY_NO = "N";

    private String player_one;
    private String player_two;
    private String game_mode;

    private int player_one_score = 0;
    private int player_two_score = 0;

    public static void main(String[] args) {
        TTTPlayer player = new TTTPlayer();
        player.run();
    }

    // Runs the TTT game
    private void run() {
        System.out.println(WELCOME_MESSAGE);
        configureGameMode();
        startGame();
    }

    private void startGame() {
        // Generate randomly who goes first.
        int turn = 0;
        TTTState state = new TTTState(game_mode, player_one, player_two);
        while (!state.hasLost()) {
            // Continue playing
            state.makeMove(turn);
            turn++;
            turn = turn % 2;

        }
        System.out.println(GAMEOVER_MESSAGE);
        System.out.println("Winner: " + state.getWinner());
        printScore(state.getWinner());

        // Check if the user wants to replay.
        if (replay()) {
            startGame();
        }
    }

    private void configureGameMode() {
        Scanner sc = new Scanner(System.in);
        String mode;
        System.out.print(MODE_MESSAGE);
        mode = sc.nextLine().trim().toUpperCase();

        if (mode.equals(MULTI_PLAYER_MODE)) {
            game_mode = MULTI_PLAYER_MODE;
            System.out.println("Bot: You have selected Multi-player mode");
            System.out.print("Bot: Enter Player One name: ");
            player_one = sc.nextLine();
            System.out.print("Bot: Enter Player Two name: ");
            player_two = sc.nextLine();

        } else if (mode.equals(SINGLE_PLAYER_MODE)) {
            game_mode = SINGLE_PLAYER_MODE;
            System.out.println("Bot: You have selected Single player mode");
            System.out.print("Bot: Enter Player name: ");
            player_one = sc.nextLine();
            player_two = "Bot";

        } else {
            System.out.println(ERROR_MESSAGE);
            System.out.print("Invalid game mode, please try 'S' or 'M'\n");
            System.out.println(ERROR_MESSAGE + "\n");
            configureGameMode();
        }

        // System.out.println(player_one + " VERSUS " + player_two);
        System.out.println(BEGIN_MESSAGE);
    }

    // Should only be called after a game has ended.
    // Prints the total score for each player.
    private void printScore(String winner) {
        if (winner.equals(player_one)) {
            player_one_score++;
        } else if (winner.equals(player_two)) {
            player_two_score++;
        }
        System.out.println(player_one + " score: " + player_one_score);
        System.out.println(player_two + " score: " + player_two_score);
    }

    // Returns true if player wants to replay again.
    private boolean replay() {
        String answer;
        Scanner sc = new Scanner(System.in);

        System.out.print("Bot: Type 'y' or 'Y' to play again: ");
        answer = sc.nextLine().trim().toUpperCase();
        if (answer.equals(REPLAY_YES)) {
            return true;
        }
        return false;
    }

}
