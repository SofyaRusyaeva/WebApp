package com.example.WebApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


// TODO: Заказы
// TODO: Страница пользователя

// TODO: Автоматически обновлять токен при протухании

// TODO: Добавить картинки к товарам
// TODO: Множественный выбор в каталоге

@SpringBootApplication
@EnableScheduling
public class WebAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebAppApplication.class, args);
	}

}
