# Blog Website Application

## Deployment Guide

This guide will help you deploy the Blog Website application to various environments.

### Prerequisites

- Java 17 or higher
- Maven
- MySQL Database

### Environment Variables

The application uses environment variables for configuration. These are defined in the `.env` file for local development, but should be set in your deployment environment's configuration.

#### Required Environment Variables

```
# Database Configuration
DATABASE_URL=jdbc:mysql://your-db-host:3306/your-db-name?useSSL=true&serverTimezone=UTC
DATABASE_USER=your_database_user
DATABASE_PASSWORD=your_database_password
PORT=8081  # Or your preferred port

# Application Configuration
SPRING_PROFILES_ACTIVE=prod
```

#### Optional Environment Variables

```
# JPA Configuration
JPA_DATABASE_PLATFORM=org.hibernate.dialect.MySQL8Dialect
JPA_HIBERNATE_DDL_AUTO=update  # Use 'validate' in production
JPA_SHOW_SQL=false
JPA_FORMAT_SQL=false

# Logging Configuration
LOG_LEVEL_APP=INFO
LOG_LEVEL_WEB=INFO
LOG_LEVEL_SECURITY=INFO

# Session Configuration
SESSION_TIMEOUT=30m
COOKIE_HTTP_ONLY=true
COOKIE_SECURE=true  # Set to true when using HTTPS
```

### Deployment Steps

1. **Build the application**:
   ```
   mvn clean package
   ```

2. **Run the application**:
   ```
   java -jar target/BlogWebsite-0.0.1-SNAPSHOT.jar
   ```

### Deployment to Cloud Platforms

#### Heroku

1. Create a `Procfile` in the root directory with the following content:
   ```
   web: java -jar target/BlogWebsite-0.0.1-SNAPSHOT.jar
   ```

2. Set environment variables in Heroku dashboard or using Heroku CLI:
   ```
   heroku config:set DATABASE_URL=jdbc:mysql://your-db-host:3306/your-db-name
   heroku config:set DATABASE_USER=your_database_user
   heroku config:set DATABASE_PASSWORD=your_database_password
   heroku config:set SPRING_PROFILES_ACTIVE=prod
   ```

3. Deploy to Heroku:
   ```
   git push heroku main
   ```

#### Docker

1. Create a `Dockerfile` in the root directory:
   ```dockerfile
   FROM openjdk:17-jdk-slim
   VOLUME /tmp
   COPY target/BlogWebsite-0.0.1-SNAPSHOT.jar app.jar
   ENTRYPOINT ["java","-jar","/app.jar"]
   ```

2. Build and run the Docker image:
   ```
   docker build -t blog-website .
   docker run -p 8081:8081 --env-file .env blog-website
   ```

### Security Considerations for Production

1. Set `JPA_HIBERNATE_DDL_AUTO=validate` in production to prevent automatic schema changes
2. Ensure `COOKIE_SECURE=true` when using HTTPS
3. Use strong, unique passwords for database access
4. Consider using a connection pool for database connections
5. Set appropriate logging levels for production

### Troubleshooting

- If the application fails to start, check the logs for error messages
- Verify that all required environment variables are set correctly
- Ensure the database is accessible from the deployment environment
- Check that the database user has appropriate permissions