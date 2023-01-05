package com.example.ecommerce.Model

class usersCustomers {
    var name: String? = null
    var phone: String? = null
    var password: String? = null
    var email: String? = null

    constructor() {}
    constructor(name: String?, phone: String?, password: String?, email: String?) {
        this.name = name
        this.phone = phone
        this.password = password
        this.email = email
    }
}