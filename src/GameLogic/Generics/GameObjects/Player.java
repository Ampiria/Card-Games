package GameLogic.Generics.GameObjects;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private List<Card> hand;
    private double wallet;
    private int bet;
    private StringBuffer sb;
    private boolean busted;

    public Player(int wallet) {
        this.wallet = wallet;
        hand = new ArrayList<>();
        busted = false;
    }

    public List<Card> getHand() {
        return hand;
    }

    public double getWallet() {
        return wallet;
    }

    public void bet(int x) {
        bet = x;
    }

    public String handString(List<Card> hand) {
        sb = new StringBuffer();
        for (Card c : hand) {
            sb.append(c.cardString()).append(",");
        }
        if (sb.length() == 0) return "";
        return sb.substring(0, sb.length() - 1);
    }

    public int getBet() {
        return bet;
    }

    public void deal(List<Card> cards) {
        hand.addAll(cards);
    }

    public void clearHand() {
        hand.clear();
    }

    public void reward(int winnings) {
        wallet += winnings;
    }

    public boolean isBusted() {
        return busted;
    }

    public void lostRound(){
        wallet -= bet;
        bet = 0;
    }

    public void setBusted(boolean busted) {
        this.busted = busted;
    }
}