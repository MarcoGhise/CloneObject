package it.blog.CloneObject.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import it.blog.CloneObject.bean.Basket;
import it.blog.CloneObject.business.BLBasket;
import it.blog.CloneObject.business.JsonSerializator;
import it.blog.CloneObject.memento.BasketOriginator;
import it.blog.CloneObject.memento.BasketOriginator.Memento;
import lombok.extern.slf4j.Slf4j;

@RestController
public class BasketRest {

	private final BLBasket blBasket;

	private final JsonSerializator serializator;

	private BasketOriginator originator;

	@Autowired
	public BasketRest(BLBasket blBasket, JsonSerializator serializator, BasketOriginator originator) {
		this.blBasket = blBasket;
		this.serializator = serializator;
		this.originator = originator;
	}

	@GetMapping(value = "/basket", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Basket> getBasket(HttpServletRequest request, HttpServletResponse response) {
		return ResponseEntity.ok(blBasket.getNewBasket());
	}

	@PostMapping(value = "/basket:clone", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Basket[]> cloneBasket(HttpServletRequest request, HttpServletResponse response)
			throws CloneNotSupportedException {
		/*
		 * Get current Basket
		 */
		Basket currentBasket = blBasket.getNewBasket();
		/*
		 * Clone it
		 */
		Basket cloneBasket = (Basket) currentBasket.clone();
		/*
		 * Change product from clone basket
		 */
		cloneBasket.getProducts().get(0).setName("New Apple");

		cloneBasket.setUuid("1");

		return ResponseEntity.ok(new Basket[] { currentBasket, cloneBasket });
	}

	@PostMapping(value = "/basket:serialize", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Basket[]> cloneSerializeBasket(HttpServletRequest request, HttpServletResponse response) {
		/*
		 * Get current Basket
		 */
		Basket currentBasket = blBasket.getNewBasket();
		/*
		 * Clone it
		 */
		Basket cloneBasket = blBasket.cloneSerializable(currentBasket);
		/*
		 * Change product from clone basket
		 */
		cloneBasket.getProducts().get(0).setName("New Apple");

		cloneBasket.setUuid("1");

		return ResponseEntity.ok(new Basket[] { currentBasket, cloneBasket });
	}

	@PostMapping(value = "/basket:memento", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Basket[]> mementoBasket(HttpServletRequest request, HttpServletResponse response) {
		/*
		 * Set current state
		 */
		originator.setOriginatorState(blBasket.getNewBasket());
		/*
		 * Create Memento
		 */
		Memento step1 = originator.createMemento();
		/*
		 * Add wrong product
		 */
		originator.getOriginatorState().getProducts().add(blBasket.getWrongProduct());

		String originalBasket = serializator.serializeObject(originator.getOriginatorState());
		/*
		 * Rollback
		 */
		originator.restoreMemento(step1);

		String rollbackBasket = serializator.serializeObject(originator.getOriginatorState());

		return ResponseEntity.ok(new Basket[] { serializator.deSerializeObject(originalBasket, Basket.class),
				serializator.deSerializeObject(rollbackBasket, Basket.class) });

	}
}
