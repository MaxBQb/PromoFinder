# <p align="center"> PromoFinder
<p align="center"><img width="300" src="app/src/main/ic_launcher-playstore.png"></p>

Приложение для поиска промокодов ([backend](https://gitlab.com/p6505) by Inozpavel)

## Демонстрация

![Регистрация и вход](/demo/auth.png)
![Основной функционал приложения](/demo/main.png)

~~Документация: [на API](http://mc.icomm.pro:9080/swagger/index.html)~~ (более недоступна)

## Технологический стек и немного деталей
- Вёрстка: XML & Data Binding
- Кратко об архитектуре: Clean Architecture, MVVM
- DI: Koin (с кодогенерацией)
- Навигация: Fragment Navigation API & Safe Args
- Локальное хранение данных: SharedPreferences
- Работа с сетью: Retrofit2
- Реактивность: Kotlin Coroutines & LiveData & Data Binding Observable
- Пагинация: Присутствует (paging lib)
- Поддержка версий Android 5.0+ (API 21+)
