package ch.zhaw.akguelar.food;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point für die Food-Service-Applikation.
 * Konfiguriert automatisch Spring Boot und startet den Embedded-Server.
 */
@SpringBootApplication
public class FoodApplication {

	/**
	 * Startet die Spring Boot Anwendung.
	 *
	 * @param args Kommandozeilenargumente (z.B. Profile, Port)
	 */
	public static void main(String[] args) {
		SpringApplication.run(FoodApplication.class, args);
	}

}
