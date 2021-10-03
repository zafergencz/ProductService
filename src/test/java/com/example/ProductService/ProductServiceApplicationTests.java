package com.example.ProductService;

import com.example.ProductService.model.Product;
import com.example.ProductService.repository.ProductRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


@SpringBootTest
class ProductServiceApplicationTests {

	@Mock
	private ProductRepository productRepository;

	@InjectMocks
	private ProductController productController = new ProductController();

	public ArrayList<Product> productList;
	public Product sampleProduct;

	ProductServiceApplicationTests(){
		sampleProduct = sampleProductEntity();
		productList = new ArrayList<>();
		productList.add(sampleProduct);
		productList.add(new Product("fanta","this an orange cola", "drink", "orange", new Date(), new Date() ));
	}

	@BeforeEach
	void setMockOutput() {
		when(productRepository.findById(5L)).thenReturn(Optional.of(sampleProduct));
		when(productRepository.findByType("telephone")).thenReturn(productList);
		when(productRepository.findByColor("red")).thenReturn(productList);
		when(productRepository.findByColorAndType("red", "telephone")).thenReturn(productList);
		when(productRepository.findAll()).thenReturn(productList);
		when(productRepository.save(sampleProduct)).thenReturn(sampleProduct);
	}

	public Product sampleProductEntity(){
		Product p = new Product();
		p.setName("phone");
		p.setType("telephone");
		p.setColor("red");
		p.setDetail("this is a red telephone");
		p.setCreateDate(new Date());
		p.setUpdateDate(new Date());
		return p;
	}

	@Test
	void testGetProductById() {
		assertEquals("phone", productController.getProductById(5L).getBody().getName());
	}

	@Test
	void testGetProductByType() {
		assertEquals("phone", productController.getProducts("telephone",null).getBody().get(0).getName());
	}

	@Test
	void testGetProductByColor() {
		assertEquals("phone", productController.getProducts(null,"red").getBody().get(0).getName());
	}

	@Test
	void testGetProductByColorAndType() {
		assertEquals("phone", productController.getProducts("telephone","red").getBody().get(0).getName());
	}

	@Test
	void testGetAllProducts() {

        assertThat(productController.getProducts(null, null).getBody().size()).isGreaterThan(0);
	}

	@Test
	void testCreateProduct(){
		assertEquals(201,productController.createProduct(sampleProduct).getStatusCode().value());
	}

	@Test
	void testUpdateProduct(){
		assertEquals(200,productController.updateProduct(sampleProduct, 5L).getStatusCode().value());
	}

	@Test
	void testDeleteProduct(){
		assertEquals(204,productController.deleteProduct(5L).getStatusCode().value());
	}

	@Test
	void testDeleteAll(){
		assertEquals(204,productController.deleteAllProducts().getStatusCode().value());
	}

}
