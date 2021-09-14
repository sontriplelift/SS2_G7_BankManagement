package services.account.model;

import domainapp.basics.model.meta.DAttr;
import domainapp.basics.model.meta.DAttr.Type;


public enum TermOfSaving { 
	
	Indefinite,
	OneMonth,
	TwoMonth,
	ThreeMonth,
	FourMonth,
	FiveMonth,
	SixMonth,
	NineMonth,
	TwelveMonth,
	FifteenMonth,
	EighteenMonth,
	TwentyFourMonth,
	ThirtySixMonth;
	
	  @DAttr(name="name", type=Type.String, id=true, length=20)
	  public String getName() {
	    return name();
	  }

}
