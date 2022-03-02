package it.blog.CloneObject.bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class Basket implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Basket() {
	}

	public Basket(List<Product> products) {
		this.date = LocalDateTime.now();
		this.uuid = UUID.randomUUID().toString();
		this.products = products;
	}

	protected LocalDateTime date;
	protected String uuid;
	protected List<Product> products;

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Object clone() throws CloneNotSupportedException {

		Basket clone = (Basket) super.clone();
		clone.setProducts(this.products.stream().map(p -> (Product) p.clone()).collect(Collectors.toList()));
		// clone.setProducts(clone.products.clone());
		// clone.setProducts(Arrays.stream(this.products).toArray(Product[]::new));
		return clone;

	}

	@Override
	public String toString() {
		return "Basket [date=" + date + ", uuid=" + uuid + ", products=" + products + "]";
	}
}
