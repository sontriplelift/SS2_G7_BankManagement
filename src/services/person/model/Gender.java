package services.person.model;

import domainapp.basics.model.meta.DAttr;
import domainapp.basics.model.meta.DAttr.Type;

/**
 * @overview Represents the gender of a person.
 *
 * @author Duc Minh Le (ducmle)
 */
public enum Gender {
  Male,
  Female,
  Other
  ;
  
  @DAttr(name="name", type=Type.String, id=true, length=10)
  public String getName() {
    return name();
  }
}
