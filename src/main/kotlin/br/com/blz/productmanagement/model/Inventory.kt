package br.com.blz.productmanagement.model

data class Inventory(
  val quantity: Int,
  val warehouses: List<Warehouse>
)
