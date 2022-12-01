package web

import (
	"net/http"
)

func Server() {
	root := http.Dir("web/")
	staticFileServer := http.FileServer(root)
	mux := http.NewServeMux()
	mux.HandleFunc("/add.html", addHandler)
	mux.HandleFunc("/add", add)
	mux.HandleFunc("/edit.html", editHandler)
	mux.HandleFunc("/edit", edit)
	mux.HandleFunc("/search", search)
	mux.Handle("/static/", staticFileServer)
	mux.HandleFunc("/", indexHandler)
	http.ListenAndServe(":80", mux)
}
