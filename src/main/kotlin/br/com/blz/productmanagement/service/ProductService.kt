package br.com.blz.productmanagement.service

import br.com.blz.productmanagement.dto.NewProductDTO
import br.com.blz.productmanagement.model.Inventory
import br.com.blz.productmanagement.model.Product
import br.com.blz.productmanagement.model.Warehouse
import org.springframework.stereotype.Service

@Service
class ProductService(
  private val products: MutableList<Product> = mutableListOf()
) {

  fun listBySku(sku: Int): Product {
    val quantity = 0
    val returnedProducts = products.firstOrNull { it.sku == sku } ?: throw Exception("No product found with the passed Sku.")
    returnedProducts?.inventory?.warehouses?.forEach { warehouse -> quantity.plus(warehouse.quantity) }
    returnedProducts?.isMarketable = (returnedProducts?.inventory?.quantity ?: 0) > 0
    return returnedProducts
  }

  fun create(newProductDTO: NewProductDTO): Product {
    val product = mapToProduct(newProductDTO)
    products.add(product)
    return product
  }

  fun editBySku(sku: Int, newProductDTO: NewProductDTO): Product {
    if(sku != newProductDTO.sku) throw Exception("Sku does not match the sku passed on body")
    val newProduct = mapToProduct(newProductDTO)
    val index = products.indexOfFirst { it.sku == sku }

    if (index == -1)
      throw Exception("No sku found.")

    products[index] = newProduct
    return newProduct
  }

  fun delete(sku: Int): String{
    products.removeIf { it.sku == sku }
    return "Product $sku deleted succesfully"
  }

  fun mapToProduct(newProductDTO: NewProductDTO): Product {
    val inventory = Inventory(
      sumQuantity(newProductDTO),
      newProductDTO.inventory.warehouses.map { warehouseDTO ->
        warehouseDTO.quantity
        Warehouse(
          warehouseDTO.locality,
          warehouseDTO.quantity,
          warehouseDTO.type
        )
      }
    )
    return Product(newProductDTO.sku, newProductDTO.name, inventory, inventory.quantity > 0)
  }

  fun sumQuantity(newProductDTO: NewProductDTO): Int {
    var quantity = 0
    newProductDTO.inventory.warehouses.forEach {
      quantity += it.quantity
    }
    return quantity
  }

}
