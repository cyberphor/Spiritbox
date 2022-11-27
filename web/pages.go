package web

import (
	"fmt"
	"net/http"
	"text/template"
)

var templates *template.Template

func init() {
	templates = template.Must(template.ParseGlob("web/templates/*"))
}

func indexHandler(w http.ResponseWriter, r *http.Request) {
	templates.ExecuteTemplate(w, "index.gohtml", nil)
}

func addHandler(w http.ResponseWriter, r *http.Request) {
	templates.ExecuteTemplate(w, "add.gohtml", nil)
}

func add(w http.ResponseWriter, r *http.Request) {
	if r.Method != "POST" {
		http.Redirect(w, r, "/", http.StatusMethodNotAllowed)
	} else {
		err := r.ParseForm()
		if err != nil {
			panic(err)
		}
		query := r.PostFormValue("")
		fmt.Println(query)
		http.Redirect(w, r, "/", http.StatusSeeOther)
	}
}

func search(w http.ResponseWriter, r *http.Request) {
	if r.Method != "GET" {
		http.Redirect(w, r, "/", http.StatusMethodNotAllowed)
	} else {
		err := r.ParseForm()
		if err != nil {
			panic(err)
		}
		query := r.FormValue("search")
		fmt.Println(query)
		http.Redirect(w, r, "/", http.StatusSeeOther)
	}
}
