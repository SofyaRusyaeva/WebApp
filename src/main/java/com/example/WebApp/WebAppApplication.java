package com.example.WebApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


// TODO: logout(Черный список accessToken)
// TODO: настройка environment variables
// TODO: выводить стоимость корзины
// TODO: Проверка валидности токена
// TODO: Изменение пароля
// TODO: Обработка ошибок

// OrderItemController и OrderItemService не нужен

@SpringBootApplication
public class WebAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebAppApplication.class, args);
	}

}
