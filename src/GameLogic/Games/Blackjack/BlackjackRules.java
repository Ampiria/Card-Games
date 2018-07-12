package GameLogic.Games.Blackjack;

import GameLogic.Generics.GameObjects.Card;
import GameLogic.Generics.GameObjects.Deck;
import GameLogic.Generics.GameObjects.Player;
import GameLogic.Generics.Interfaces.Rules;
import GameLogic.Generics.Types.Order;
import GameLogic.Generics.Types.Value;

import java.util.*;

public class BlackjackRules implements Rules {
    private final int INITIAL_DEAL = 2;
    private final int SUBSEQUENT_DEAL = 1;
    private Player dealer;
    private Scanner sc;
    private boolean allBusted;

    BlackjackRules() {
        dealer = new Player(0);
        sc = new Scanner(System.in);
        allBusted = false;
    }

    @Override
    public void dealToPlayer(Player p, Deck deck, int x) {
        p.deal(deck.draw(x));
    }

    @Override
    public int scoreHand(List<Card> h) {
        int score = 0;
        for (Card c : h) {
            score += c.numValue();
        }
        return score;
    }

    private void bet(Player player) {
        System.out.println("How much do you want to bet?: ");
        int amount = sc.nextInt();
        player.bet(amount);
    }

    public int dealerScore(List<Card> hand) {
        int score = 0;
        List<Card> aces = new ArrayList<>();
        for (Card c : hand) {
            if (c.getValue() == Value.Ace) {
                aces.add(c);
            } else {
                score += c.numValue();
            }
        }
        for (Card c : aces) {
            if (21 - score < 11) {
                score += 1;
            } else if (21 - score == 11) {
                score += (aces.indexOf(c) == aces.size() - 1) ? 11 : 1;
            } else {
                score += 11;
            }
        }
        return score;
    }

    private Optional dealerTurn(Deck deck) {
        Optional result = Optional.empty();
        int score = dealerScore(dealer.getHand());
        while (!allBusted && score < 17) {
            dealToPlayer(dealer, deck, SUBSEQUENT_DEAL);
            score = dealerScore(dealer.getHand());
        }
        if (score <= 21) result = Optional.of(dealerScore(dealer.getHand()));
        System.out.println("Dealer's Hand: " + dealer.handString(dealer.getHand()));
        System.out.println("Dealer's Score: " + score);
        return result;
    }

    private void evaluateRound(Player player) {
        if (handCompare(player.getHand(), dealer.getHand()).equals(Order.GREATER) ||
                (scoreHand(player.getHand()) == 21 && dealerScore(dealer.getHand()) != 21)) {
            player.reward((player.getBet() * 3) / 2);
        }else{
            player.lostRound();
        }
        System.out.println("Your Score: " + scoreHand(player.getHand()));
    }

    private void dealToAll(List<Player> players, Deck deck) {
        for (Player player : players) {
            if (!player.isBusted()) {
                dealToPlayer(player, deck, INITIAL_DEAL);
            }
        }
        dealToPlayer(dealer, deck, INITIAL_DEAL);
    }

    private void clearAll(List<Player> players) {
        for (Player player : players) {
            player.clearHand();
        }
        dealer.clearHand();
    }

    @Override
    public void round(List<Player> players, Deck deck) {
        for (Player p : players) {
            if (!p.isBusted()) {
                takeTurn(p, deck);
            }
        }
        allBusted = true;
        for (Player player : players) {
            if (!player.isBusted()) allBusted = false;
        }
        if (dealerTurn(deck).equals(Optional.empty())) {
            dealer.clearHand();
        }
        for (Player player : players) {
            evaluateRound(player);
        }
        clearAll(players);
        dealToAll(players, deck);
        deck.shuffle();
    }

    @Override
    public void initialize(List<Player> players, Deck deck) {
        dealToAll(players, deck);
    }

    private Optional<Integer> actOnDecision(Player player, Deck deck, String decision, int score) {
        decision = decision.toLowerCase();
        switch (decision) {
            case "hit":
                dealToPlayer(player, deck, SUBSEQUENT_DEAL);
                System.out.println("Hand: " + player.handString(player.getHand()));
                System.out.println("Score: " + scoreHand(player.getHand()));
                System.out.println("Dealers Visible Card: " + dealer.getHand().get(0).cardString());
                return askPlayer(player, deck);
            case "stay":
                return Optional.of(score);
        }
        return Optional.empty();
    }

    private Optional<Integer> splitHand(List<Card> hand, Deck deck) {
        Player split1 = new Player(0);
        Player split2 = new Player(0);
        List<Player> splits = Arrays.asList(split1, split2);
        int start = 0;
        for(Player split : splits){
            split.deal(hand.subList(start,start + 1));
            split.deal(deck.draw(1));
            start++;
        }
        Optional<Integer> firstHand = askPlayer(split1, deck);
        Optional<Integer> secondHand = askPlayer(split2, deck);
        if (!firstHand.isPresent() || (secondHand.isPresent() && (firstHand.get() < secondHand.get()))) {
            return secondHand;
        }
        return firstHand;
    }

    private Optional<Integer> askPlayer(Player player, Deck deck) {
        int score = scoreHand(player.getHand());
        if (score > 21) {
            return Optional.empty();
        }
        List<Card> hand = player.getHand();
        if(hand.size() == 2 && hand.get(0).getValue() == hand.get(1).getValue()){
            System.out.println("Would you like to split your hand?");
            boolean split = (sc.next().equals("yes"));
            if(split){
                player.bet(2 * player.getBet());
                return splitHand(hand, deck);
            }
        }
        System.out.println("Would you like to hit or stay?");
        String decision = sc.next();
        return actOnDecision(player, deck, decision, score);
    }

    @Override
    public void takeTurn(Player player, Deck deck) {
        System.out.println("Wallet: " + player.getWallet());
        System.out.println("Hand: " + player.handString(player.getHand()));
        System.out.println("Score: " + scoreHand(player.getHand()));
        System.out.println("Dealers Visible Card: " + dealer.getHand().get(0).cardString());
        bet(player);
        Optional result = askPlayer(player, deck);
        if (!result.isPresent()) {
            player.clearHand();
        }
    }

    @Override
    public boolean gameOver(List<Player> players) {
        for (Player player : players) {
            if (player.getWallet() > 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Order cardCompare(Card c1, Card c2) {
        return Order.GREATER;
    }

    @Override
    public Order handCompare(List<Card> h1, List<Card> h2) {
        int h1score = scoreHand(h1);
        int h2score = scoreHand(h2);
        if (h1score > h2score) return Order.GREATER;
        else if (h1score < h2score) return Order.LESS;
        return Order.EQUAL;
    }
}