package GameLogic;

import GameLogic.Games.Blackjack.Blackjack;
import GameLogic.Generics.Exceptions.InvalidPlayerAmount;
import GameLogic.Generics.Game;

import java.util.Scanner;

public class GameTest {
    private static Scanner sc = new Scanner(System.in);
    private static Game game;

    private static void initPlayers() {
        System.out.println("How many Players");
        int n = sc.nextInt();
        try {
            game.start(n);
        } catch (InvalidPlayerAmount invalidPlayerAmount) {
            System.out.println(invalidPlayerAmount.getMessage());
            initPlayers();
        }
    }

    public static void main(String[] args) {
        System.out.println("What would you like to play?: ");
        String gm = sc.next();
        if (gm.toLowerCase().equals("blackjack")) {
            game = new Blackjack();
            initPlayers();
        }
    }
}