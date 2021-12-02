#На основе чего будет состоять образ — adoptopenjdk/openjdk11.
FROM adoptopenjdk/openjdk11:ubi

# Добавляем аргумент в образ с именем JAR_FILE, который находится в папке target.
# Причем нынешняя папка определяется по месту Dockerfile.
ARG JAR_FILE=target/*.jar

# *** Теперь нужно передать эти переменные внутрь докер контейнера. Это environment variable.
ENV BOT_NAME=defaultg_bot
ENV BOT_TOKEN=2222222222:AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA

#Копируем в докер-образ jar нашего проекта.
COPY ${JAR_FILE} app.jar

# Эта строка по сути содержит массив, созданный из команды в терминале, которую разделили по пробелу.
 # *** Чтобы передать переменную в запросе, нужно добавить следующую конструкцию: -D{имя переменной}=”{значение переменной}”. Фигурные скобки не дописываем ;)
 # *** Получим запрос, при котором будет запущено наше приложение с предопределенными значениями — имя и токена бота:
 # ***  java -jar -Dbot.username=”asdasdasd_bot” -Dbot.token=”asfaszqfaasdqwe” *.jar
ENTRYPOINT ["java","-Dtelegrambot.botUserName=${BOT_NAME}","-Dtelegrambot.botToken=${BOT_TOKEN}","-jar","/app.jar"]