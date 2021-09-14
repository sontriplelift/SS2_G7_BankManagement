package software;

import services.person.model.employee.Employee;
import services.person.model.employee.Teller;
import domainapp.basics.exceptions.DataSourceException;
import domainapp.basics.exceptions.NotFoundException;
import domainapp.software.SoftwareFactory;
import domainapp.softwareimpl.DomSoftware;

public class DomMainTeller {

	public static void main(String[] args) {
		    DomSoftware sw = SoftwareFactory.createDefaultDomSoftware();
		    
		    // this should be run subsequent times
		    sw.init();
		    try {
		    	sw.addClass(Employee.class);
//				Teller teller = sw.retrieveObjectById(Teller.class, 1);
		    	Employee employee = sw.retrieveObjectById(Employee.class, "EMP0001");
		    	System.out.println(employee);
			} catch (NotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (DataSourceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		    
		    
	}
	
	 
}
