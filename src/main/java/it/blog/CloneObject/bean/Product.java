package it.blog.CloneObject.bean;

import java.io.Serializable;

public class Product implements Serializable, Cloneable {

	public String name;
	public String code;
	public int qty;
	public double price;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Product [name=" + name + ", code=" + code + ", qty=" + qty + ", price=" + price + "]";
	}

	public Object clone() {

		try {
			return (Product) super.clone();
		} catch (CloneNotSupportedException e) {

			return this;
		}

	}

}
