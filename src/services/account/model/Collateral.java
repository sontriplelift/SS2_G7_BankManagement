package services.account.model;

import domainapp.basics.model.meta.DAttr;
import domainapp.basics.model.meta.DAttr.Type;

public enum Collateral { 
	None,
	Movable,
	Imovable;
	
	  
	  @DAttr(name="name", type=Type.String, id=true, length=20)
	  public String getName() {
	    return name();
	  }

}