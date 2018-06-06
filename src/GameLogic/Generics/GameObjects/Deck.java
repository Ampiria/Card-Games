package GameLogic.Generics.GameObjects;

import java.util.List;
import java.util.Random;

public class Deck {
    private List<Card> cards;
    private Random random = new Random();
    private int ind = 0;

    public Deck(List<Card> cards) {
        this.cards = cards;
        shuffle();
    }

    private void swap(int a, int b) {
        Card temp = cards.get(a);
        this.cards.set(a, cards.get(b));
        this.cards.set(b, temp);
    }

    public void shuffle() {
        int deckSize = cards.size() - 1;
        for (int i = deckSize; i > 0; i--) {
            int j = random.nextInt(i);
            swap(i, j);
        }
    }

    public List<Card> draw(int x) {
        if (ind + x >= cards.size()) {
            ind = 0;
            shuffle();
        }
        int start = ind;
        int end = ind + x;
        ind += x;
        return cards.subList(start, end);
    }
}