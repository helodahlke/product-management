package br.com.blz.productmanagement.model

data class Product (
  val sku: Int,
  val name: String,
  val inventory: Inventory,
  var isMarketable: Boolean
)
