## **Simple Todo Server**

Simple todo server with JWT Authentication and SQLite, a database called "data.db" will be created at root folder when the application start
****
User routes:
****
**Creates a User**

`POST` -> `http://localhost:8080/user/`

body

    {
        "name":"Marcelo Alban",
        "email":"youremail@gmail.com",
        "password": "yourpassword"
    }

****
**Generate Token**

`POST` -> `http://localhost:8080/user/generateToken`

body

    {
    "email":"youremail@gmail.com",
    "password": "yourpassword"
    }

****
Todo routes(All of these routes are authenticated)

**Return all**

`GET` -> `http://localhost:8080/todo/`
****
**Return a filtered Todo**

`GET` -> `http://localhost:8080/todo/{id}`
****
**Creates a Todo**

`POST` -> `http://localhost:8080/todo/`

body

    {
        "todo" : "Todo description",
        "done" : false
    }

****
**Updates a specific Todo**

`PUT` -> `http://localhost:8080/todo/{id}`

body

    {
        "todo" : "Another todo description",
        "done" : false
    }

****
**Excludes a specific Todo**

`DELETE` -> `http://localhost:8080/todo/{id}`
