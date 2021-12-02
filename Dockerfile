#На основе чего будет состоять образ — adoptopenjdk/openjdk11.
FROM adoptopenjdk/openjdk11:ubi

# Добавляем аргумент в образ с именем JAR_FILE, который находится в папке target.
# Причем нынешняя папка определяется по месту Dockerfile.
ARG JAR_FILE=target/*.jar

# Теперь нужно передать эти переменные внутрь докер контейнера. Это environment variable.
ENV BOT_NAME=defaultg_bot
ENV BOT_TOKEN=2222222222:AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
ENV BOT_DB_USERNAME=db_username
ENV BOT_DB_PASSWORD=db_pwd

#Копируем в докер-образ jar нашего проекта.
COPY ${JAR_FILE} app.jar

# Эта строка по сути содержит массив, созданный из команды в терминале, которую разделили по пробелу.
 # *** Чтобы передать переменную в запросе, нужно добавить следующую конструкцию: -D{имя переменной}=”{значение переменной}”. Фигурные скобки не дописываем ;)
 # *** Получим запрос, при котором будет запущено наше приложение с предопределенными значениями — имя и токена бота:
 # ***  java -jar -Dspring.datasource.username=user -Dspring.datasource.password=pwd -Dbot.username=asd_bot -Dbot.token=228:asd *.jar
# !!!!!! Последняя строка в Dockerfile (которая начинается с ENTRYPOINT) должна быть без переноса элементов.
# !!!!!! Если сделать перенос, работать этот код не будет.
ENTRYPOINT ["java","-Dspring.datasource.username=${BOT_DB_USERNAME}","-Dspring.datasource.password=${BOT_DB_PASSWORD}","-Dtelegrambot.botUserName=${BOT_NAME}","-Dtelegrambot.botToken=${BOT_TOKEN}","-jar","/app.jar"]