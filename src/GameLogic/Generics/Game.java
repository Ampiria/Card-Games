package GameLogic.Generics;

import GameLogic.Generics.Exceptions.InvalidPlayerAmount;
import GameLogic.Generics.GameObjects.Deck;
import GameLogic.Generics.GameObjects.Player;
import GameLogic.Generics.Interfaces.Rules;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private int minPlayers, maxPlayers, wallet;
    private Deck deck;
    private Rules rules;
    private List<Player> players;

    public Game(int min, int max, int wallet, Deck deck, Rules rules) {
        minPlayers = min;
        maxPlayers = max;
        this.deck = deck;
        this.wallet = wallet;
        this.rules = rules;
        players = new ArrayList<>();
    }

    public void start(int p) throws InvalidPlayerAmount {
        if (p < minPlayers) {
            throw new InvalidPlayerAmount("Too Few Players");
        } else if (p > maxPlayers) {
            throw new InvalidPlayerAmount("Too Many Players");
        }
        for (int i = 0; i < p; i++) {
            players.add(new Player(wallet));
        }
        rules.initialize(players, deck);
        play();
    }


    private void play() {
        while (!rules.gameOver(players)) {
            rules.round(players, deck);
        }
    }
}