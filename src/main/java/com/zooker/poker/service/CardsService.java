package com.zooker.poker.service;

import com.zooker.poker.enums.Rank;
import com.zooker.poker.enums.Suit;
import com.zooker.poker.model.Card;
import com.zooker.poker.model.Player;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CardsService {

    private final List<Card> deck;
    private final List<Player> players;
    private static final int NUMBER_OF_CARDS = 5;

    private CardsService() {
        this.deck = new ArrayList<>();
        this.players = new ArrayList<>();
        initDeck();
    }

    private void initDeck() {
        for (Suit suit :
                Suit.values()) {
            for (Rank rank :
                    Rank.values()) {
                Card card = new Card();
                card.setKindCard(suit);
                card.setValueCard(rank);
                this.deck.add(card);
            }
        }

    }

    public List<Card> getDeck() {
        return deck;
    }

    public void deliverCards(int numberOfPlayers) {
        Random random = new Random();
        for (int i = 1; i <= numberOfPlayers; i++) {
            Player player = new Player("Player " + i);

            for (int j = 0; j < NUMBER_OF_CARDS; j++) {
                int indexPositionDeck = random.nextInt(deck.size() - 1);
                Card cardFromDeck = this.deck.get(indexPositionDeck);
                player.getCards().add(cardFromDeck);
                addingCardsToHand(player, cardFromDeck);
                this.deck.remove(indexPositionDeck);
            }
            this.players.add(player);
        }
    }

    private void addingCardsToHand(Player player, Card cardFromDeck) {
        TreeMap<Rank, List<Card>> rankedHand = player.getHand();
        Rank rankingCard = cardFromDeck.getValueCard();
        List<Card> playerHand;
        if (rankedHand.containsKey(rankingCard)) {
            playerHand = rankedHand.get(cardFromDeck.getValueCard());
        } else {
            playerHand = new ArrayList<>();
        }
        playerHand.add(cardFromDeck);
        rankedHand.put(rankingCard, playerHand);
    }

    public void checkPlayerHand() {
        players.forEach(player -> {
            System.out.println(player.getPlayerName());
            System.out.println(Arrays.toString(player.getCards().stream().sorted().toArray()));
            List<Card> cards = player.getCards();
            TreeMap<Rank, List<Card>> hand = player.getHand();
            List<Rank> pairsInHand = handWithPairs(hand);
            boolean handWithStraight = handWithStraight(hand);
            boolean handWithFlush = handWithFlush(cards);
            boolean handWithThreeOfAKind = handWithThreeOfAKind(hand);
            if (handWithStraight && handWithFlush) {
                System.out.println(player.getPlayerName() + " with Straight flush!" );
            } else if (handWithPoker(hand)) {
                System.out.println(player.getPlayerName() + " with Poker!" );
            } else if (handWithThreeOfAKind && pairsInHand.size() == 1) {
                System.out.println(player.getPlayerName() + " with Full House!" );
            } else if (handWithFlush) {
                System.out.println(player.getPlayerName() + " with Flush!" );
            } else if (handWithStraight) {
                System.out.println(player.getPlayerName() + " with Straight!" );
            } else if (handWithThreeOfAKind) {
                System.out.println(player.getPlayerName() + " with Three of a Kind!" );
            } else if (pairsInHand.size() == 2) {
                System.out.print(player.getPlayerName() + " with two Pairs of ");
                pairsInHand.forEach(rank -> System.out.print(rank + " "));
                System.out.print("!");
                System.out.println();
            } else if (pairsInHand.size() == 1) {
                System.out.println(player.getPlayerName() + " with Pair of " + pairsInHand.get(0) + "!" );
            } else {
                System.out.println(player.getPlayerName() + " with Highest card: " + handHighestCard(cards) );
            }
        });
    }

    private boolean handWithPoker(TreeMap<Rank, List<Card>> playerHand) {
        for (Map.Entry<Rank, List<Card>> rank :
                playerHand.entrySet()) {
            if (rank.getValue().size() == 4) {
                return true;
            }
        }
        return false;
    }

    private boolean handWithThreeOfAKind(TreeMap<Rank, List<Card>> playerHand) {

        for (Map.Entry<Rank, List<Card>> rank :
                playerHand.entrySet()) {
            if (rank.getValue().size() == 3) {
                return true;
            }
        }
        return false;
    }

    private List<Rank> handWithPairs(TreeMap<Rank, List<Card>> playerHand) {
        List<Rank> pairs = new ArrayList<>();
        for (Map.Entry<Rank, List<Card>> rank :
                playerHand.entrySet()) {
            if (rank.getValue().size() == 2) {
                pairs.add(rank.getKey());
            }
        }
        return pairs;
    }

    private boolean handWithStraight(TreeMap<Rank, List<Card>> playerHand) {
        List<Rank> ranksHand = new ArrayList<>(playerHand.keySet());
        Rank lowRank = ranksHand.stream().sorted().findFirst().get();
        Rank[] ranks = Rank.values();
        Arrays.sort(ranks);
        List<Rank> ranksList = Arrays.asList(ranks);
        int indexLowRank = ranksList.indexOf(lowRank);
        List<Rank> straightList = ranksList.subList(indexLowRank, indexLowRank + 4);

        return ranksHand.equals(straightList);
    }

    private boolean handWithFlush(List<Card> playerCards) {
        long countSuits = playerCards.stream()
                .map(Card::getKindCard).distinct().count();
        return countSuits == 1;
    }

    private Rank handHighestCard(List<Card> playerCards) {
        return playerCards.stream().map(Card::getValueCard)
                .max(Enum::compareTo).get();
    }

}
