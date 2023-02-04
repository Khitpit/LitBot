package LitBot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Driver {

    public static void main(String[] args){
        setupGame();
    }

    public static void setupGame(){
        List<Player> players = getPlayers();
        dealCards(players);
    }

    public static List<Player> getPlayers(){
        Scanner input = new Scanner(System.in);
        int numOfPlayers = 0;

        System.out.print("Enter the number of players (including LitBot): ");
        try{
            numOfPlayers = input.nextInt();
        } catch (Exception e) {
            System.out.println("Please enter an even integer.");
            System.out.println(e);
            input.reset();
        }

        List<Player> players = new ArrayList<Player>(numOfPlayers);

        System.out.println("Enter the names of LitBot's teammates:");
        Player LitBot = new Player("LitBot", true);
        players.add(LitBot);
        for (int i = 0; i < (numOfPlayers/2) - 1; i++) {
            String name = input.next();
            Player temp = new Player(name, true);
            players.add(temp);
        }
        System.out.println("Enter the names of the opposing team:");
        for (int i = 0; i < numOfPlayers/2; i++){
            String name = input.next();
            Player temp = new Player(name, false);
            players.add(temp);
        }

        return players;
    }

    public static void dealCards(List<Player> players){
        List<Card> deck = new ArrayList<Card>(54);
        for (Suit s : Suit.values()) {
            if (s == Suit.JOKER){
                Card newCard = new Card(0, s);
                deck.add(newCard);
                newCard = new Card(1, s);
                deck.add(newCard);
            }else{
                for (int i = 1; i < 14; i++) {
                    Card newCard = new Card(i, s);
                    deck.add(newCard);
                }
            }
        }

        int handSize = deck.size()/(players.size());
        Random r = new Random();
        for (int i = 0; i < handSize; i++) {
            for (Player p : players) {
                int randomCardIndex = r.nextInt(deck.size());
                p.hand.add(deck.get(randomCardIndex));
                deck.remove(randomCardIndex);
            }
        }

        int playerNum = 0;
        while(deck.size() > 0){
            int randomCardIndex = r.nextInt(deck.size());
            players.get(playerNum).hand.add(deck.get(randomCardIndex));
            deck.remove(randomCardIndex);
            randomCardIndex = r.nextInt(deck.size());
            players.get(players.size() - playerNum - 1).hand.add(deck.get(randomCardIndex));
            deck.remove(randomCardIndex);
            playerNum++;
        }

    }
}