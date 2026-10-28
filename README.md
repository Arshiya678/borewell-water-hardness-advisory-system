# Borewell Water Hardness Advisory System (BWHAS)

A Java console application for registering water sources, recording water-hardness readings, classifying hardness, and generating usage advisories. The project demonstrates object-oriented programming and JDBC-based MySQL persistence.

## Technologies

- Java
- MySQL
- JDBC (MySQL Connector/J)
## Project structure

- `src/com/bwhas/model/` — domain model and water-source types
- `src/com/bwhas/dao/` — data access interfaces and implementations
- `src/com/bwhas/service/` — hardness classification and validation
- `src/com/bwhas/exception/` — custom exceptions
- `src/com/bwhas/util/` — database connection utility
- `src/com/bwhas/main/` — application entry point and model test runner
- `schema.sql` — MySQL schema and sample water-source/test data

## MySQL Connector/J Dependency

This project uses MySQL Connector/J to connect Java to MySQL.

- Download the official JDBC driver from the [MySQL Connector/J download page](https://dev.mysql.com/downloads/connector/j/).
- Add the downloaded JAR to your project's classpath.
- Configure your database credentials using environment variables as described in the setup instructions.

The driver JAR is not included in this repository.

## Setup

1. Install Java and MySQL.
2. In MySQL, run `schema.sql` to create `bwhas_db` and its tables/sample data. Review the SQL before running it against any database containing data you need to keep.
3. Configure your database connection using environment variables. The defaults are `DB_URL=jdbc:mysql://localhost:3306/bwhas_db` and `DB_USER=root`; set `DB_PASSWORD` to your local MySQL password. Do not commit credentials to Git.
4. Compile the Java source files with the downloaded MySQL Connector/J JAR on the classpath, then run `com.bwhas.main.BWHASApplication`. The exact compile/run commands depend on your Java version and terminal setup.

### Set environment variables on macOS (zsh)

```bash
export DB_URL="jdbc:mysql://localhost:3306/bwhas_db"
export DB_USER="root"
export DB_PASSWORD="your-local-mysql-password"
```

Run the application from the same terminal session. Replace the example password with your own local MySQL password; do not put the real value in this README or source code.

## Concepts demonstrated

- Encapsulation, inheritance, polymorphism, and abstraction
- Interfaces and packages
- Custom exception handling and input validation
- JDBC, DAO pattern, parameterized SQL queries, and CRUD operations

## Security note

Database credentials are read from environment variables instead of being hardcoded. The `app_user` table is included as a schema example but has no seeded login credentials; do not store plaintext passwords if authentication is added.
