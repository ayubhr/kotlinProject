package com.example.ecommerce.Model

class modelProducts(
    private var pImg: String,
    private var pName: String,
    private var pPrice: String
) {
    fun getpImg(): String {
        return pImg
    }

    fun setpImg(pImg: String) {
        this.pImg = pImg
    }

    fun getpName(): String {
        return pName
    }

    fun setpName(pName: String) {
        this.pName = pName
    }

    fun getpPrice(): String {
        return pPrice
    }

    fun setpPrice(pPrice: String) {
        this.pPrice = pPrice
    }
}