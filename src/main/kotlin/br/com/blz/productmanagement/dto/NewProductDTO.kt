package br.com.blz.productmanagement.dto

import br.com.blz.productmanagement.model.Inventory

data class ProductDto (
  val sku: Int,
  val name: String,
  val inventory: Inventory
)
