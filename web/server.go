package web

import (
	"net/http"
)

func Server() {
	filePath := http.Dir("web/")
	fileServer := http.FileServer(filePath)
	http.Handle("/static/", fileServer)
	http.HandleFunc("/search", search)
	http.HandleFunc("/", index) // if all else fails, send them here
	http.ListenAndServe(":443", nil)
}
