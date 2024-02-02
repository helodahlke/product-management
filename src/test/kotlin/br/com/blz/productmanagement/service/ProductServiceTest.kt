package br.com.blz.productmanagement.service

import br.com.blz.productmanagement.dto.NewInventoryDTO
import br.com.blz.productmanagement.dto.NewProductDTO
import br.com.blz.productmanagement.dto.NewWarehouseDTO
import br.com.blz.productmanagement.model.WarehouseType
import io.kotest.assertions.throwables.shouldThrowWithMessage
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class ProductServiceTest : ShouldSpec({

  var productService = ProductService()

  beforeEach {
    productService = ProductService(mutableListOf())
  }

  should("create a new product") {
    val newProduct = createNewProductDTO(12)

    val result = productService.create(newProduct)

    with(result) {
      sku shouldBe 12345
      name shouldBe "Test Product"
      isMarketable shouldBe true
      result.inventory.quantity shouldBe 12
      inventory.warehouses.forEach {
        it.locality shouldBe "Brasil"
        it.type shouldBe WarehouseType.PHYSICAL_STORE
        it.quantity shouldBe 12
      }
    }
  }

  should("return existing products by SKU") {
    val newProduct = createNewProductDTO(12)

    productService.create(newProduct)
    val result = productService.listBySku(12345)

    with(result) {
      sku shouldBe 12345
      name shouldBe "Test Product"
      isMarketable shouldBe true
      result.inventory.quantity shouldBe 12
      inventory.warehouses.forEach {
        it.locality shouldBe "Brasil"
        it.type shouldBe WarehouseType.PHYSICAL_STORE
        it.quantity shouldBe 12
      }
    }
  }

  should("return marketable false") {
    val newProduct = createNewProductDTO(0)

    productService.create(newProduct)
    val result = productService.listBySku(12345)

    with(result) {
      isMarketable shouldBe false
      inventory.quantity shouldBe 0
    }
  }

  should("delete existing product by sku") {
    val newProduct = createNewProductDTO(0)

    productService.create(newProduct)
    val deleteMessage = productService.delete(12345)

    deleteMessage shouldBe "Product 12345 deleted succesfully"
  }

  should("throw no sku found exception") {
    val result: () -> Unit = { productService.listBySku(12345) }

    shouldThrowWithMessage<Exception>("No product found with the passed Sku.", result)
  }

  should("throw Sku does not match the sku passed on body") {
    val result: () -> Unit = { productService.editBySku(11, createNewProductDTO(1)) }

    shouldThrowWithMessage<Exception>("Sku does not match the sku passed on body", result)
  }

  should("throw The sku can only be created once.") {

    productService.create(createNewProductDTO(2))
    val result: () -> Unit = { productService.create(createNewProductDTO(1)) }

    shouldThrowWithMessage<Exception>("The sku can only be created once.", result)
  }

})

private fun createNewProductDTO(quantity: Int): NewProductDTO {
  val newWarehouse = NewWarehouseDTO(
    "Brasil",
    WarehouseType.PHYSICAL_STORE,
    quantity
  )

  val warehouses = listOf(newWarehouse)
  val inventory = NewInventoryDTO(warehouses)

  return NewProductDTO(sku = 12345, name = "Test Product", inventory)
}
