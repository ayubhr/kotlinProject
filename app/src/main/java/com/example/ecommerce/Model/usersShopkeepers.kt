package com.example.ecommerce.Model

class usersShopkeepers {
    var username: String? = null
    var phone: String? = null
    var password: String? = null
    var shopID: String? = null
    var shopname: String? = null

    constructor() {}
    constructor(
        username: String?,
        phone: String?,
        password: String?,
        shopID: String?,
        shopname: String?
    ) {
        this.username = username
        this.phone = phone
        this.password = password
        this.shopID = shopID
        this.shopname = shopname
    }
}