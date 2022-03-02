package it.blog.CloneObject.memento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.blog.CloneObject.bean.Basket;
import it.blog.CloneObject.business.JsonSerializator;

@Component
public class BasketOriginator {

	private Basket originatorState;
	private JsonSerializator serializer;
	
	@Autowired
	public BasketOriginator(JsonSerializator serializer)
	{
		this.serializer = serializer;
	}
	 
  public void setOriginatorState(Basket state) {
      originatorState = state;
  }

  public Basket getOriginatorState() {
      return originatorState;
  }

  public Memento createMemento() {
      Memento memento = new Memento();
      memento.setMementoState( serializer.deSerializeObject(serializer.serializeObject(originatorState), Basket.class));
      return memento;
  }

  public void restoreMemento(Memento memento) {
      originatorState = serializer.deSerializeObject(serializer.serializeObject(memento.getMementoState()), Basket.class);
  }

  public class Memento {

      private Basket mementoState;

      private Basket getMementoState() {
          return this.mementoState;
      }

      private void setMementoState(Basket originatorState) {
          this.mementoState = originatorState;
      }
  }
}
