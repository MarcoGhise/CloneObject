package it.blog.CloneObject.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.blog.CloneObject.bean.Basket;
import it.blog.CloneObject.bean.Product;

import java.util.ArrayList;
import java.util.List;

@Component
public class BLBasket {

	private final JsonSerializator serializator;

	@Autowired
	public BLBasket(JsonSerializator serializator) {
		this.serializator = serializator;
	}

	/*
	 * Create a new Basket
	 */
	public Basket getNewBasket() {
		Product p1 = new Product();
		p1.setName("Apple");
		p1.setCode("APP");
		p1.setQty(10);
		p1.setPrice(2.30);

		Product p2 = new Product();
		p2.setName("Peach");
		p2.setCode("PCH");
		p2.setQty(5);
		p2.setPrice(5.60);
		
		List<Product> products = new ArrayList<>();
		products.add(p1);
		products.add(p2);
		
		return new Basket(products);
	}

	public Basket cloneSerializable(Basket basket) {
		return serializator.deSerializeObject(serializator.serializeObject(basket), Basket.class);
	}
	
	/*
	 * Get the wrong product
	 */
	public Product getWrongProduct() {
		Product p1 = new Product();
		p1.setName("Potato");
		p1.setCode("POT");
		p1.setQty(10);
		p1.setPrice(4.20);

		return p1;
	}
}
