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

RUN ./mvnw clean package -DskipTests


# ---------- Final Image ----------
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=backend /app/target/*.jar app.jar

# Copy React build into Spring Boot's static folder
COPY --from=frontend /app/frontend/dist /app/static

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]