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

func index(w http.ResponseWriter, r *http.Request) {
	templates.ExecuteTemplate(w, "index.gohtml", nil)
}

func search(w http.ResponseWriter, r *http.Request) {
	if r.Method != "POST" {
		http.Redirect(w, r, "/", http.StatusMethodNotAllowed)
	} else {
		err := r.ParseForm()
		if err != nil {
			// TODO: add middleware
			fmt.Println("Form parsing error")
		}
		query := r.PostFormValue("search")
		fmt.Println(query)
		http.Redirect(w, r, "/", http.StatusSeeOther)
	}
}
