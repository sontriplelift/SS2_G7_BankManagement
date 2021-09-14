package software;

import domainapp.basics.exceptions.DataSourceException;
import domainapp.software.SoftwareFactory;
import domainapp.softwareimpl.DomSoftware;
import services.person.model.customer.Customer;
import services.transactions.model.Transactions;

/**
 * @overview 
 *
 * @author Duc Minh Le (ducmle)
 *
 * @version 
 */
public class DomMainUtilities {
  
  public static void main(String[] args) {
    DomSoftware sw = SoftwareFactory.createDefaultDomSoftware();
    
    // this should be run subsequent times
    sw.init();
    
    try {
      // print materialised domain model
      printMaterialisedDomainModel(sw);
      
      // delete a domain class
//      deleteClass(sw, Student.class);
      
 //      delete the domain model fragment
//      deleteDomainModel(sw, Transactions.class);
      
      // delete multiple domain model fragments 
      deleteDomainModel(sw, Main.model);

//      printMaterialisedDomainModel(sw);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * @effects 
   * 
   * @version 
   * 
   */
  private static void printMaterialisedDomainModel(DomSoftware sw) {
    String modelName = sw.getDomainModelName(Customer.class);
    if (modelName != null) {
      sw.printMaterialisedDomainModel(modelName);
    }
  }

  /**
   * @effects 
   * 
   * @version 
   * 
   */
  private static void deleteDomainModel(DomSoftware sw, Class c) {
    String modelName = sw.getDomainModelName(c);
    if (modelName != null) {
      try {
        sw.deleteDomainModel(modelName);
      } catch (DataSourceException e) {
        e.printStackTrace();
      }
    }
  }

  private static void deleteDomainModel(DomSoftware sw, Class... classes) {
    try {
      sw.deleteDomainModel(classes);
    } catch (DataSourceException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * @effects 
   * 
   * @version 
   * @param sw 
   * 
   */
  private static void deleteClass(DomSoftware sw, Class c) throws DataSourceException {
    boolean isReg = sw.isRegistered(c);
    boolean isMat = sw.isMaterialised(c);
    System.out.printf("%s%n  isRegistered: %b%n  isMaterialised: %b%n", 
        c.getSimpleName(), isReg, isMat);
    if (isMat) {
      Class[] toDelete = {c};
      System.out.printf("...unregistering/deleting%n");
      sw.deleteDomainModel(toDelete);
      isReg = sw.isRegistered(c);
      isMat = sw.isMaterialised(c);
      System.out.printf("  isRegistered: %b%n  isMaterialised: %b%n", isReg, isMat);
    }    
  }
}
