[Тестовое задание  - Media-tel](https://github.com/boomzin/mediateltesttask)

Прилоежние принимает REST-подобный HTTP запрос в параметрах которого передаются название города (например "Moscow") и код страны (например "ru"). 
Во время выполнения запроса осуществляется поиск в базе данных прогнозов погоды, если необходимые данные не найдены в базе данных
программа отсылает запрос к сервису API OpenWeatherMap: http://openweathermap.org/api с запросом на получение недостающих данных. 
Обновленные данные о погоде записываются в базу данных.
Пользователю возвращает текущую температуру в этом городе и минимальную из прогнозируемых температур в ближайшие 3 дня в JSON формате.

Приложение самодостаточное, запускаться из командной строки и не требует отдельно установленных servlet контейнеров, application серверов и т.п.

Приложение построено с использованием Spring Boot

#### База данных Postgress в памяти с таблицей:
- **CityForecast** - хранит сущности прогнозов
    - соответствует классу `CityForecast.class`, хранит информацию о координатах города, прогноз погоды и время этого прогноза.

Порядок запуска из репозитория:
<pre>
git clone https://github.com/boomzin/mediateltesttask
cd mediateltesttask/
bash startDatabase.sh
chmod +x gradlew
./gradlew assemble
java -jar ./build/libs/mediateltesttask-0.0.1-SNAPSHOT.jar 
</pre>

После запуска будут доступен endpoint:
<pre>http://localhost:8087/temperature</pre>

Пример запроса:
<pre>http://localhost:8087/temperature?cityName=moscow&countryCode=ru</pre>



