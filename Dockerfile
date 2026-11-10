# ---------- Frontend ----------
FROM node:22 AS frontend

WORKDIR /app/frontend

COPY frontend/package*.json ./
RUN npm install

COPY frontend/ ./
RUN npm run build


# ---------- Backend ----------
FROM eclipse-temurin:21-jdk AS backend

WORKDIR /app

COPY pom.xml mvnw ./
COPY .mvn .mvn

RUN chmod +x mvnw
RUN ./mvnw dependency:go-offline

COPY src ./src

# Put the React build inside src/main/resources/static BEFORE packaging, so it
# ends up on the classpath inside the jar. That is the only place Spring Boot
# looks for static files.
COPY --from=frontend /app/frontend/dist ./src/main/resources/static

RUN ./mvnw clean package -DskipTests


# ---------- Final Image ----------
FROM eclipse-temurin:21-jre

WORKDIR /app

# The jar now contains both the API and the React page.
COPY --from=backend /app/target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
