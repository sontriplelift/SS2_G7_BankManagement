//package services.person.employee;
//
//import java.util.Date;
//
//import domainapp.basics.exceptions.ConstraintViolationException;
//import domainapp.basics.model.meta.AttrRef;
//import domainapp.basics.model.meta.DAssoc;
//import domainapp.basics.model.meta.DAssoc.AssocEndType;
//import domainapp.basics.model.meta.DAssoc.AssocType;
//import domainapp.basics.model.meta.DAssoc.Associate;
//import domainapp.basics.model.meta.DAttr;
//import domainapp.basics.model.meta.DAttr.Format;
//import domainapp.basics.model.meta.DAttr.Type;
//import domainapp.basics.model.meta.DClass;
//import domainapp.basics.model.meta.DOpt;
//import domainapp.basics.util.Tuple;
//import exceptions.DExCode;
//import services.branch.Branch;
//import services.person.Address;
//import services.person.Gender;
//import utils.DToolkit;
//
///**
// * Represents a Employee. The Employee ID is auto-incremented from the current
// * year.
// * 
// * @author dmle
// * @version 2.0
// */
//@DClass(schema = "banksystem")
//public  class Employee {
//	public static final String A_name = "name";
//	public static final String A_gender = "gender";
//	public static final String A_id = "id";
//	public static final String A_dob = "dob";
//	public static final String A_address = "address";
//	public static final String A_email = "email";
//	public static final String A_phone = "phone";
//	public static final String A_branch = "branch";
//	public static final String A_salary = "salary";
//
////	@DAttr(name = "id1", id = true, type = Type.Integer, auto = true, length = 7, mutable = false, optional = false)
////	private int id1;
//	// attributes of Employees
//	@DAttr(name = A_id,  id = true,type = Type.String, auto = true, length = 7, mutable = false, optional = false)
//	private String id;
//	// static variable to keep track of Employee id
//	private static int idCounter = 0;
//
//	@DAttr(name = A_name, type = Type.String, length = 30, optional = false, cid = true)
//	private String name;
//
//	@DAttr(name = A_gender, type = Type.Domain, length = 10, optional = false)
//	private Gender gender;
//
//	@DAttr(name = A_dob, type = Type.Date, length = 15, optional = false, format = Format.Date)
//	private Date dob;
//
//	@DAttr(name = A_address, type = Type.Domain, length = 20, optional = false)
//	@DAssoc(ascName = "employee-has-address", role = "employee", ascType = AssocType.One2One, endType = AssocEndType.One, associate = @Associate(type = Address.class, cardMin = 1, cardMax = 1))
//	private Address address;
//
//	@DAttr(name = A_email, type = Type.String, length = 30, optional = false)
//	private String email;
//
//	@DAttr(name = A_phone, type = Type.String, length = 11, optional = false)
//	private String phone;
//
//	@DAttr(name = A_salary, type = Type.Long, optional = false)
//	private Long salary;
//
//	@DAttr(name = "branch", type = Type.Domain, length = 14, optional = false, serialisable = false)
//	protected Branch branch;
//
//	// constructor methods
//	// for creating in the application
//	
//	@DOpt(type = DOpt.Type.ObjectFormConstructor)
//	@DOpt(type = DOpt.Type.RequiredConstructor)
//	public Employee(@AttrRef("name") String name, @AttrRef("gender") Gender gender, @AttrRef("dob") Date dob,
//			@AttrRef("address") Address address, @AttrRef("email") String email, @AttrRef("phone") String phone,
//			@AttrRef("salary") Long salary, @AttrRef("branch") Branch branch) {
//		this(null, name, gender, dob, address, email, phone, salary, branch);
//	}
//
//	public Employee(@AttrRef("id") String id, @AttrRef("dob") String name, @AttrRef("gender") Gender gender,
//			@AttrRef("dob") Date dob, @AttrRef("address") Address address, @AttrRef("email") String email,
//			@AttrRef("phone") String phone, @AttrRef("salary") Long salary) throws ConstraintViolationException {
//		// generate an id
//		this(id, name, gender, dob, address, email, phone, salary, null);
//	}
//	
//	
//	// a shared constructor that is invoked by other constructors
//		@DOpt(type = DOpt.Type.DataSourceConstructor)
//	public Employee(String id, String name, Gender gender,
//			 Date dob,  Address address,  String email,
//			 String phone, Long salary, Branch branch)
//			throws ConstraintViolationException {
//		// generate an id
//		this.id = nextID(id);
//
//		// assign other values
////		this.account = account;
//		this.name = name;
//		this.gender = gender;
//		this.dob = dob;
//		this.address = address;
//		this.email = email;
//		this.phone = phone;
//		this.branch = branch;
//		this.salary = salary;
//
//	}
//
//	// setter methods
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public void setDob(Date dob) throws ConstraintViolationException {
//		// additional validation on dob
//		if (dob.before(DToolkit.MIN_DOB)) {
//			throw new ConstraintViolationException(DExCode.INVALID_DOB, dob);
//		}
//
//		this.dob = dob;
//	}
//
//	public void setGender(Gender gender) {
//		this.gender = gender;
//	}
//
//	public void setAddress(Address address) {
//		this.address = address;
//	}
//
//	// v2.7.3
//	public void setNewAddress(Address address) {
//		// change this invocation if need to perform other tasks (e.g. updating value of
//		// a derived attribtes)
//		setAddress(address);
//	}
//
//	public void setEmail(String email) throws ConstraintViolationException {
//		if (email.indexOf("@") < 0) {
//			throw new ConstraintViolationException(ConstraintViolationException.Code.INVALID_VALUE,
//					new Object[] { "'" + email + "' (does not have '@') " });
//		}
//		this.email = email;
//	}
//
//	public void setPhone(String phone) {
//		this.phone = phone;
//	}
//
//	public void setSalary(Long salary) {
//		this.salary = salary;
//	}
//
//	public void setBranch(Branch branch) {
//		this.branch = branch;
//	}
//
//	// getter methods
//	public String getId() {
//		return id;
//	}
//
//	public String getName() {
//		return name;
//	}
//
//	public Gender getGender() {
//		return gender;
//	}
//
//	public Date getDob() {
//		return dob;
//	}
//
//	public Address getAddress() {
//		return address;
//	}
//
//	public String getEmail() {
//		return email;
//	}
//
//	public String getPhone() {
//		return phone;
//	}
//
//	public Long getSalary() {
//		return salary;
//	}
//
//	public Branch getBranch() {
//		return branch;
//	}
//
//	// override toString
//	/**
//	 * @effects returns <code>this.id</code>
//	 */
//	@Override
//	public String toString() {
//		return toString(true);
//	}
//
//	/**
//	 * @effects returns <code>Employee(id,name,dob,address,email)</code>.
//	 */
//	public String toString(boolean full) {
//		if (full)
//			return "Employee(" + id + "," + name + "," + gender + ", " + dob + "," + address + "," + email + "," + phone
//					+ ")";
//		else
//			return "Employee(" + id + ")";
//	}
//
//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
//		Employee other = (Employee) obj;
//		if (id == null) {
//			if (other.id != null)
//				return false;
//		} else if (!id.equals(other.id))
//			return false;
//		return true;
//	}
//
//	// automatically generate the next Employee id
//	private String nextID(String id) throws ConstraintViolationException {
//		if (id == null) { // generate a new id
////			if (idCounter == 0) {
////				idCounter = Calendar.getInstance().get(Calendar.YEAR);
////			} else {
//			idCounter++;
//			String stringIdCounter = String.format("%04d", idCounter);
////			}
//			return "EMP" + stringIdCounter;
//		} else {
//			// update id
//			int num;
//			try {
//				num = Integer.parseInt(id.substring(1));
//			} catch (RuntimeException e) {
//				throw new ConstraintViolationException(ConstraintViolationException.Code.INVALID_VALUE, e,
//						new Object[] { id });
//			}
//
//			if (num > idCounter) {
//				idCounter = num;
//			}
//
//			return id;
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
//
//			String maxId = (String) maxVal;
//
//			try {
//				int maxIdNum = Integer.parseInt(maxId.substring(3));
//
//				if (maxIdNum > idCounter) // extra check
//					idCounter = maxIdNum;
//
//			} catch (RuntimeException e) {
//				throw new ConstraintViolationException(ConstraintViolationException.Code.INVALID_VALUE, e,
//						new Object[] { maxId });
//			}
//		}
//	}
//}

package services.person.model.employee;

import java.util.Date;

import domainapp.basics.exceptions.ConstraintViolationException;
import domainapp.basics.model.meta.AttrRef;
import domainapp.basics.model.meta.DAssoc;
import domainapp.basics.model.meta.DAssoc.AssocEndType;
import domainapp.basics.model.meta.DAssoc.AssocType;
import domainapp.basics.model.meta.DAssoc.Associate;
import domainapp.basics.model.meta.DAttr;
import domainapp.basics.model.meta.DAttr.Format;
import domainapp.basics.model.meta.DAttr.Type;
import domainapp.basics.model.meta.DClass;
import domainapp.basics.model.meta.DOpt;
import domainapp.basics.util.Tuple;
import exceptions.DExCode;
import services.Address;
import services.branch.model.Branch;
import services.person.model.Gender;
import utils.DToolkit;

/**
 * Represents a Employee. The Employee ID is auto-incremented from the current
 * year.
 * 
 * @author dmle
 * @version 2.0
 */
@DClass(schema = "banksystem")
public abstract  class Employee {
	public static final String A_name = "name";
	public static final String A_gender = "gender";
	public static final String A_id = "id";
	public static final String A_dob = "dob";
	public static final String A_address = "address";
	public static final String A_email = "email";
	public static final String A_phone = "phone";
	public static final String A_branch = "branch";
	public static final String A_salary = "salary";

//	@DAttr(name = "id1", id = true, type = Type.Integer, auto = true, length = 7, mutable = false, optional = false)
//	private int id1;
	// attributes of Employees
	@DAttr(name = A_id,  id = true,type = Type.String, auto = true, length = 7, mutable = false, optional = false)
	private String id;
	// static variable to keep track of Employee id
	private static int idCounter = 0;

	@DAttr(name = A_name, type = Type.String, length = 30, optional = false, cid = true)
	private String name;

	@DAttr(name = A_gender, type = Type.Domain, length = 10, optional = false)
	private Gender gender;

	@DAttr(name = A_dob, type = Type.Date, length = 15, optional = false, format = Format.Date)
	private Date dob;

	@DAttr(name = A_address, type = Type.Domain, length = 20, optional = false)
	@DAssoc(ascName = "employee-has-address", role = "employee", ascType = AssocType.One2Many, endType = AssocEndType.Many, associate = @Associate(type = Address.class, cardMin = 1, cardMax = 1))
	private Address address;

	@DAttr(name = A_email, type = Type.String, length = 30, optional = false)
	private String email;

	@DAttr(name = A_phone, type = Type.String, length = 11, optional = false)
	private String phone;

	@DAttr(name = A_salary, type = Type.Long, optional = false, min = 15000000)
	private Long salary;

//	@DAttr(name = "branch", type = Type.Domain, length = 14, optional = false, serialisable = false)
//	protected Branch branch;

	// constructor methods
	// for creating in the application
	
	@DOpt(type = DOpt.Type.ObjectFormConstructor)
	@DOpt(type = DOpt.Type.RequiredConstructor)
	public Employee(@AttrRef("name") String name, @AttrRef("gender") Gender gender, @AttrRef("dob") Date dob,
			@AttrRef("address") Address address, @AttrRef("email") String email, @AttrRef("phone") String phone,
			@AttrRef("salary") Long salary) {
		this(null, name, gender, dob, address, email, phone, salary);
	}

	@DOpt(type = DOpt.Type.DataSourceConstructor)
	public Employee(@AttrRef("id") String id, @AttrRef("dob") String name, @AttrRef("gender") Gender gender,
			@AttrRef("dob") Date dob, @AttrRef("address") Address address, @AttrRef("email") String email,
			@AttrRef("phone") String phone, @AttrRef("salary") Long salary) throws ConstraintViolationException {
		// generate an id
		this.id = nextID(id);

		// assign other values
//		this.account = account;
		this.name = name;
		this.gender = gender;
		if (dob.before(DToolkit.MIN_DOB)) {
			throw new ConstraintViolationException(DExCode.INVALID_DOB, dob);
		}
		this.dob = dob;
		this.address = address;
		this.email = email;
		this.phone = phone;
		this.salary = salary;
	}
	

	// setter methods
	public void setName(String name) {
		this.name = name;
	}

	public void setDob(Date dob) throws ConstraintViolationException {
		// additional validation on dob
		if (dob.before(DToolkit.MIN_DOB)) {
			throw new ConstraintViolationException(DExCode.INVALID_DOB, dob);
		}

		this.dob = dob;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	// v2.7.3
	public void setNewAddress(Address address) {
		// change this invocation if need to perform other tasks (e.g. updating value of
		// a derived attribtes)
		setAddress(address);
	}

	public void setEmail(String email) throws ConstraintViolationException {
		if (email.indexOf("@") < 0) {
			throw new ConstraintViolationException(ConstraintViolationException.Code.INVALID_VALUE,
					new Object[] { "'" + email + "' (does not have '@') " });
		}
		this.email = email;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setSalary(Long salary) {
		this.salary = salary;
	}

//	public void setBranch(Branch branch) {
//		this.branch = branch;
//	}

	// getter methods
	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Gender getGender() {
		return gender;
	}

	public Date getDob() {
		return dob;
	}

	public Address getAddress() {
		return address;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone() {
		return phone;
	}

	public Long getSalary() {
		return salary;
	}

//	public Branch getBranch() {
//		return branch;
//	}

	// override toString
	/**
	 * @effects returns <code>this.id</code>
	 */
	@Override
	public String toString() {
		return toString(true);
	}

	/**
	 * @effects returns <code>Employee(id,name,dob,address,email)</code>.
	 */
	public String toString(boolean full) {
		if (full)
			return "Employee(" + id + "," + name + "," + gender + ", " + dob + "," + address + "," + email + "," + phone
					+ ")";
		else
			return "Employee(" + id + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	// automatically generate the next Employee id
	private String nextID(String id) throws ConstraintViolationException {
		if (id == null) { // generate a new id
//			if (idCounter == 0) {
//				idCounter = Calendar.getInstance().get(Calendar.YEAR);
//			} else {
			idCounter++;
			String stringIdCounter = String.format("%04d", idCounter);
//			}
			return "EMP" + stringIdCounter;
		} else {
			// update id
			int num;
			try {
				num = Integer.parseInt(id.substring(3));
			} catch (RuntimeException e) {
				throw new ConstraintViolationException(ConstraintViolationException.Code.INVALID_VALUE, e,
						new Object[] { id });
			}

			if (num > idCounter) {
				idCounter = num;
			}

			return id;
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

			String maxId = (String) maxVal;

			try {
				int maxIdNum = Integer.parseInt(maxId.substring(3));

				if (maxIdNum > idCounter) // extra check
					idCounter = maxIdNum;

			} catch (RuntimeException e) {
				throw new ConstraintViolationException(ConstraintViolationException.Code.INVALID_VALUE, e,
						new Object[] { maxId });
			}
		}
	}
}
