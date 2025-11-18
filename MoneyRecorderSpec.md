I want you to generate code for me.
This file is used to generate code of this project.
This is spring boot project using java. 
This project is located in ./MoneyRecorderAIver.
This project is using maven as build tool.

In ./MoneyRecorderAIver/src/main/java/com/example/MoneyRecorderAIver/Controller we keep all the controllers.

Controller List :
    Controller Name : UserController 
        Request Mapping : /users
            Functions : 
                1. GET /all
                    Description : Get all users
                    Request Body : None
                    Response : 
                        Example 1 : 
                            [
                                {
                                    "userId": 1,
                                    "name": "John Doe",
                                    "email": "john.doe@example.com",
                                    "createdAt": "2023-09-15T12:00:00",
                                    "updatedAt": "2023-09-15T12:00:00"
                                }
                            ]
                2. GET /byUserId
                    Description : Get user by id
                    Request Body : {
                        "userId": 1
                    }
                    Response : 
                        Example 1 : 
                            {
                                "userId": 1,
                                "name": "John Doe",
                                "email": "john.doe@example.com",
                                "createdAt": "2023-09-15T12:00:00",
                                "updatedAt": "2023-09-15T12:00:00"
                            }
                        
                3. POST /create
                    Description : Create new user
                    Request Body : 
                        Example 1 : 
                            {
                                "name": "John Doe",
                                "email": "john.doe@example.com",
                                "password": "password"
                            }
                    Response : 
                        Example 1 : 200 OK

    Controller Name : IncomeController
        Request Mapping : /incomes
            Functions : 
                1. GET /all
                    Description : Get all incomes
                    Request Body : None
                    Response : 
                        Example 1 : 
                            [
                                {
                                    "incomeId": 1,
                                    "userId": 1,
                                    "amount": 100,
                                    "description": "Salary",
                                    "incomeDate": "2023-09-15T12:00:00"
                                }
                            ]
                2. GET /byUserId
                    Description : Get all incomes by user id
                    Request Body : {
                        "userId": 1
                    }
                    Response : 
                        Example 1 : 
                            [
                                {
                                    "incomeId": 1,
                                    "userId": 1,
                                    "userName: "John Doe",
                                    "amount": 100,
                                    "description": "Salary",
                                    "incomeDate": "2023-09-15T12:00:00"
                                }
                            ]
                3. POST /create
                    Description : 
                        Create new income
                    Request Body : 
                        Example 1 : 
                            [
                                {
                                    "userId": 1,
                                    "amount": 100,
                                    "description": "Salary",
                                    "incomeDate": "2023-09-15T12:00:00"
                                }
                            ]
                    Response : 
                        Example 1 : 200 OK
    Controller Name : ExpendController
        Request Mapping : /expends
            Functions : 
                1. GET /all
                    Description : Get all expends
                    Request Body : None
                    Response : 
                        Example 1 : 
                            [
                                {
                                    "expendId": 1,
                                    "userId": 1,
                                    "amount": 100,
                                    "description": "Salary",
                                    "expendDate": "2023-09-15T12:00:00"
                                }
                            ]
                2. GET /byUserId
                    Description : Get all expends by user id
                    Request Body : {
                        "userId": 1
                    }
                    Response : 
                        Example 1 : 
                            [
                                {
                                    "expendId": 1,
                                    "userId": 1,
                                    "userName: "John Doe",
                                    "amount": 100,
                                    "description": "Salary",
                                    "expendDate": "2023-09-15T12:00:00"
                                }
                            ]
                3. POST /create
                    Description : Create new expend
                    Request Body :
                        Example 1 :
                            [
                                {
                                    "userId": 1,
                                    "amount": 100,
                                    "description": "Salary",
                                    "expendDate": "2023-09-15T12:00:00"
                                }
                            ]
                    Response :
                        Example 1 : 200 OK