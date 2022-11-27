package database

import (
	_ "github.com/lib/pq"
)

func AddTable() {
	/*
		database, err := sql.Open("postgres", "spiritbox.db")
		if err != nil {
			panic(err)
		}
		statement := `CREATE TABLE IF NOT EXISTS users (
			id INTEGER PRIMARY KEY,
			username  TEXT,
			password  TEXT,
			team      TEXT,
			role      TEXT,
			UNIQUE(username)
			);`
		query, err := database.Prepare(statement)
		if err != nil {
			panic(err)
		}
		query.Exec()
		database.Close()
	*/
}
