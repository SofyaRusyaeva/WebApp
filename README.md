# Описание концепции
**Тема проекта:** приложение для интернет-магазина косметики.
Приложение для магазина косметики будет включать следующие основные функции:

* Каталог товаров:
  + просмотр и фильтрация товаров,
  + сортировка по цене,
  + добавление в корзину 
* Корзина покупок:
  + добавление и удаление товаров,
  + сохранение корзины между сессиями
* Возможность оформления заказа
* Личный кабинет пользователя:
  + регистрация и авторизация,
  + просмотр истории заказов

# Схема взаимодействия
* Клиент (Frontend):
   + Веб-интерфейс, отправляет запросы к серверу через REST API.
   + Получает данные от сервера и отображает их пользователю.
* Сервер (Backend):
   + Написан на Java с использованием Spring Boot.
   + Обрабатывает запросы от клиента.
   + Взаимодействует с базой данных для получения и сохранения данных.
   + Возвращает данные клиенту в формате JSON.
* База данных:
   + Хранит данные о товарах, пользователях, заказах и корзинах.

# Структура базы данных
* Пользователь: id, имя пользователя, email, пароль, номер телефона
* Заказ: id, id пользователя, итоговая стоимость, дата, статус заказа
* Товар в заказе: id, id заказа, id товара, количество
* Корзина: id, id пользователя, итоговая стоимость
* Товар в корзине: id, id корзины, id товара, количество
* Товар: id, название, описание, категория товара, бренд, цена
* Бренд: id, название, страна

# Стек технологий
* Backend:
  + Язык программирования: Java
  + Фреймворк: Spring Boot
* Frontend:
  + Thymeleaf (Spring)
  + HTML, CSS
* База данных: PostgreSQL
* Сборка: Maven
* Среда разработки: IntelliJ IDEA
* Дополнительно:
  + GitHub
  + Диаграммы и проектирование: UML (Draw.io)
  + Документирование: Markdown
  + Контейнеризация: Docker
  + Аутентификация: JWT (JSON Web Tokens)
  + Postman


# Структура API
## Пользователи

### !Информация о текущем пользователе
**Метод**: `GET`

**URL**: `/api/shop/user/{userId}`

#### Успешный ответ

**Code**: `200 OK`

```json
{
  "id": 1,
  "username": "user",
  "email": "user@example.com",
  "phone": 123456789
}
```
#### Ошибка
Пользователь не авторизован

**Code**: `401 Unauthorized`



### Информация о конкретном пользователе
**Метод**: `GET`

**URL**: `/api/shop/users/{id}`

#### Успешный ответ

**Code**: `200 OK`

```json
{
  "userId": 1,
  "userName": "Ann",
  "email": "ann@gmail.com",
  "password": "$2a$10$uRCsPwku1eHlVwsbOtMkuOnjj2UlprtbX85/5uPZwx3n2JA.irjki",
  "phone": "1234567890"
}
```
#### Ошибка
Пользователь не найден

**Code**: `404 Not Found`


### Создание нового пользователя 
**Метод**: `POST`

**URL**: `/api/shop/users`

**Тело запроса**
```json
{
  "userName": "Kate",
  "email": "kate@gmail.com",
  "password": "qwerty",
  "phone": "+79272053156"
}
```

#### Успешный ответ

**Code**: `201 Created`

```json
{
  "userId": 3,
  "userName": "Kate",
  "email": "kate@gmail.com",
  "password": "$2a$10$bZqaMDoPcYqo90sFJULIDOKZJ01h5/JUVMD7sYC40T9Ru/IyJ23/y",
  "phone": "+79272053156"
}
```
#### Ошибка
Некорректные данные (например, email уже существует)

**Code**: `400 Bad Request`

### !Обновление информации о текущем пользователе
**Метод**: `PUT`

**URL**: `/api/users/me`

```json
{
  "username": "user",
  "email": "user@example.com",
  "password": "123",
  "phone": 123456789
}
```
#### Успешный ответ

**Code**: `200 OK`

```json
{
  "id": 1,
  "username": "user",
  "email": "user@example.com",
  "phone": 123456789
}
```
#### Ошибка
Некорректные данные

**Code**: `400 Bad Request`

Пользователь не авторизован

**Code**: `401 Unauthorized`

Телефон или email уже используются другим пользователем

**Code**: `409 Conflict`


### !Удаление текущего пользователя
**Метод**: `DELETE`

**URL**: `/api/users/me`

#### Успешный ответ

**Code**: `204 No Content`

#### Ошибка
Пользователь не авторизован

**Code**: `401 Unauthorized`


### !Регистрация пользователя
**Method**: `POST`

**URL**: `/api/auth/register`

**Тело запроса**
```json
{
  "username": "user",
  "email": "user@example.com",
  "password": "123",
  "phone": 123456789
}
```

#### Успешный ответ

**Code**: `201 Created`

```json
{
  "id": 1,
  "username": "user",
  "email": "user@example.com",
  "phone": 123456789
}
```
#### Ошибка
Некорректные данные

**Code**: `400 Bad Request`


### !Авторизация пользователя
**Method**: `POST`

**URL**: `/api/auth/login`

#### Успешный ответ

**Code**: `200 OK`

```json
{
  "token": "jwt_token"
}
```

#### Ошибка
Неверный email или пароль

**Code**: `401 Unauthorized`



# Товары

### Список товаров
**Method**: `GET`

**URL**: `/api/shop/products`

#### Успешный ответ

**Code**: `200 OK`

```json
[
  {
    "id": 1,
    "name": "Lipstick",
    "price": 1599
  },
  {
    "id": 2,
    "name": "Foundation",
    "price": 2499
  }
]

```


### Информация о товаре
**Method**: `GET`

**URL**: `/api/shop/products/{id}`

#### Успешный ответ

**Code**: `200 OK`

```json
{
  "id": 1,
  "name": "Lipstick",
  "description": "Matte finish lipstick",
  "category": "Lips",
  "brand_id": 123,
  "price": 1599
}

```

#### Ошибка
Товар не найден

**Code**: `404 Not Found`


### Добавление нового товара
**Method**: `POST` 

**URL**: `/api/shop/product`

```json
{
  "name": "Lipstick",
  "description": "Matte finish lipstick",
  "category": "Lips",
  "brand_id": 123,
  "price": 1599
}
```

#### Успешный ответ

**Code**: `201 Created`

```json
{
  "id": 1,
  "name": "Lipstick",
  "description": "Matte finish lipstick",
  "category": "Lips",
  "brand_id": 123,
  "price": 1599
}

```

#### Ошибка
Некорректные данные

**Code**: `400 Bad Request`

### Изменения товара
**Method**: `PUT` 

**URL**: `/api/product/{id}`

```json
{
  "name": "Lipstick",
  "description": "Matte finish lipstick",
  "category": "Lips",
  "brand_id": 123,
  "price": 1300
}
```

#### Успешный ответ

**Code**: `200 OK`

```json
{
  "id": 1,
  "name": "Lipstick",
  "description": "Matte finish lipstick",
  "category": "Lips",
  "brand_id": 123,
  "price": 1300
}
```

#### Ошибка
Некорректные данные

**Code**: `400 Bad Request`

Товар не найден

**Code**: `404 Not Found`


### Удаление товара
**Method**: `DELETE` 

**URL**: `/api/shop/product/{id}`

#### Успешный ответ

**Code**: `204 No Content`

#### Ошибка
Товар не найден

**Code**: `404 Not Found`




## Заказы
### !Список заказов текущего пользователя
**Method**: `GET`

**URL**: `/api/shop/orders`

#### Успешный ответ

**Code**: `200 OK`

```json
[
  {
    "id": 1,
    "status": "processing",
    "total_price": 3198
  }
]
```
#### Ошибка
Пользователь не авторизован

**Code**: `401 Unauthorized`


### Информация о заказе
**Method**: `GET`

**URL**: `/api/shop/orders/{id}`

#### Успешный ответ

**Code**: `200 OK`

```json
{
  "id": 1,
  "status": "processing",
  "total_price": 3198
}
```
#### Ошибка
Заказ не найден

**Code**: `404 Not Found`


### ?Создание заказа
**Method**: `POST`

**URL**: `/api/shop/orders`
```json
{
    "cart_id": 1
}
```
#### Успешный ответ

**Code**: `201 Created`

```json
{
  "id": 1,
  "status": "in_progress",
  "products": [
    {
      "product_id": 1,
      "quantity": 2
    }
  ],
  "total_price": 3198
}
```
#### Ошибка
Корзина пуста

**Code**: `400 Bad Request`

Корзина не найдена

**Code**: `404 Not Found`

### Обновление статуса заказа
**Method**: `PUT`

**URL**: `/api/orders/{id}`

```json
{
    "status": "ready"
}
```

**Code**: `201 Created`

```json
{
  "id": 2,
  "status": "ready"
}
```
#### Ошибка
Заказ не найден

**Code**: `404 Not Found`


## Корзина
###  Получение содержимого корзины текущего пользователя
**Method**: `GET`

**URL**: `/api/cart`

**Code**: `200 OK`

```json
[
  {
    "product_id": 1,
    "quantity": 2
  }
]
```
#### Ошибка
Пользователь не авторизован

**Code**: `401 Unauthorized`

###  Добавление товара в корзину
**Method**: `POST`

**URL**: `/api/cart`

**Тело запроса**
```json
{
  "product_id": 1,
  "quantity": 2
}
```

#### Успешный ответ

**Code**: `200 OK`
```json
{
  "product_id": 1,
  "quantity": 2
}
```
#### Ошибка
Некорректные данные

**Code**: `400 Bad Request`

###  Изменение количества товара в корзине
**Method**: `PUT`

**URL**: `/api/cart/{product_id}`

**Тело запроса**
```json
{
  "product_id": 1,
  "quantity": 5
}
```

#### Успешный ответ

**Code**: `200 OK`

```json
{
  "product_id": 1,
  "quantity": 5
}
```

#### Ошибка
Некорректные данные

**Code**: `400 Bad Request`


### Удаление товара из корзины
**Method**: `DELETE`

**URL**: `/api/cart/{product_id}`

#### Успешный ответ

**Code**: `204 No Content`

#### Ошибка
Товар не найден

**Code**: `404 Not Found`
