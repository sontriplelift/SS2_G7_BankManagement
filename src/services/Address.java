//package services.person;
//
//import java.util.ArrayList;
//import java.util.Collection;
//
//import domainapp.basics.exceptions.ConstraintViolationException;
//import domainapp.basics.model.meta.AttrRef;
//import domainapp.basics.model.meta.DAssoc;
//import domainapp.basics.model.meta.DAssoc.AssocEndType;
//import domainapp.basics.model.meta.DAssoc.AssocType;
//import domainapp.basics.model.meta.DAssoc.Associate;
//import domainapp.basics.model.meta.DAttr;
//import domainapp.basics.model.meta.DAttr.Type;
//import domainapp.basics.model.meta.DClass;
//import domainapp.basics.model.meta.DOpt;
//import domainapp.basics.model.meta.MetaConstants;
//import domainapp.basics.model.meta.Select;
//import domainapp.basics.util.Tuple;
//import services.person.customer.Customer;
//import services.branch.Branch;
//import services.person.employee.Employee;
//
///**
// * A domain class whose objects are address names. This class is used as the
// * <code>allowedValues</code> of the domain attributes of other domain classes
// * (e.g. Customer.address).
// * 
// * <p>
// * Method <code>toString</code> overrides <code>Object.toString</code> to return
// * the string representation of a address name which is expected by the
// * application.
// * 
// * @author dmle
// *
// */
//@DClass(schema = "banksystem")
//public class Address {
//
//	public static final String A_city = "city";
//	public static final String A_district = "district";
//	public static final String A_ward = "ward";
//
//	@DAttr(name = "id", id = true, auto = true, length = 6, mutable = false, optional = false, type = Type.Integer)
//	private int id;
//	private static int idCounter;
//
//	@DAttr(name = A_city, type = Type.String, length = 20, optional = false)
//	private String city;
//
//	@DAttr(name = A_district, type = Type.String, length = 20, optional = false)
//	private String district;
//
//	@DAttr(name = A_ward, type = Type.String, length = 20, optional = false)
//	private String ward;
//
//	@DAttr(name = "branch", type = Type.Domain, optional = true, serialisable = false)
//	@DAssoc(ascName = "branch-has-address", role = "address", ascType = AssocType.One2One, endType = AssocEndType.One, associate = @Associate(type = Branch.class, cardMin = 1, cardMax = 1))
//	private Branch branch;
//
//	@DAttr(name = "customers", type = Type.Collection, serialisable = false, optional = false, filter = @Select(clazz = Customer.class))
//	@DAssoc(ascName = "customer-has-address", role = "address", ascType = AssocType.One2Many, endType = AssocEndType.One, associate = @Associate(type = Customer.class, cardMin = 1, cardMax = 1000))
//	private Collection<Customer> customers;
//
//	// derived attributes
//	private int customersCount;
////
////	@DAttr(name = "employee", type = Type.Domain, serialisable = false)
////	@DAssoc(ascName = "employee-has-address", role = "address", ascType = AssocType.One2Many, endType = AssocEndType.One, associate = @Associate(type = Employee.class, cardMin = 1, cardMax = MetaConstants.CARD_MORE))
////	private Employee employee;
//
//	// from object form: Customer is not included
//	@DOpt(type = DOpt.Type.ObjectFormConstructor)
//	@DOpt(type = DOpt.Type.RequiredConstructor)
//	public Address(@AttrRef("city") String city, @AttrRef("district") String district, @AttrRef("ward") String ward) {
//		this(null, city, district, ward, null);
//	}
//
//	// from object form: Customer is included
//	@DOpt(type = DOpt.Type.ObjectFormConstructor)
//	public Address( @AttrRef("city") String city, @AttrRef("district") String district,
//			@AttrRef("ward") String ward, @AttrRef("branch") Branch branch) {
//		this(null, city, district, ward, branch);
//	}
//
//	// from data source
//	@DOpt(type = DOpt.Type.DataSourceConstructor)
//	public Address(@AttrRef("id") Integer id, @AttrRef("city") String city, @AttrRef("district") String district,
//			@AttrRef("ward") String ward, @AttrRef("branch") Branch branch) {
//		this.id = nextId(id);
//		this.city = city;
//		this.district = district;
//		this.ward = ward;
//		this.branch = branch;
////
//		customers = new ArrayList<>();
//		customersCount = 0;
//	}
//
//	// based constructor (used by others)
////  private Address(Integer id, String city, String district, String ward, Customer customer) {
////    this.id = nextId(id);
////    this.district = district;
////    this.ward = ward;
////    this.customer = customer;
////  }
//	
//
//
//	public int getId() {
//		return id;
//	}
//
//	public String getCity() {
//		return city;
//	}
//
//	public void setCity(String city) {
//		this.city = city;
//	}
//
//	public String getDistrict() {
//		return district;
//	}
//
//	public void setDistrict(String district) {
//		this.district = district;
//	}
//
//	public String getWard() {
//		return ward;
//	}
//
//	public void setWard(String ward) {
//		this.ward = ward;
//	}
//
//	public Branch getBranch() {
//		return branch;
//	}
//
//	@DOpt(type = DOpt.Type.LinkAdderNew)
//	public void setNewBranch(Branch branch) {
//		this.branch = branch;
//		// do other updates here (if needed)
//		// update!!
//	}
//
//	public void setBranch(Branch branch) {
//		this.branch = branch;
//	}
//
//	@DOpt(type = DOpt.Type.LinkAdder)
//	// only need to do this for reflexive association: @MemberRef(name="customers")
//	public boolean addCustomer(Customer s) {
//		if (!this.customers.contains(s)) {
//			customers.add(s);
//		}
//
//		// no other attributes changed
//		return false;
//	}
//
//	@DOpt(type = DOpt.Type.LinkAdderNew)
//	public boolean addNewCustomer(Customer s) {
//		customers.add(s);
//		customersCount++;
//
//		// no other attributes changed
//		return false;
//	}
//
//	@DOpt(type = DOpt.Type.LinkAdder)
//	public boolean addCustomer(Collection<Customer> customers) {
//		for (Customer s : customers) {
//			if (!this.customers.contains(s)) {
//				this.customers.add(s);
//			}
//		}
//
//		// no other attributes changed
//		return false;
//	}
//
//	@DOpt(type = DOpt.Type.LinkAdderNew)
//	public boolean addNewCustomer(Collection<Customer> customers) {
//		this.customers.addAll(customers);
//		customersCount += customers.size();
//
//		// no other attributes changed
//		return false;
//	}
//
//	@DOpt(type = DOpt.Type.LinkRemover)
//	// only need to do this for reflexive association: @MemberRef(name="customers")
//	public boolean removeCustomer(Customer s) {
//		boolean removed = customers.remove(s);
//
//		if (removed) {
//			customersCount--;
//		}
//
//		// no other attributes changed
//		return false;
//	}
//
//	@DOpt(type = DOpt.Type.Setter)
//	public void setCustomers(Collection<Customer> customers) {
//		this.customers = customers;
//
//		customersCount = customers.size();
//	}
//
//	/**
//	 * @effects return <tt>customersCount</tt>
//	 */
//	@DOpt(type = DOpt.Type.LinkCountGetter)
//	public Integer getCustomersCount() {
//		return customersCount;
//	}
//
//	@DOpt(type = DOpt.Type.LinkCountSetter)
//	public void setCustomersCount(int count) {
//		customersCount = count;
//	}
//
//	@DOpt(type = DOpt.Type.Getter)
//	public Collection<Customer> getCustomers() {
//		return customers;
//	}
//
//	@Override
//	public String toString() {
//		return "Address(" + getId() + "," + getCity() +","+getDistrict()+","+getWard()+")";
//	}
//	
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + id;
//		return result;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Address other = (Address) obj;
//		if (id != other.id)
//			return false;
//		return true;
//	}
//
//	private static int nextId(Integer currID) {
//		if (currID == null) {
//			idCounter++;
//			return idCounter;
//		} else {
//			int num = currID.intValue();
//			if (num > idCounter)
//				idCounter = num;
//
//			return currID;
//		}
//	}
//
//	/**
//	 * @requires minVal != null /\ maxVal != null
//	 * @effects update the auto-generated value of attribute <tt>attrib</tt>,
//	 *          specified for <tt>derivingValue</tt>, using <tt>minVal, maxVal</tt>
//	 */
//	@DOpt(type = DOpt.Type.AutoAttributeValueSynchroniser)
//	public static void updateAutoGeneratedValue(DAttr attrib, Tuple derivingValue, Object minVal, Object maxVal)
//			throws ConstraintViolationException {
//
//		if (minVal != null && maxVal != null) {
//			// TODO: update this for the correct attribute if there are more than one auto
//			// attributes of this class
//			if (attrib.name().equals("id")) {
//				int maxIdVal = (Integer) maxVal;
//				if (maxIdVal > idCounter)
//					idCounter = maxIdVal;}
//		}
//	}
//}

package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import domainapp.basics.exceptions.ConstraintViolationException;
import domainapp.basics.model.meta.AttrRef;
import domainapp.basics.model.meta.DAssoc;
import domainapp.basics.model.meta.DAssoc.AssocEndType;
import domainapp.basics.model.meta.DAssoc.AssocType;
import domainapp.basics.model.meta.DAssoc.Associate;
import domainapp.basics.model.meta.DAttr;
import domainapp.basics.model.meta.DAttr.Type;
import domainapp.basics.model.meta.DClass;
import domainapp.basics.model.meta.DOpt;
import domainapp.basics.model.meta.MetaConstants;
import domainapp.basics.model.meta.Select;
import domainapp.basics.util.Tuple;
import services.branch.model.Branch;
import services.person.model.customer.Customer;
import services.person.model.employee.Employee;

/**
 * A domain class whose objects are address names. This class is used as the
 * <code>allowedValues</code> of the domain attributes of other domain classes
 * (e.g. Customer.address).
 * 
 * <p>
 * Method <code>toString</code> overrides <code>Object.toString</code> to return
 * the string representation of a address name which is expected by the
 * application.
 * 
 * @author dmle
 *
 */
@DClass(schema = "banksystem")
public class Address {

	public static final String A_address = "address";

	@DAttr(name = "id", id = true, auto = true, length = 6, mutable = false, optional = false, type = Type.Integer)
	private int id;
	private static int idCounter;

	@DAttr(name = A_address, type = DAttr.Type.String, length = 60, optional = false, cid = true)
	private String address;
	
//	@DAttr(name = "atm", type = Type.Domain, optional = true, serialisable = false)
//	@DAssoc(ascName = "atm-has-address", role = "address", ascType = AssocType.One2One, endType = AssocEndType.One, associate = @Associate(type = ATM.class, cardMin = 1, cardMax = 1))
//	private ATM atm;

	@DAttr(name = "branch", type = Type.Domain, optional = true, serialisable = false)
	@DAssoc(ascName = "branch-has-address", role = "address", ascType = AssocType.One2One, endType = AssocEndType.One, associate = @Associate(type = Branch.class, cardMin = 1, cardMax = 1))
	private Branch branch;
	

	@DAttr(name = "customers", type = Type.Collection, serialisable = false, optional = true, filter = @Select(clazz = Customer.class))
	@DAssoc(ascName = "customer-has-address", role = "address", ascType = AssocType.One2Many, endType = AssocEndType.One, associate = @Associate(type = Customer.class, cardMin = 1, cardMax = 1000))
	private Collection<Customer> customers;

	// derived attributes
	private int customersCount;
	
	@DAttr(name = "employees", type = Type.Collection, serialisable = false, optional = true, filter = @Select(clazz = Employee.class))
	@DAssoc(ascName = "employee-has-address", role = "address", ascType = AssocType.One2Many, endType = AssocEndType.One, associate = @Associate(type = Employee.class, cardMin = 1, cardMax = 1000))
	private Collection<Employee> employees;

	// derived attributes
	private int employeesCount;


	@DOpt(type = DOpt.Type.ObjectFormConstructor)
	@DOpt(type = DOpt.Type.RequiredConstructor)
	public Address(@AttrRef("address") String address) {
		this(null, address, null);
	}
	
	@DOpt(type = DOpt.Type.ObjectFormConstructor)
	public Address( @AttrRef("address") String address,  @AttrRef("branch") Branch branch) {
		this(null, address,  branch);
	}

	// from data source
	@DOpt(type = DOpt.Type.DataSourceConstructor)
	public Address(@AttrRef("id") Integer id, @AttrRef("address") String address) {
		this(id, address,  null);
	}
	
	
	public Address( Integer id,  String address,
			   Branch branch) {
		this.id = nextId(id);
		this.address = address;
		
		this.branch = branch;

		customers = new ArrayList<>();
		customersCount = 0;
		
		employees = new ArrayList<>();
		employeesCount = 0;
	}

	// based constructor (used by others)
//  private Address(Integer id, String city, String district, String ward, Customer customer) {
//    this.id = nextId(id);
//    this.district = district;
//    this.ward = ward;
//    this.customer = customer;
//  }
	

	@DOpt(type = DOpt.Type.Getter)
	public int getId() {
		return id;
	}
	
	@DOpt(type = DOpt.Type.Getter)
	public String getAddress() {
		return address;
	}

	@DOpt(type = DOpt.Type.Setter)
	public void setAddress(String address) {
		this.address = address;
	}

	@DOpt(type = DOpt.Type.Getter)
	public Branch getBranch() {
		return branch;
	}

	@DOpt(type = DOpt.Type.LinkAdderNew)
	public void setNewBranch(Branch branch) {
		this.branch = branch;
		// do other updates here (if needed)
		// update!!
	}

	@DOpt(type = DOpt.Type.Setter)
	public void setBranch(Branch branch) {
		this.branch = branch;
	}
	

	@DOpt(type = DOpt.Type.LinkAdder)
	// only need to do this for reflexive association: @MemberRef(name="customers")
	public boolean addCustomer(Customer s) {
		if (!this.customers.contains(s)) {
			customers.add(s);
		}

		// no other attributes changed
		return false;
	}

	@DOpt(type = DOpt.Type.LinkAdderNew)
	public boolean addNewCustomer(Customer s) {
		customers.add(s);
		customersCount++;

		// no other attributes changed
		return false;
	}

	@DOpt(type = DOpt.Type.LinkAdder)
	public boolean addCustomer(Collection<Customer> customers) {
		for (Customer s : customers) {
			if (!this.customers.contains(s)) {
				this.customers.add(s);
			}
		}

		// no other attributes changed
		return false;
	}

	@DOpt(type = DOpt.Type.LinkAdderNew)
	public boolean addNewCustomer(Collection<Customer> customers) {
		this.customers.addAll(customers);
		customersCount += customers.size();

		// no other attributes changed
		return false;
	}

	@DOpt(type = DOpt.Type.LinkRemover)
	// only need to do this for reflexive association: @MemberRef(name="customers")
	public boolean removeCustomer(Customer s) {
		boolean removed = customers.remove(s);

		if (removed) {
			customersCount--;
		}

		// no other attributes changed
		return false;
	}

	@DOpt(type = DOpt.Type.Setter)
	public void setCustomers(Collection<Customer> customers) {
		this.customers = customers;

		customersCount = customers.size();
	}

	/**
	 * @effects return <tt>customersCount</tt>
	 */
	@DOpt(type = DOpt.Type.LinkCountGetter)
	public Integer getCustomersCount() {
		return customersCount;
	}

	@DOpt(type = DOpt.Type.LinkCountSetter)
	public void setCustomersCount(int count) {
		customersCount = count;
	}

	@DOpt(type = DOpt.Type.Getter)
	public Collection<Customer> getCustomers() {
		return customers;
	}
	
	
	
	@DOpt(type = DOpt.Type.LinkAdder)
	// only need to do this for reflexive association: @MemberRef(name="employees")
	public boolean addEmployee(Employee s) {
		if (!this.employees.contains(s)) {
			employees.add(s);
		}

		// no other attributes changed
		return false;
	}
	
	@DOpt(type = DOpt.Type.LinkAdderNew)
	public boolean addNewEmployee(Employee s) {
		employees.add(s);
		employeesCount++;

		// no other attributes changed
		return false;
	}

	@DOpt(type = DOpt.Type.LinkAdder)
	public boolean addEmployee(Collection<Employee> employees) {
		for (Employee s : employees) {
			if (!this.employees.contains(s)) {
				this.employees.add(s);
			}
		}

		// no other attributes changed
		return false;
	}

	@DOpt(type = DOpt.Type.LinkAdderNew)
	public boolean addNewEmployee(Collection<Employee> employees) {
		this.employees.addAll(employees);
		employeesCount += employees.size();

		// no other attributes changed
		return false;
	}

	@DOpt(type = DOpt.Type.LinkRemover)
	// only need to do this for reflexive association: @MemberRef(name="employees")
	public boolean removeEmployee(Employee s) {
		boolean removed = employees.remove(s);

		if (removed) {
			employeesCount--;
		}

		// no other attributes changed
		return false;
	}

	@DOpt(type = DOpt.Type.Setter)
	public void setEmployees(Collection<Employee> employees) {
		this.employees = employees;

		employeesCount = employees.size();
	}

	/**
	 * @effects return <tt>employeesCount</tt>
	 */
	@DOpt(type = DOpt.Type.LinkCountGetter)
	public Integer getEmployeesCount() {
		return employeesCount;
	}

	@DOpt(type = DOpt.Type.LinkCountSetter)
	public void setEmployeesCount(int count) {
		employeesCount = count;
	}

	@DOpt(type = DOpt.Type.Getter)
	public Collection<Employee> getEmployees() {
		return employees;
	}

	@Override
	public String toString() {
		return "Address(" + getId() + "," + getAddress() +")";
	}
	
	@Override
	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + id;
//		return result;
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Address other = (Address) obj;
		if (id != other.id)
			return false;
		return true;
	}

	private static int nextId(Integer currID) {
		if (currID == null) {
			idCounter++;
			return idCounter;
		} else {
			int num = currID.intValue();
			if (num > idCounter)
				idCounter = num;

			return currID;
		}
	}

	/**
	 * @requires minVal != null /\ maxVal != null
	 * @effects update the auto-generated value of attribute <tt>attrib</tt>,
	 *          specified for <tt>derivingValue</tt>, using <tt>minVal, maxVal</tt>
	 */
	@DOpt(type = DOpt.Type.AutoAttributeValueSynchroniser)
	public static void updateAutoGeneratedValue(DAttr attrib, Tuple derivingValue, Object minVal, Object maxVal)
			throws ConstraintViolationException {

		if (minVal != null && maxVal != null) {
			// TODO: update this for the correct attribute if there are more than one auto
			// attributes of this class
			if (attrib.name().equals("id")) {
				int maxIdVal = (Integer) maxVal;
				if (maxIdVal > idCounter)
					idCounter = maxIdVal;}
		}
	}
}
