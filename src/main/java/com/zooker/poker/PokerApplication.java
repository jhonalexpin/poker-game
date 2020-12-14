package com.zooker.poker;

import com.zooker.poker.service.CardsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PokerApplication implements CommandLineRunner {

	@Autowired
	CardsService cardsService;

	public static void main(String[] args) {
		SpringApplication.run(PokerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		if (args != null && args.length > 0) {
			int numberOfPlayers = 0;
			try {
				numberOfPlayers = Integer.parseInt(args[0]);
			} catch (NumberFormatException ne) {
				System.out.println("Please, insert a valid number of players");
				System.exit(1);
			}

			if (numberOfPlayers > 10) {
				System.out.println("You exceed the max number of players, the max number of players in the game is 10");
				System.exit(1);
			}

			cardsService.deliverCards(numberOfPlayers);
			cardsService.checkPlayerHand();


		} else {
			System.out.println("Please, insert a number of players");
			System.exit(1);
		}
	}
}
