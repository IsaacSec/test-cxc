# Getting Started

## 1 - Setup Project

### 1.1 - Requirements

* Java SDK 22

### 1.2 - Up Postgres database


Make available a postgres database with docker

```
docker run -d --name postgres -p 5432:5432 -e POSTGRES_PASSWORD=<password> -e POSTGRES_USER=<user> -e POSTGRES_DB=<db-name> postgres:13-alpine
```

### 1.3 - Add environment variables

```
DB_NAME=?;
DB_HOST=?;
DB_PORT=?;
DB_USER=?;
DB_PASS=?;
```

### 1.4 - Run Migrations

Run the following gradle tasks passing the required parameters for the db connection

```
flywayMigrate -Dflyway.url=<url> -Dflyway.user=<user> -Dflyway.password=<password>
```