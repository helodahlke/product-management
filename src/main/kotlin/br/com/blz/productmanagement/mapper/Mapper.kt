package br.com.blz.productmanagement

import br.com.blz.productmanagement.dto.NewProductDTO
import br.com.blz.productmanagement.model.Inventory
import br.com.blz.productmanagement.model.Product
import br.com.blz.productmanagement.model.Warehouse

fun NewProductDTO.toModel(): Product {
  val inventory = Inventory(
    sumQuantity(this),
    this.inventory.warehouses.map { warehouseDTO -> warehouseDTO.quantity
      Warehouse(
        warehouseDTO.locality,
        warehouseDTO.quantity,
        warehouseDTO.type
      )
    }
  )
  return Product(
    sku = this.sku,
    name = this.name,
    inventory = inventory,
    isMarketable = inventory.quantity > 0
  )
}

fun sumQuantity(newProductDTO: NewProductDTO): Int {
  var quantity = 0
  newProductDTO.inventory.warehouses.forEach {
    quantity += it.quantity
  }
  return quantity
}
