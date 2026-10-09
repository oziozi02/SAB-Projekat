# Movie Rating System — SQL Server + JDBC

A database project built for the Databases course at ETF Belgrade.
The system manages movies, genres, users, ratings, and watchlists,
with automated behavior tracking via SQL Server triggers and procedures.

## Project Structure

- `jo210476.sql` - Complete database setup (tables, constraints, defaults, triggers, procedures, and functions)
- `jo210476-tsql.sql` - Triggers, stored procedures, and functions only (for re-running without recreating the schema)
- `SAB_domaci_2526.pdf` - Original project requirements
- `student/` - Java JDBC implementation
  - `DB.java` - Database connection (singleton)
  - `jo210476_GeneralOperations.java` - General operations (erase all)
  - `jo210476_GenresOperations.java` - Genre CRUD operations
  - `jo210476_MoviesOperations.java` - Movie CRUD operations
  - `jo210476_RatingsOperations.java` - Rating operations
  - `jo210476_TagsOperations.java` - Tag operations
  - `jo210476_UsersOperations.java` - User operations and recommendations
  - `jo210476_WatchlistsOperations.java` - Watchlist operations

## Features

- **Genre & movie management** - Add, update, and remove movies and genres
- **Rating system** - Users rate movies 1–10, with extreme score blocking
- **Watchlists** - Users maintain personal watchlists
- **Movie trend tracking** - Automatic status updates (Trending, Rising, Falling, Classic)
- **Recommendations** - Personalized movie recommendations based on favorite genres
- **User profiling** - Automatic user descriptions (curious, focused, undefined)
- **Thematic specializations** - Users earn specializations based on their ratings
- **Rewards** - Users earn rewards for rating underrated films in favorite genres

## Setup

1. Run `jo210476.sql` in SSMS to create the database and tables
2. Configure the JDBC connection in `DB.java`:
   - Server: `localhost`
   - Port: `1433`
   - Database: `Filmovi`

## Technologies

- SQL Server 2025 Express
- Java JDBC (mssql-jdbc driver)
- SSMS 22
