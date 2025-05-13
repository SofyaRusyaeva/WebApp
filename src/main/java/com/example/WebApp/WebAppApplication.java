package com.example.WebApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


// TODO: Корзина
// TODO: Заказы
// TODO: Страница пользователя

// TODO: Шапка меню со всеми страницами и на ней кнопка logout

// TODO: Автоматически обновлять токен при протухании

// TODO: Форма для создания бренда (на странице с товарами)
// TODO: Выбор бренда из списка при создании товара (опционально)



@SpringBootApplication
@EnableScheduling
public class WebAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebAppApplication.class, args);
	}

}
