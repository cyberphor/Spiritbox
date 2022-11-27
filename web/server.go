package web

import (
	"net/http"
)

func Server() {
	root := http.Dir("web/")
	static := http.FileServer(root)
	mux := http.NewServeMux()
	mux.HandleFunc("/search", search)
	mux.Handle("/static/", static)
	mux.HandleFunc("/", index)
	http.ListenAndServe(":80", mux)
}
