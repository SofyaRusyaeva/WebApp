package com.example.WebApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


// TODO: logout
// TODO: настройка environment variables
// TODO: выводить стоимость корзины
// TODO: починить login
// TODO: Проверка валидности токена
// TODO: Изменение пароля

// OrderItemController и OrderItemService не нужен

@SpringBootApplication
public class WebAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebAppApplication.class, args);
	}

}
