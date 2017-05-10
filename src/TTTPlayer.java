import java.util.Scanner;

public class TTTPlayer {
    public static final String WELCOME_MESSAGE = "Bot: Hi, welcome to TicTacToe!";
    public static final String MODE_MESSAGE = "Bot: Choose your mode! Single player(S), Multi-player(M)\nS/M: ";
    public static final String BEGIN_MESSAGE = "~~~~~~~BEGIN~~~~~~~";
    public static final String ERROR_MESSAGE = "~~~~~~~ERROR~~~~~~~";
    public static final String MULTI_PLAYER_MODE = "M";
    public static final String SINGLE_PLAYER_MODE = "S";
    
    String player_one_name;
    String player_two_name;
    String game_mode;
    
    public static void main(String[] args) {
        TTTPlayer player= new TTTPlayer();
        player.run();
    }
    // Runs the TTT game
    private void run() {
        System.out.println(WELCOME_MESSAGE);
        // Check if single player or multiplayer mode.
        configureGameMode();
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
            player_one_name = sc.nextLine();
            System.out.print("Bot: Enter Player Two name: ");
            player_two_name = sc.nextLine();
            System.out.print("Bot: " + player_one_name + " VERSUS " + player_two_name);
            
        } else if (mode.equals(SINGLE_PLAYER_MODE)) {
            game_mode = SINGLE_PLAYER_MODE;
            System.out.println("Bot: You have selected Single player mode");
            System.out.print("Bot: Enter Player name: ");
            player_one_name = sc.nextLine();
        } else {
            System.out.println(ERROR_MESSAGE);
            System.out.print("Invalid game mode, please try 'S' or 'M'\n");
            System.out.println(ERROR_MESSAGE + "\n");
            configureGameMode();
        }
        System.out.println(BEGIN_MESSAGE);
    }
}
