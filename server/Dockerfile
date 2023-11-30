FROM amazoncorretto:17

# common for all images
ENV MAVEN_HOME /usr/share/maven

COPY --from=maven:3.9.5-eclipse-temurin-11 ${MAVEN_HOME} ${MAVEN_HOME}
COPY --from=maven:3.9.5-eclipse-temurin-11 /usr/local/bin/mvn-entrypoint.sh /usr/local/bin/mvn-entrypoint.sh
COPY --from=maven:3.9.5-eclipse-temurin-11 /usr/share/maven/ref/settings-docker.xml /usr/share/maven/ref/settings-docker.xml

RUN ln -s ${MAVEN_HOME}/bin/mvn /usr/bin/mvn

ARG MAVEN_VERSION=3.9.5
ARG USER_HOME_DIR="/root"
ENV MAVEN_CONFIG "$USER_HOME_DIR/.m2"

ENTRYPOINT ["/usr/local/bin/mvn-entrypoint.sh"]
CMD ["mvn"]

# Копируем исходный код в контейнер
COPY . /usr/src/app

# Устанавливаем рабочую директорию
WORKDIR /usr/src/app

# Выполняем сборку проекта с помощью Maven
RUN mvn clean install -DskipTests


# Копируем JAR-файл в отдельную директорию
RUN mkdir -p /usr/src/app/target-app \
    && cp target/server.jar /usr/src/app/target-app/

# Устанавливаем рабочую директорию для запуска JAR-файла
WORKDIR /usr/src/app/target-app

# Команда для запуска приложения
CMD ["java", "-jar", "server.jar"]
