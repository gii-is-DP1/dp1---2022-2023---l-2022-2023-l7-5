package org.springframework.samples.petclinic.game;

import static org.assertj.core.api.Assertions.assertThat;


import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.samples.petclinic.game.exception.NotThisTypeOfGame;
import org.springframework.samples.petclinic.game.exception.TooManyPlayers;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
public class GameServiceTests {
	
	@Autowired
	protected GameService gameService;
	
	@Autowired
	protected UserService userService;
	
	@Test
	@Transactional
	public void shouldInsertGame() {
		int found = this.gameService.getGames().size();
		
		Game game = new Game();
		game.setMode("survival");
		game.setFinished(true);
		game.setNumberOfPlayers(1);
		game.setDateOfCreation(LocalDate.now());
		game.setNumberCurrentPlayers(1);
		
		this.gameService.save(game);
		assertThat(game.getId()).isNotNull();
		assertThat(this.gameService.getGames().size()).isEqualTo(found+1);
	}
	
	@Test
	void shouldFindGameById() {
		Game game = new Game();
		game.setMode("survival");
		game.setFinished(true);
		game.setNumberOfPlayers(1);
		game.setNumberCurrentPlayers(1);
		game.setDateOfCreation(LocalDate.now());
		this.gameService.save(game);
		Integer id = game.getId();
		
		assertThat(this.gameService.getGameById(id)).isEqualTo(game);
	}
	
	@Test
	void shouldDeleteGameById() {
		int found = this.gameService.getGames().size();
		assertThat(found).isEqualTo(0);
		
		Game game = new Game();
		game.setMode("survival");
		game.setFinished(true);
		game.setNumberOfPlayers(1);
		game.setNumberCurrentPlayers(1);
		game.setDateOfCreation(LocalDate.now());
		this.gameService.save(game);
		Integer id = game.getId();
		
		assertThat(this.gameService.getGames().size()).isEqualTo(1);

		this.gameService.deleteGameById(id);
		assertThat(this.gameService.getGames().size()).isEqualTo(0);
	}
	
	@Test
	void shouldInitPlayerToGame() {
		
		User user = new User();
		user.setUsername("manuelEjemplo2");
		user.setEmail("manuel.ejemplo@gmail.com");
		user.setPassword("password");
		user.setEnabled(true);
		this.userService.saveUser(user);
		
		Game game = new Game();
		game.setMode("survival");
		game.setFinished(true);
		game.setNumberOfPlayers(4);
		game.setNumberCurrentPlayers(0);
		game.setDateOfCreation(LocalDate.now());
		this.gameService.save(game);
		
		String username = user.getUsername();
		this.gameService.initPlayerToGame(username, game);
		
		assertThat(game.getNumberCurrentPlayers()).isEqualTo(1);
	}
	
	@Test
	void shoulJoinPlayerToGame() {
		User user = new User();
		user.setUsername("manuelEjemplo2");
		user.setEmail("manuel.ejemplo@gmail.com");
		user.setPassword("password");
		user.setEnabled(true);
		this.userService.saveUser(user);
		String username1 = user.getUsername();
		
		User user2 = new User();
		user2.setUsername("manuelEjemplo3");
		user2.setEmail("manuel.ejemplo3@gmail.com");
		user2.setPassword("password");
		user2.setEnabled(true);
		this.userService.saveUser(user2);
		String username2 = user2.getUsername();
		
		Game game = new Game();
		game.setMode("COMPETITIVE");
		game.setFinished(true);
		game.setNumberOfPlayers(4);
		game.setNumberCurrentPlayers(0);
		game.setDateOfCreation(LocalDate.now());
		this.gameService.save(game);
		
		this.gameService.initPlayerToGame(username1, game);
		
		assertThat(game.getNumberCurrentPlayers()).isEqualTo(1);
		
		try {
			this.gameService.joinPlayerToGame(username2, game);
		} catch (TooManyPlayers | NotThisTypeOfGame e) {
			e.printStackTrace();
		}
		
		assertThat(game.getNumberCurrentPlayers()).isEqualTo(2);
		
	}
}