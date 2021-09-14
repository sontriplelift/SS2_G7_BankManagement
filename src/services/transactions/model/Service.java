package services.transactions.model;

import domainapp.basics.model.meta.DAttr;
import domainapp.basics.model.meta.DAttr.Type;

public enum Service { 
	PayConsumerGoods, 
	BookMovieTicket,
	BookFlightTicket, 
	BookHotelRoom,
	PayInsurrance;
	
	  
	  @DAttr(name="name", type=Type.String, id=true, length=20)
	  public String getName() {
	    return name();
	  }

}
