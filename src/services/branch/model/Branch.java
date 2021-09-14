package services.branch.model;

import java.util.ArrayList;
import java.util.Collection;

import services.Address;
import services.person.model.employee.Manager;
import services.person.model.employee.Teller;
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
import domainapp.basics.model.meta.Select;
import domainapp.basics.util.Tuple;

/**
 * Represents a teller class.
 * 
 * @author dmle
 *
 */
@DClass(schema = "banksystem")
public class Branch {

	public static final String A_address = "address";
	public static final String A_manager = "manager";
	public static final String A_teller = "tellers";

	@DAttr(name = "id", id = true, auto = true, length = 6, mutable = false, type = Type.Integer)
	private int id;
	private static int idCounter;

	// candidate identifier
	@DAttr(name = A_address,type = Type.Domain, length = 20, optional = false)
	@DAssoc(ascName = "branch-has-address", role = "branch", ascType = AssocType.One2One, endType = AssocEndType.One, associate = @Associate(type = Address.class, cardMin = 1, cardMax = 1))
	private Address address;

	@DAttr(name = A_manager, type = Type.Domain, length = 20, optional = true)
	@DAssoc(ascName = "branch-has-manager", role = "branch", ascType = AssocType.One2One, endType = AssocEndType.One, associate = @Associate(type = Manager.class, cardMin = 1, cardMax = 1))
	private Manager manager;

	@DAttr(name = A_teller, type = Type.Collection, serialisable = false, optional = true, filter = @Select(clazz = Teller.class
//			, attributes = {Teller.A_id, Teller.A_name, Teller.A_dob, Teller.A_branch }
	))
	@DAssoc(ascName = "branch-has-teller", role = "branch", ascType = AssocType.One2Many, endType = AssocEndType.One, associate = @Associate(type = Teller.class, cardMin = 1, cardMax = 30))
	private Collection<Teller> tellers;

	// derived attributes
	private int tellersCount;

	@DOpt(type = DOpt.Type.ObjectFormConstructor)
	@DOpt(type = DOpt.Type.RequiredConstructor)
	public Branch(@AttrRef("address") Address address, @AttrRef("manager") Manager manager) {
		this(null, address, manager);
	}

	@DOpt(type = DOpt.Type.ObjectFormConstructor)
	public Branch(@AttrRef("address") Address address) {
		this(null, address, null);
	}
	
	// constructor to create objects from data source
	@DOpt(type = DOpt.Type.DataSourceConstructor)
	public Branch(@AttrRef("id") Integer id, @AttrRef("address") Address address, @AttrRef("manager") Manager manager) {
		this.id = nextID(id);
		this.address = address;
		this.manager = manager;

		tellers = new ArrayList<>();
		tellersCount = 0;
	}

	@DOpt(type = DOpt.Type.Setter)
	public void setAddress(Address address) {
		this.address = address;
	}

	@DOpt(type = DOpt.Type.Setter)
	public void setManager(Manager manager) {
		this.manager = manager;
	}

	@DOpt(type = DOpt.Type.LinkAdder)
	// only need to do this for reflexive association: @MemberRef(name="tellers")
	public boolean addTeller(Teller s) {
		if (!this.tellers.contains(s)) {
			tellers.add(s);
		}

		// no other attributes changed
		return false;
	}

	@DOpt(type = DOpt.Type.LinkAdderNew)
	public boolean addNewTeller(Teller s) {
		tellers.add(s);
		tellersCount++;

		// no other attributes changed
		return false;
	}

	@DOpt(type = DOpt.Type.LinkAdder)
	public boolean addTeller(Collection<Teller> tellers) {
		for (Teller s : tellers) {
			if (!this.tellers.contains(s)) {
				this.tellers.add(s);
			}
		}

		// no other attributes changed
		return false;
	}

	@DOpt(type = DOpt.Type.LinkAdderNew)
	public boolean addNewTeller(Collection<Teller> tellers) {
		this.tellers.addAll(tellers);
		tellersCount += tellers.size();

		// no other attributes changed
		return false;
	}

	@DOpt(type = DOpt.Type.LinkRemover)
	// only need to do this for reflexive association: @MemberRef(name="tellers")
	public boolean removeTeller(Teller s) {
		boolean removed = tellers.remove(s);

		if (removed) {
			tellersCount--;
		}

		// no other attributes changed
		return false;
	}

	@DOpt(type = DOpt.Type.Setter)
	public void setTellers(Collection<Teller> tellers) {
		this.tellers = tellers;

		tellersCount = tellers.size();
	}

	/**
	 * @effects return <tt>tellersCount</tt>
	 */
	@DOpt(type = DOpt.Type.LinkCountGetter)
	public Integer getTellersCount() {
		return tellersCount;
	}

	@DOpt(type = DOpt.Type.LinkCountSetter)
	public void setTellersCount(int count) {
		tellersCount = count;
	}

	@DOpt(type = DOpt.Type.Getter)
	public Address getAddress() {
		return address;
	}

	@DOpt(type = DOpt.Type.Getter)
	public Collection<Teller> getTellers() {
		return tellers;
	}

	@DOpt(type = DOpt.Type.Getter)
	public int getId() {
		return id;
	}

	@DOpt(type = DOpt.Type.Getter)
	public Manager getManager() {
		return manager;
	}

	@Override
	public String toString() {
		return "Branch(" + getId() + "," + getAddress() + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
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
		Branch other = (Branch) obj;
		if (id != other.id)
			return false;
		return true;
	}

	private static int nextID(Integer currID) {
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
			if (attrib.name().equals("id")) {
				int maxIdVal = (Integer) maxVal;
				if (maxIdVal > idCounter)
					idCounter = maxIdVal;
			}
		}
	}

}
