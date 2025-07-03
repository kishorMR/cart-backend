package com.tcs.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.tcs.product.entity.Product;
import com.tcs.product.entity.ProductImage;
import com.tcs.product.exception.ImageFormatException;
import com.tcs.product.exception.NoProductsFoundException;
import com.tcs.product.repository.ProductRepository;
import com.tcs.product.repository.ProductsImagesRepository;

@Service
public class ProductService {
	
	@Autowired
	ProductRepository productRepoitory;
	
	@Autowired
	ProductsImagesRepository productImageRepo;
	
	public Product addNewProduct(Product product) {
		return productRepoitory.save(product);
	}
	
	public String updateProduct(Long id, Product product, String imageUrl, int imgId) throws ImageFormatException {
		product.setProductId(id);
		List<ProductImage> piList = productImageRepo.findByProductProductId(product.getProductId());
		
		if(!piList.isEmpty() && imgId<piList.size()) {			
			piList.get(imgId).setProduct(product);
			
			if(!(imageUrl.contains(".jpg") || imageUrl.contains(".png"))) {
				System.out.println("Not uploaded..Only jpg and png format Allowed..");
				throw new ImageFormatException("Only jpg and png format Allowed..");
			}
			piList.get(imgId).setUrl(imageUrl);
		}
		else if(piList.isEmpty() && imgId>=0){ //first image, if imgId<0 -> then image not added.
			ProductImage newProductImage = new ProductImage(); //new Object -> first image
			newProductImage.setProduct(product);
			
			if(!(imageUrl.contains(".jpg") || imageUrl.contains(".png"))) {
				System.out.println("Not uploaded..Only jpg and png format Allowed..");
				throw new ImageFormatException("Only jpg and png format Allowed..");
			}
			newProductImage.setUrl(imageUrl);
			
			piList.add(newProductImage);
			
//			productImageRepo.save(newProductImage);
		}
		product.setProductImageList(piList);
		
		if(productRepoitory.findById(id).isPresent()) {	
			productRepoitory.save(product);
			if(!piList.isEmpty()) productImageRepo.saveAll(piList);
			return "Update Sucess";
		}
		else {	
			throw new NoProductsFoundException();
		}
	}
	
	
	public Page<Product> getAllProducts(int page, int size) {
		return productRepoitory.findAll(PageRequest.of(page,size));
	}
	
	public List<Product> getAllProductsByName(String name){
		if(productRepoitory.findByProductNameContainingIgnoreCase(name).isEmpty())
			throw new NoProductsFoundException();
		else return productRepoitory.findByProductNameContainingIgnoreCase(name);
	}
	
	public String deleteProduct(Long id) {
		if(productRepoitory.findById(id).isPresent()) {
			productRepoitory.deleteById(id);
			return "The Item with ID: "+id+" is deleted!!";
		}else throw new NoProductsFoundException();
	}

	public List<Product> getProductByCategories(String category) {
		return productRepoitory.findByCategoryContainingIgnoreCase(category);
	}

	public Optional<Product> getProductById(Long id) {
		Optional<Product> product = productRepoitory.findById(id);
		if(product.isEmpty()) {
			throw new NoProductsFoundException();
		}
		
		return product;
	}

	public void uploadProductImages(Long id, String url) throws ImageFormatException {
		if(!(url.contains(".jpg") || url.contains(".png"))) {
			System.out.println("Not uploaded..Only jpg and png format Allowed..");
			throw new ImageFormatException("Only jpg and png format Allowed..");
		}
		Optional<Product> product = productRepoitory.findById(id);
		if(product.isEmpty()) {
			System.out.println("Not uploaded..1");
			return;
		}
			
		List<ProductImage> productImageList = productImageRepo.findByProductProductId(product.get().getProductId());
		if(productImageList.isEmpty()) { //not present in ProductImage
			ProductImage newProductImage = new ProductImage(); //new Object
			newProductImage.setProduct(product.get());
			newProductImage.setUrl(url);
			
			productImageRepo.save(newProductImage);
		}
		else {
			ProductImage newProductImage = new ProductImage(); //new Object
			newProductImage.setProduct(product.get());
			newProductImage.setUrl(url);
			
			productImageList.add(newProductImage);
			
			productImageRepo.save(newProductImage);
		}
	}
	
	public List<ProductImage> getProductImageById(Long id) {
		Optional<Product> product = productRepoitory.findById(id);
		if(product.isEmpty()) {
			throw new NoProductsFoundException();
		}
		
		List<ProductImage> productImages = productImageRepo.findByProductProductId(product.get().getProductId());
		
		return productImages;
	}
}
