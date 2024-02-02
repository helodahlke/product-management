package br.com.blz.productmanagement.model

data class Warehouse(
  val locality: String,
  val quantity: Int,
  val type: WarehouseType
)
