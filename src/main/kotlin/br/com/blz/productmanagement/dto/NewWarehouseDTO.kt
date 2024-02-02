package br.com.blz.productmanagement.dto

import br.com.blz.productmanagement.model.WarehouseType

data class NewWarehouseDTO (
  val locality: String,
  val type: WarehouseType,
  val quantity: Int
)
