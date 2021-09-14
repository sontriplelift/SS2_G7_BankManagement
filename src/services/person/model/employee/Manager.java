//package services.person.employee;
//
//import java.util.Date;
//
//import services.branch.Branch;
//import services.person.Address;
//import services.person.Gender;
//import domainapp.basics.exceptions.ConstraintViolationException;
//import domainapp.basics.model.meta.AttrRef;
//import domainapp.basics.model.meta.DAssoc;
//import domainapp.basics.model.meta.DAttr;
//import domainapp.basics.model.meta.DClass;
//import domainapp.basics.model.meta.DOpt;
//import domainapp.basics.model.meta.DAssoc.AssocEndType;
//import domainapp.basics.model.meta.DAssoc.AssocType;
//import domainapp.basics.model.meta.DAssoc.Associate;
//import domainapp.basics.model.meta.DAttr.Type;
//
//@DClass(schema = "banksystem")
//public class Manager extends Employee{
//
//	@DOpt(type = DOpt.Type.ObjectFormConstructor)
//	@DOpt(type = DOpt.Type.RequiredConstructor)
//	public Manager(@AttrRef("name") String name, @AttrRef("gender") Gender gender, @AttrRef("dob") Date dob,
//			@AttrRef("address") Address address, @AttrRef("email") String email, @AttrRef("phone") String phone, @AttrRef("salary") Double salary, @AttrRef("branch") Branch branch) {
//		this(null, name, gender, dob, address, email, phone, salary, branch);
//	}
//	
////	@DOpt(type = DOpt.Type.DataSourceConstructor)
////	public Manager(@AttrRef("id") String id, @AttrRef("dob") String name, @AttrRef("gender") Gender gender,
////			@AttrRef("dob") Date dob, @AttrRef("address") Address address, @AttrRef("email") String email,
////			@AttrRef("phone") String phone, @AttrRef("salary") Integer salary) throws ConstraintViolationException {
////		// generate an id
////		this(id, name, gender, dob, address, email, phone, salary, null);
////
////	}
//	
//	public Manager(String id, String name, Gender gender,
//			 Date dob,  Address address,  String email,
//			 String phone, Double salary, Branch branch) {
//		super(id, name, gender, dob, address, email, phone, salary, branch);
//	}
//	
//	@Override
//	@DAttr(name = "branch", type = Type.Domain, length = 14, optional = false)
//	@DAssoc(ascName = "branch-has-manager", role = "manager", ascType = AssocType.One2One, endType = AssocEndType.One
//	, associate = @Associate(type = Branch.class, cardMin = 1, cardMax = 1))
//	public Branch getBranch() {
//		return super.branch;
//	}
//
//}

package services.person.model.employee;

import java.util.Date;

import services.Address;
import services.branch.model.Branch;
import services.person.model.Gender;
import domainapp.basics.exceptions.ConstraintViolationException;
import domainapp.basics.model.meta.AttrRef;
import domainapp.basics.model.meta.DAssoc;
import domainapp.basics.model.meta.DAttr;
import domainapp.basics.model.meta.DClass;
import domainapp.basics.model.meta.DOpt;
import domainapp.basics.model.meta.DAssoc.AssocEndType;
import domainapp.basics.model.meta.DAssoc.AssocType;
import domainapp.basics.model.meta.DAssoc.Associate;
import domainapp.basics.model.meta.DAttr.Type;

@DClass(schema = "banksystem")
public class Manager extends Employee{
	
	@DAttr(name = "branch", type = Type.Domain, length = 14, optional = false, serialisable = false)
	@DAssoc(ascName = "branch-has-manager", role = "manager", ascType = AssocType.One2One, endType = AssocEndType.One
	, associate = @Associate(type = Branch.class, cardMin = 1, cardMax = 1))
	private Branch branch;
	
	@DOpt(type = DOpt.Type.ObjectFormConstructor)
	@DOpt(type = DOpt.Type.RequiredConstructor)
	public Manager(@AttrRef("name") String name, @AttrRef("gender") Gender gender, @AttrRef("dob") Date dob,
			@AttrRef("address") Address address, @AttrRef("email") String email, @AttrRef("phone") String phone, @AttrRef("salary") Long salary, @AttrRef("branch") Branch branch) {
		this(null, name, gender, dob, address, email, phone, salary, branch);
	}
	
//	@DOpt(type = DOpt.Type.ObjectFormConstructor)
//	public Manager( @AttrRef("name") String name, @AttrRef("gender") Gender gender, @AttrRef("dob") Date dob,
//			@AttrRef("address") Address address, @AttrRef("email") String email, @AttrRef("phone") String phone, @AttrRef("salary") Double salary) {
//		this( name, gender, dob, address, email, phone, salary, null);
//	}
	
	@DOpt(type = DOpt.Type.DataSourceConstructor)
	public Manager(@AttrRef("id") String id, @AttrRef("dob") String name, @AttrRef("gender") Gender gender,
			@AttrRef("dob") Date dob, @AttrRef("address") Address address, @AttrRef("email") String email,
			@AttrRef("phone") String phone, @AttrRef("salary") Long salary, @AttrRef("branch") Branch branch) throws ConstraintViolationException {
		// generate an id
		super(id, name, gender, dob, address, email, phone, salary);
		this.branch = branch;

	}
	
//	public Manager(String id, String name, Gender gender,
//			 Date dob,  Address address,  String email,
//			 String phone, Double salary, Branch branch) {
//		super(id, name, gender, dob, address, email, phone, salary, branch);
//	}
	
	public void setBranch(Branch branch) {
		this.branch = branch;
	}

	public Branch getBranch() {
		return branch;
	}

}

