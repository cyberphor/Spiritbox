package database

import (
	"database/sql"

	_ "github.com/mattn/go-sqlite3"
)

func Server() {
	database, err := sql.Open("sqlite3", "spiritbox.db")
	if err != nil {
		panic(err)
	}
	statement := `CREATE TABLE IF NOT EXISTS indicators (
		reportNumber INTEGER PRIMARY KEY,
		location TEXT,
		dateTimeGroup TEXT,
		tactic TEXT,
		sourceIpAddress TEXT,
		sourceHostname TEXT,
		destinationIpAddress TEXT,
		destinationHostname TEXT,
		direction TEXT,
		numberOfSystemsAffected TEXT,
		actionsTaken TEXT,
		identificationMethod TEXT,
		UNIQUE(reportNumber)
	);`
	query, err := database.Prepare(statement)
	if err != nil {
		panic(err)
	}
	query.Exec()
	database.Close()
}
