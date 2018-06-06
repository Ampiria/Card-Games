package GameLogic.Generics.GameObjects;

import GameLogic.Generics.Types.Suit;
import GameLogic.Generics.Types.Value;

import java.util.Scanner;

public class Card {
    private Suit suit;
    private Value value;

    public Card(Suit s, Value v) {
        suit = s;
        value = v;
    }

    public Suit getSuit() {
        return suit;
    }

    public Value getValue() {
        return value;
    }

    public String cardString() {
        return "(" + value.name() + " of " + suit.name() + ")";
    }

    public int numValue() {
        switch (value) {
            case Two:
                return 2;
            case Three:
                return 3;
            case Four:
                return 4;
            case Five:
                return 6;
            case Seven:
                return 7;
            case Eight:
                return 8;
            case Nine:
                return 9;
            case Ten:
                return 10;
            case Jack:
                return 10;
            case Queen:
                return 10;
            case King:
                return 10;
            case Ace:
                Scanner sc = new Scanner(System.in);
                System.out.println("Do you want the value to be 1 or 11?");
                int v = sc.nextInt();
                if (v != 11 && v != 1) {
                    System.out.println("Please use the provided choices");
                    numValue();
                } else {
                    return v;
                }
        }
        return 0;
    }
}