package GameLogic.Generics.Interfaces;

import GameLogic.Generics.GameObjects.Card;
import GameLogic.Generics.GameObjects.Deck;
import GameLogic.Generics.GameObjects.Player;
import GameLogic.Generics.Types.Order;

import java.util.List;

public interface Rules {
    void dealToPlayer(Player p, Deck deck, int x);

    int scoreHand(List<Card> h);

    void round(List<Player> players, Deck deck);

    void initialize(List<Player> players, Deck deck);

    void takeTurn(Player player, Deck deck);

    boolean gameOver(List<Player> players);

    Order cardCompare(Card c1, Card c2);

    Order handCompare(List<Card> h1, List<Card> h2);
}