package br.com.blz.productmanagement.service

import br.com.blz.productmanagement.dto.NewProductDTO
import br.com.blz.productmanagement.model.Inventory
import br.com.blz.productmanagement.model.Product
import br.com.blz.productmanagement.model.Warehouse
import br.com.blz.productmanagement.toModel
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
    val product = newProductDTO.toModel()
    products.add(product)
    return product
  }

  fun editBySku(sku: Int, newProductDTO: NewProductDTO): Product {

    if(sku != newProductDTO.sku) throw Exception("Sku does not match the sku passed on body")

    val newProduct = newProductDTO.toModel()
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

}
