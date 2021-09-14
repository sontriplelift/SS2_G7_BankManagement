package services.person.report;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import domainapp.basics.core.dodm.dsm.DSMBasic;
import domainapp.basics.core.dodm.qrm.QRM;
import domainapp.basics.exceptions.DataSourceException;
import domainapp.basics.exceptions.NotPossibleException;
import domainapp.basics.model.Oid;
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
import domainapp.basics.model.query.Expression.Op;
import domainapp.basics.model.query.Query;
import domainapp.basics.model.query.QueryToolKit;
import domainapp.basics.modules.report.model.meta.Output;
import services.person.model.customer.Customer;


/**
 * @overview Represent the reports about customers by name.
 * 
 * @author dmle
 *
 * @version 5.0
 */
@DClass(schema = "banksystem", serialisable = false)
public class CustomersByNameReport {
	
	public static final String A_rptCustomerByName = "rptCustomerByName";
	public static final String A_enrolments = "enrolments";
	
	@DAttr(name = "id", id = true, auto = true, type = Type.Integer, length = 5, optional = false, mutable = false)
	private int id;
	private static int idCounter = 0;

	/** input: customer name */
	@DAttr(name = "name", type = Type.String, length = 30, optional = false)
	private String name;

	/** output: customers whose names match {@link #name} */
	@DAttr(name = "customers", type = Type.Collection, optional = false, mutable = false, serialisable = false, filter = @Select(clazz = Customer.class, attributes = {
			Customer.A_id, Customer.A_name, Customer.A_gender, Customer.A_dob, Customer.A_address, Customer.A_email,
			Customer.A_phone, Customer.A_rptCustomerByName }), derivedFrom = { "name" })
	@DAssoc(ascName = "customers-by-name-report-has-customers", role = "report", ascType = AssocType.One2Many, endType = AssocEndType.One, associate = @Associate(type = Customer.class, cardMin = 0, cardMax = MetaConstants.CARD_MORE))
	@Output
	private Collection<Customer> customers;

	/** output: number of customers found (if any), derived from {@link #customers} */
	@DAttr(name = "numCustomers", type = Type.Integer, length = 20, auto = true, mutable = false)
	@Output
	private int numCustomers;

//realise link to report
	@DAttr(name = A_rptCustomerByName, type = Type.Domain, serialisable = false,
//IMPORTANT: set virtual=true to exclude this attribute from the object state
//(avoiding the view having to load this attribute's value from data source)
			virtual = true)
	private CustomersByNameReport rptCustomerByName;

	/**
	 * @effects initialise this with <tt>name</tt> and use {@link QRM} to retrieve
	 *          from data source all {@link Customer} whose names match
	 *          <tt>name</tt>. initialise {@link #customers} with the result if any.
	 * 
	 *          <p>
	 *          throws NotPossibleException if failed to generate data source query;
	 *          DataSourceException if fails to read from the data source
	 * 
	 */
	@DOpt(type = DOpt.Type.ObjectFormConstructor)
	@DOpt(type = DOpt.Type.RequiredConstructor)
	public CustomersByNameReport(@AttrRef("name") String name) throws NotPossibleException, DataSourceException {
		this.id = ++idCounter;

		this.name = name;

		doReportQuery();
	}

	public CustomersByNameReport getRptcustomerbyname() {
		return rptCustomerByName;
	}

	/**
	 * @effects return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @effects
	 * 
	 *          <pre>
	 *  set this.name = name
	 *  if name is changed
	 *    invoke {@link #doReportQuery()} to update the output attribute value
	 *    throws NotPossibleException if failed to generate data source query; 
	 *    DataSourceException if fails to read from the data source.
	 *          </pre>
	 */
	public void setName(String name) throws NotPossibleException, DataSourceException {
//    boolean doReportQuery = (name != null && !name.equals(this.name));

		this.name = name;

		// DONOT invoke this here if there are > 1 input attributes!
		doReportQuery();
	}

	/**
	 * This method is invoked when the report input has be set by the user.
	 * 
	 * @effects
	 * 
	 *          <pre>
	 *   formulate the object query
	 *   execute the query to retrieve from the data source the domain objects that satisfy it 
	 *   update the output attributes accordingly.
	 *  
	 *  <p>throws NotPossibleException if failed to generate data source query; 
	 *  DataSourceException if fails to read from the data source.
	 *          </pre>
	 */
	@DOpt(type = DOpt.Type.DerivedAttributeUpdater)
	@AttrRef(value = "customers")
	public void doReportQuery() throws NotPossibleException, DataSourceException {
		// the query manager instance

		QRM qrm = QRM.getInstance();

		// create a query to look up Customer from the data source
		// and then populate the output attribute (customers) with the result
		DSMBasic dsm = qrm.getDsm();

		// TODO: to conserve memory cache the query and only change the query parameter
		// value(s)
		Query q = QueryToolKit.createSearchQuery(dsm, Customer.class, new String[] { Customer.A_name },
				new Op[] { Op.MATCH }, new Object[] { "%" + name + "%" });
		
		// TODO: EX6.1
//				Query q1 = QueryToolKit.createSearchQuery(dsm, Customer.class, new String[] { Customer.A_name },
//						new Op[] { Op.MATCH }, new Object[] { name + "%"  });
//				
//				// TODO: EX6.2
//				Query q2 = QueryToolKit.createSearchQuery(dsm, Customer.class, new String[] { Customer.A_name },
//						new Op[] { Op.MATCH }, new Object[] { "%" + name   });
				

				Map<Oid, Customer> result = qrm.getDom().retrieveObjects(Customer.class, q);
//				Map<Oid, Customer> result2 = qrm.getDom().retrieveObjects(Customer.class, q2);
				
				// merge 2 map
//				Map<Oid, Customer> result = new HashMap<>();
				
//				if (result1 != null ) {
//					result.putAll(result1);
//				}
//				
//				if (result2 != null) {
//					result.putAll(result2);
//				}

				if (result != null) {
					// update the main output data
					
					customers = result.values();
//					customers.addAll(result1.values());

					// update other output (if any)
					numCustomers = customers.size();
				} else {
					// no data found: reset output
					resetOutput();
				}
	}

	/**
	 * @effects reset all output attributes to their initial values
	 */
	private void resetOutput() {
		customers = null;
		numCustomers = 0;
	}

	/**
	 * A link-adder method for {@link #customers}, required for the object form to
	 * function. However, this method is empty because customers have already be
	 * recorded in the attribute {@link #customers}.
	 */
	@DOpt(type = DOpt.Type.LinkAdder)
	public boolean addCustomer(Collection<Customer> customers) {
		// do nothing
		return false;
	}

	/**
	 * @effects return customers
	 */
	public Collection<Customer> getCustomers() {
		return customers;
	}

	/**
	 * @effects return numCustomers
	 */
	public int getNumCustomers() {
		return numCustomers;
	}

	/**
	 * @effects return id
	 */
	public int getId() {
		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	/**
	 * @effects
	 * 
	 * @version
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	/**
	 * @effects
	 * 
	 * @version
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CustomersByNameReport other = (CustomersByNameReport) obj;
		if (id != other.id)
			return false;
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	/**
	 * @effects
	 * 
	 * @version
	 */
	@Override
	public String toString() {
		return "CustomersByNameReport (" + id + ", " + name + ")";
	}

}
