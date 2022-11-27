package main

import (
	"github.com/cyberphor/spiritbox/database"
	"github.com/cyberphor/spiritbox/web"
)

func main() {
	database.Server()
	web.Server()
}
