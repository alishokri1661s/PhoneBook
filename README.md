﻿# PhoneBook
Simple Phonebook app using Spring Boot, Spring JPA, and MySql database.

## Table of contents
* [General info](#general-info)
* [How to run](#how-to-run)
* [How to use](#how-to-use)
* [Technologies and Dev Dependencies](#technologies-and-dev-dependencies)

## General info
This project contains two REST API endpoints:
* POST http://localhost:8080/api/contact : To add a new contact to the phonebook.
* GET http://localhost:8080/api/contact : To get all contacts in the phonebook.

You can also filter contacts using query parameters on the second endpoint.

ex: GET http://localhost:8080/api/contact?name=ali&phoneNumber=0919&email=gmail

### Contact structure
* Name
* PhoneNumber
* Email
* Company
* GithubId
* List of Github repositories name

#### After saving each contact, a background job enqueue and executed to get all repositories' names from Github API and save to the database. So if you get all contacts you can see the list of Github repositories.

## How to run
To run the project first download the project as a Zip file and extract it.
Then by executing the 'run.bat' file the server will be run.

Also, you have to install MySql and create a new database named 'phonebook'.

## How to use
You can use curl, postman, or some other tools to send HTTP requests.

I used the Intellij Idea to send HTTP requests.

![img.png](img.png)

## Technologies and Dev Dependencies
#### Project is created with:
* Spring Boot
* Spring JPA
* MySql database
#### Dependencies used in this project:
* Lombok
* specification-arg-resolver
* jobrunr

#### IDEA:
* Intellij
