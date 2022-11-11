# Nba Clone App (Android Native Jetpack)

App creada en Android nativo usando XML y los componentes de arquitectura Jetpack. Para poder mostrar los últimos equipos, jugadores y juegos de la NBA.
Se usa el patrón de diseño recomendado por Google MVVM, y la micro-arquitectura Clean Architecture.

## Screenshots
![Home Nba App](assets/app_nba.png)

### Kit de herramientas del proyecto

- Inyección de dependencias (Dagger Hilt)
- Splash Screen.
- SwipeRefreshLayout.
- Base de datos local (Room y SQLite)
- Navegación entre fragmentos (Navigation Jetpack)
- Interfaz creada usando XML y Material Design.
- Configuración de temas Dark y Light.
- Uso de corrutinas nativo en Kotlin y livedata.
- Uso de capa de presentacion usando casos de uso.
- Capa de datos usando Datasources.
- Pruebas unitarias.

El API utilizada para el consumo de datos es:

[Free Nba](https://free-nba.p.rapidapi.com/)

Los endpoints utilizados son:
* [Teams](https://free-nba.p.rapidapi.com/teams)
* [Players](https://free-nba.p.rapidapi.com/players)
* [Games](https://free-nba.p.rapidapi.com/games)
* [Stats](https://free-nba.p.rapidapi.com/stats)
