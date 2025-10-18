package main

import (
	"encoding/json"
	"fmt"
	"io/ioutil"
	"log"
	"net/http"

	"github.com/gorilla/mux"
)

// Downloading the needed package -->
//  --> go mod init example.com/myapp
// ---> go get github.com/gorilla/mux

type Article struct {
	ID      string `json:"id"`
	Title   string `json:"title"`
	Desc    string `json:"desc"`
	Content string `json:"content"`
}

var Articles []Article

/*type Articles []Article

func allArticles(w http.ResponseWriter, r *http.Request) {
	Articles := Articles{
		Article{Title: "Test Title", Desc: "Test Description", Content: "Welcome to Home page!"},
	}

	fmt.Println("Endpoint Hit: All Articles")
	json.NewEncoder(w).Encode(Articles)
}
*/

func homePage(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintf(w, "Welcome to Home Page!")
}

func createNewArticle(w http.ResponseWriter, r *http.Request) {
	// Getting the body of the post request
	w.Header().Set("Content-Type", "application/json")
	reqBody, _ := ioutil.ReadAll(r.Body)

	//fmt.Fprintf(w, "%+v", string(reqBody))
	var article Article
	json.Unmarshal(reqBody, &article)

	Articles = append(Articles, article)
	json.NewEncoder(w).Encode(article)
}

func deleteArticle(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")
	vars := mux.Vars(r)
	id := vars["id"]

	for index, article := range Articles {
		if article.ID == id {
			Articles = append(Articles[:index], Articles[index+1:]...)

			fmt.Fprintf(w, "Article with ID %v has been deleted successfully", id)
			return
		}
	}

	// If article not found
	fmt.Fprintf(w, "Article with ID %v not found", id)
}

func updateArticle(w http.ResponseWriter, r *http.Request) {
	w.Header().Set("Content-Type", "application/json")

	vars := mux.Vars(r)
	id := vars["id"]

	// Getting the body of the put request
	reqBody, _ := ioutil.ReadAll(r.Body)

	var updatedArticle Article
	json.Unmarshal(reqBody, &updatedArticle)

	for index, article := range Articles {
		if article.ID == id {
			Articles[index] = updatedArticle
			json.NewEncoder(w).Encode(updatedArticle)
			return
		}
	}

	// If article not found
	fmt.Fprintf(w, "Article with ID %v not found", id)
}

func returnAllArticles(w http.ResponseWriter, r *http.Request) {
	fmt.Println("Endpoint Hit: Return All Articles")
	json.NewEncoder(w).Encode(Articles)
}
func returnSingleArticle(w http.ResponseWriter, r *http.Request) {
	vars := mux.Vars(r)
	key := vars["id"]

	fmt.Fprintf(w, "Key: "+key)

	fmt.Fprintf(w, " \n")

	for _, article := range Articles {
		if article.ID == key {
			json.NewEncoder(w).Encode(article)
		}
	}
}

func handleRequests() {
	//http.HandleFunc("/", homePage)
	//http.HandleFunc("/articles", returnAllArticles)
	//log.Fatal(http.ListenAndServe(":8080", nil))

	// New instance of a mux router

	myRouter := mux.NewRouter().StrictSlash(true)

	//Replacing http.handleFunc with myRouter.HandleFunc

	myRouter.HandleFunc("/", homePage)
	myRouter.HandleFunc("/articles", returnAllArticles)
	myRouter.HandleFunc("/all", returnAllArticles)
	//log.Fatal(http.ListenAndServe(":8080", myRouter))
	myRouter.HandleFunc("/article", createNewArticle).Methods("POST")
	myRouter.HandleFunc("/article/{id}", deleteArticle).Methods("DELETE")
	myRouter.HandleFunc("/article", updateArticle).Methods("PUT")
	myRouter.HandleFunc("/article/{id}", returnSingleArticle)

	log.Fatal(http.ListenAndServe(":8080", myRouter))
}

func main() {
	//handleRequests()
	fmt.Println("Rest API v2.0 - Mux Routers")
	Articles = []Article{
		{
			ID:      "1",
			Title:   "The review of Homeland series on season 2",
			Desc:    "The Homeland series is a gripping political thriller that explores the complexities of national security and personal relationships.",
			Content: "The Homeland series on season 2 continues to deliver intense drama and unexpected twists. The storyline delves deeper into the characters' lives, particularly focusing on Carrie Mathison's struggles with her bipolar disorder while trying to thwart terrorist plots."},

		{
			ID:      "2",
			Title:   "The climate change is getting worse each and every year",
			Desc:    "The article is about climate change and its effects",
			Content: "Climate change is very concerning. We need to take action now!"},
		{
			ID:      "3",
			Title:   "The impact of technology on society",
			Desc:    "This article discusses the various ways technology is shaping our lives.",
			Content: "Technology has become an integral part of our daily lives, influencing how we communicate, work, and interact with the world around us."},
		{
			ID:      "4",
			Title:   "The future of artificial intelligence",
			Desc:    "This article explores the potential impact of AI on various industries.",
			Content: "Artificial intelligence is poised to revolutionize industries by automating tasks, improving efficiency, and enabling new capabilities. However, it also raises ethical concerns and challenges that society must address."},
	}
	handleRequests()
}

// Creating a book, deleting a book, and updating a book functions
/*
Book struct (model)
type book struct {
	ID     string `json:"id"`
	Isbn   string `json:"isbn"`
	Title  string `json:"title"`
	Author *Author `json:"author"`
}

//Author struct
type Author struct {
	FirstName string `json:"firstname"`
	LastName  string `json:"lastname"`
}

//Getting all books
func getAllBooks(w http.ResponseWriter, r *http.Request) {

}

app.get('/books', getAllBooks)

*/
