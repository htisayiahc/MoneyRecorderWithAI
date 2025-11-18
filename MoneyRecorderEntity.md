This file is used to generate Entity for using in Spring Boot project.
Entity base on JPA dependency.

In ./MoneyRecorderAIver/src/main/java/com/example/MoneyRecorderAIver/Entity we keep all the Entity.

Entity list :
    UserEntity :
        id : Long
        name : String
        email : String
        password : String
        createdAt : Date
        updatedAt : Date
    IncomeEntity :
        id : Long
        userId : Long
            description : User who create this income
            detail : This income table use userId as a foreign key to link to UserEntity
        amount : Double
        description : String
        incomeDate : Date
        createdAt : Date
        updatedAt : Date
    ExpenseEntity :
        id : Long
        userId : Long
            description : User who create this expend
            detail : This expend table use userId as a foreign key to link to UserEntity
        amount : Double
        description : String
        expendDate : Date
        createdAt : Date
        updatedAt : Date
