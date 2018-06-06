package GameLogic.Games;

import GameLogic.Generics.GameObjects.Card;
import GameLogic.Generics.GameObjects.Deck;
import GameLogic.Generics.Types.Suit;
import GameLogic.Generics.Types.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StandardDecks {
    private Deck standardDeck;
    private Deck sixDeck;

    public StandardDecks() {
        List<Card> cards = new ArrayList<>();
        List<Card> sixCards = new ArrayList<>();
        Random random = new Random();

        for (Suit s : Suit.values()) {
            for (Value v : Value.values()) {
                cards.add(new Card(s, v));
            }
        }

        for (int i = 0; i < 6; i++) {
            sixCards.addAll(cards);
        }

        sixDeck = new Deck(sixCards.subList(0, random.nextInt(16) + 60));
        standardDeck = new Deck(cards);
    }

    public Deck getSixDeck() {
        return sixDeck;
    }

    public Deck getStandardDeck() {
        return standardDeck;
    }
}