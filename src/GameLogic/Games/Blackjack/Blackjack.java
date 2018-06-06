package GameLogic.Games.Blackjack;

import GameLogic.Games.StandardDecks;
import GameLogic.Generics.Game;

public class Blackjack extends Game {
    public Blackjack() {
        super(1, 7, 50000, new StandardDecks().getSixDeck(), new BlackjackRules());
    }
}