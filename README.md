##Как запустить:
Открыть терминал/командную строку в папке со скачанным проектом и запустить команду
* mvn clean package

После сборки выполнить две команды, обязательно в этом порядке 
(Все еще находясь в корне папки с проектом)
* java -jar ./inn-api/target/apigenerator-1.0.jar 
* java -jar ./generator/target/generator-1.0-jar-with-dependencies.jar 

Первая команда запускает сервер с api которая генерирует ИНН для Москвы, так как я не нашла таких в интернете.
Вторая команда запускает само приложение по генерации данных в файлы.

