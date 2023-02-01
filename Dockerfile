#На основе чего будет состоять образ — adoptopenjdk/openjdk11.
FROM adoptopenjdk/openjdk11:ubi

# Добавляем аргумент в образ с именем JAR_FILE, который находится в папке target.
# Причем нынешняя папка определяется по месту Dockerfile.
ARG JAR_FILE=target/*.jar
#ARG WAIT_FOR_DB=wait-for-mysql.sh

# Теперь нужно передать эти переменные внутрь докер контейнера. Это environment variable.
# !!! Значения переменных будут другие. Те, которые мы передадим в Dockerfile, тем не менее, требуют дать значения по умолчанию
ENV BOT_NAME=default_bot_name
ENV BOT_TOKEN=2222222222:AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
ENV BOT_DB_USERNAME=default_db_user
ENV BOT_DB_PASSWORD=default_db_password
ENV QP_LOGIN=default_login
ENV QP_PASSWORD=default_pwd

#Копируем в докер-образ jar нашего проекта.
COPY ${JAR_FILE} app.jar
#COPY ${WAIT_FOR_DB} wait-for-mysql.sh
#RUN chmod +x /wait-for-mysql.sh
#COPY wait-for-it.sh wait-for-it.sh
#RUN chmod +x /wait-for-it.sh

# Эта строка по сути содержит массив, созданный из команды в терминале, которую разделили по пробелу.
 # *** Чтобы передать переменную в запросе, нужно добавить следующую конструкцию: -D{имя переменной}=”{значение переменной}”. Фигурные скобки не дописываем ;)
 # *** Получим запрос, при котором будет запущено наше приложение с предопределенными значениями — имя и токена бота:
 # ***  java -jar -Dspring.datasource.username=user -Dspring.datasource.password=pwd -Dbot.username=asd_bot -Dbot.token=228:asd *.jar
# !!!!!! Последняя строка в Dockerfile (которая начинается с ENTRYPOINT) должна быть без переноса элементов.
# !!!!!! Если сделать перенос, работать этот код не будет.
ENTRYPOINT ["java","-Dspring.datasource.username=${BOT_DB_USERNAME}","-Dspring.datasource.password=${BOT_DB_PASSWORD}","-Dtelegrambot.botUserName=${BOT_NAME}","-Dtelegrambot.botToken=${BOT_TOKEN}","-Dquickpower.login.value=${QP_LOGIN}","-Dquickpower.password.value=${QP_PASSWORD}","-jar","app.jar"]