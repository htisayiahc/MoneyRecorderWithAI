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
                    Description : 
                        - Get all users 
                        - find all users in redis if not found in redis then find all users in database and save to redis
                            - key in redis : users:all
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
                    Description :
                        - Get user by id
                        - find user in redis if not found in redis then find user in database and save to redis
                            - key in redis : users:{userId}
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
                    Description : 
                        - Create new user
                        - save user to database
                        - save user to redis
                            - key in redis : users:{userId}
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
                    Description : 
                        - Get all incomes
                        - find all incomes in redis if not found in redis then find all incomes in database and save to redis
                            - key in redis : incomes:all
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
                    Description :
                        - Get all incomes by user id
                        - find all incomes by user id in redis if not found in redis then find all incomes by user id in database and save to redis
                            - key in redis : incomes:{userId}
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
                        - Create new income
                        - save income to database
                        - save income to redis
                            - key in redis : incomes:{userId}
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
                4. GET /total
                    Description : 
                        - Get total income by user id
                        - If userId or startDate or endDate is null then throw exception 400 error
                        - Get incomes in redis if not found in redis then find incomes in database and save to redis
                            - key in redis : incomes:{userId}
                        - use startDate and endDate to filter incomes
                        - sum from filtered incomes
                    Request Body : 
                        Example 1 : 
                            {
                                "userId": 1,
                                "startDate": "2025-01-01T00:00:00"
                                "endDate": "2025-01-31T23:59:59"
                            }
                    Response : 
                        Example 1 : 
                            {
                                "startDate": "2025-01-01T00:00:00"
                                "endDate": "2025-01-31T23:59:59"
                                "totalIncome": 100.00
                            }
    Controller Name : ExpendController
        Request Mapping : /expends
            Functions : 
                1. GET /all
                    Description 
                        - Get all expends
                        - find all expends in redis if not found in redis then find all expends in database and save to redis
                            - key in redis : expends:all
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
                4. GET /total
                    Description : 
                        - Get total expend by user id
                        - If userId or startDate or endDate is null then throw exception 400 error
                        - Get expends in redis if not found in redis then find expends in database and save to redis
                            - key in redis : expends:{userId}
                        - use startDate and endDate to filter expends
                        - sum from filtered expends
                    Request Body : 
                        Example 1 : 
                            {
                                "userId": 1,
                                "startDate": "2025-01-01T00:00:00"
                                "endDate": "2025-01-31T23:59:59"
                            }
                    Response : 
                        Example 1 : 
                            {
                                "startDate": "2025-01-01T00:00:00"
                                "endDate": "2025-01-31T23:59:59"
                                "totalExpend": 100.00
                            }