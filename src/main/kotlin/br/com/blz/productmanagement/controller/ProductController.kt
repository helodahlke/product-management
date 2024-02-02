package br.com.blz.productmanagement.controller

import br.com.blz.productmanagement.dto.NewProductDTO
import br.com.blz.productmanagement.model.Product
import br.com.blz.productmanagement.service.ProductService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/products")
class ProductController(private val service: ProductService) {

  @GetMapping("/{sku}")
  fun listBySku(@PathVariable sku: Int): Product? {
    return service.listBySku(sku)
  }

  @PostMapping
  fun create(@RequestBody productDto: NewProductDTO): Product {
    return service.create(productDto)
  }

  @PatchMapping("/{sku}")
  fun editBySku(@PathVariable sku: Int, @RequestBody newProductDto: NewProductDTO): Product{
    return service.editBySku(sku, newProductDto)
  }

  @DeleteMapping("/{sku}")
  fun deleteBySku(@PathVariable sku: Int): String{
    return service.delete(sku)
  }

}
