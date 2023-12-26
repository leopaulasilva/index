# Pokemon Trainer Index

This project is a Pokemon Trainer Index built using Spring Boot. It provides a RESTful secure API for managing Pokemon trainers.
It includes Spring Data JPA for database interaction, Spring Security for authentication and authorization, and H2 Database for data storage.
### Requirements

- Java 17
- Spring Boot 3.2.0
- H2

### Dependencies
The project has the following dependencies:

Spring Boot Starters:
- spring-boot-starter-data-jpa
- spring-boot-starter-web
- spring-boot-starter-security
- spring-boot-starter-test

Security:
- jjwt-api
- jjwt-impl
- jjwt-jackson

Development Tools:
- spring-boot-devtools
- commons-lang3

Database:
- h2 (H2 Database)

Other Libraries:
- lombok
- modelmapper (version: 3.1.1)
- spring-boot-starter-validation

## How to test
### Signup endpoint
Hit the following API:
```js
- curl --location 'http://localhost:8080/index/api/v1/auth/signup' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=860FDE1729E3027FDBBCAC75B9D066A2' \
--data-raw '{
"firstName": "Leonardo",
"lastName": "Silva",
"email": "leo@google.com",
"password": "123456"
}'
```

Remember to include the required request body. Afterwards, examine the response: a successful 
registration message along with the user token will be returned.


Response:
```json

{
"token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzcHJpbmdzZWN1cml0eUBnb29nbGUuY29tIiwiaWF0IjoxNzAzMzc0MTA1LCJleHAiOjE3MDMzNzU1NDV9.skJRpg5v34bBC0j2gpMx2pe1bvrXFprhl4U5Wb1p4EU"
}
```

### Signin endpoint
Hit the Sign-In API URL:
Ensuring that the request body  including a valid password in the request body. 
Proceed to examine the response: the authentication process will be successful, and the user token will be returned.
```js
- curl --location 'http://localhost:8080/index/api/v1/auth/signin' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=860FDE1729E3027FDBBCAC75B9D066A2' \
--data-raw '{
"email": "springsecurity@google.com",
"password": "123456"
}'
```  

Response:
```json

{
"token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzcHJpbmdzZWN1cml0eUBnb29nbGUuY29tIiwiaWF0IjoxNzAzMzc0MTA5LCJleHAiOjE3MDMzNzU1NDl9.i2RkwSYgT0o2vAwnYDRwpeGm3110XugV5or4NHHU_PQ"
}
```
### Create trainer endpoint
As this API is protected,Copy the user token generated during the sign-up process and include it as an authorization header (Bearer Token type). Send another request to the Resource API URL and examine the response: we should now have successful access to the desired resource.
This endpoint create or update the trainer's register along with his pokemons 
```js
- curl --location 'http://localhost:8080/index/api/v1/trainers' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzcHJpbmdzZWN1cml0eUBnb29nbGUuY29tIiwiaWF0IjoxNzAzMzc0MTA5LCJleHAiOjE3MDMzNzU1NDl9.i2RkwSYgT0o2vAwnYDRwpeGm3110XugV5or4NHHU_PQ' \
--header 'Cookie: JSESSIONID=860FDE1729E3027FDBBCAC75B9D066A2' \
--data-raw '{
"name": "lEO",
"email": "maria@example.com",
"instagramProfile": "https://www.instagram.com/oloco/",
"pokemons": [
            {
            "name": "bulbasaur" 
            },
            {
            "name": "venusaur"
            }
        ]
}'
```

 
Response:
```json
{
"id":1,
"name":"lEO",
"email":"maria@example.com",
"instagramProfile":"https://www.instagram.com/oloco/",
"pokemons":[
    {
    "name":"bulbasaur",
    "baseExperience":64,
    "weight":69,
    "abilities":[
            {
            "id":1,
            "name":"overgrow"
            },
            {
            "id":2,
            "name":"chlorophyll"
            }
            ],
            "id":1,
            "pokemon_id":1
            },
            {
            "name":"venusaur",
            "baseExperience":263,
            "weight":1000,
            "abilities":[
            {
            "id":3,
            "name":"overgrow"
            },
            {
            "id":4,
            "name":"chlorophyll"
            }
            ],
            "id":2,
            "pokemon_id":3
            }
        ]
}
```
### Find all trainers endpoint
```js
curl --location 'http://localhost:8080/index/api/v1/trainers' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzcHJpbmdzZWN1cml0eUBnb29nbGUuY29tIiwiaWF0IjoxNzAzMzc0MTA5LCJleHAiOjE3MDMzNzU1NDl9.i2RkwSYgT0o2vAwnYDRwpeGm3110XugV5or4NHHU_PQ' \
--header 'Cookie: JSESSIONID=860FDE1729E3027FDBBCAC75B9D066A2'
```
Response:
```json
[
{
"id": 1,
"name": "lEO",
"email": "maria@example.com",
"instagramProfile": "https://www.instagram.com/oloco/",
"pokemons": [
{
"id": 1,
"name": "bulbasaur",
"weight": 69,
"abilities": [
{
"ability": {
"name": "overgrow"
}
},
{
"ability": {
"name": "chlorophyll"
}
}
],
"base_experience": 64
},
{
"id": 2,
"name": "venusaur",
"weight": 1000,
"abilities": [
{
"ability": {
"name": "overgrow"
}
},
{
"ability": {
"name": "chlorophyll"
}
}
],
"base_experience": 263
}
]
}
]

```

### Delete endpoint by trainer id
```js
curl --location --request DELETE 'http://localhost:8080/index/api/v1/trainers/1' \
--header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzcHJpbmdzZWN1cml0eUBnb29nbGUuY29tIiwiaWF0IjoxNzAzMzcyNzk1LCJleHAiOjE3MDMzNzQyMzV9.dw_C-YgGJrkFnFryPYvknnJEmPRnkNCEcA-ZhPQktzg' \
--header 'Cookie: JSESSIONID=860FDE1729E3027FDBBCAC75B9D066A2'