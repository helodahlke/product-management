package br.com.blz.productmanagement.dto

data class NewProductDTO (
  val sku: Int,
  val name: String,
  val inventory: NewInventoryDTO
)
