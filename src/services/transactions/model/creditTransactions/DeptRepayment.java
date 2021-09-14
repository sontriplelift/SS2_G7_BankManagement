package services.transactions.model.creditTransactions;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.TimeZone;

import services.account.model.CreditAccount;
import services.person.model.employee.Teller;
import services.transactions.model.Transactions;
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
import exceptions.DExCode;

@DClass(schema = "banksystem")
public class DeptRepayment extends Transactions{
	
	@DAttr(name = "creditAccount", type = Type.Domain, optional = false)
	@DAssoc(ascName = "creditAccount-has-deptRepayment", role = "deptRepayment", ascType = AssocType.One2Many, endType = AssocEndType.Many
	, associate = @Associate(type = CreditAccount.class, cardMin = 1, cardMax = 1))
	private CreditAccount creditAccount;
	
//	@DAttr(name = "creditStatement", type = Type.Domain, optional = false)
//	@DAssoc(ascName = "creditStatement-has-deptRepayment", role = "deptRepayment", ascType = AssocType.One2One, endType = AssocEndType.One
//	, associate = @Associate(type = CreditStatement.class, cardMin = 1, cardMax = 1))
//	private CreditStatement creditStatement;
	
//	@DAttr(name="forYear", type = Type.Integer, auto = true, mutable = false, optional = false)
//	private int forYear;
//	
//	@DAttr(name="forMonth", type = Type.Integer, auto = true, mutable = false, optional = false)
//	private int forMonth;
//	
//	@DAttr(name="daysOfLate", type = Type.Integer, auto = true, mutable = false, optional = false)
//	private int daysOfLate;
	
	@DAttr(name = "repaymentAmount", type = Type.Long, length = 20, optional = false)
	private Long repaymentAmount;
	
	
	//constructor
	
	@DOpt(type = DOpt.Type.ObjectFormConstructor)
	@DOpt(type = DOpt.Type.RequiredConstructor)
	public DeptRepayment( 
			@AttrRef("time") Date time,
			@AttrRef("teller") Teller teller,
			@AttrRef("creditAccount") CreditAccount creditAccount,
//			@AttrRef("creditStatement") CreditStatement creditStatement,
//			@AttrRef("forMonth") Integer forMonth,
//			@AttrRef("daysOfLate") Integer daysOfLate
			@AttrRef("repaymentAmount") Long repaymentAmount
			)
			throws ConstraintViolationException {
		this(null, time, teller, creditAccount, repaymentAmount);
	}
	
	@DOpt(type = DOpt.Type.DataSourceConstructor)
	public DeptRepayment(@AttrRef("id") Integer id, 
			@AttrRef("time") Date time,
			@AttrRef("teller") Teller teller,
			@AttrRef("creditAccount") CreditAccount creditAccount,
//			@AttrRef("creditStatement") CreditStatement creditStatement,
//			@AttrRef("forYear") Integer forYear,
//			@AttrRef("forMonth") Integer forMonth,
//			@AttrRef("daysOfLate") Integer daysOfLate,
			@AttrRef("repaymentAmount") Long repaymentAmount
			)
			throws ConstraintViolationException {
		super(id, time, teller);
		this.creditAccount = creditAccount;
//		this.creditStatement = creditStatement;
//		this.forMonth = forMonth;
//		this.daysOfLate = daysOfLate;
		if (repaymentAmount > creditAccount.getCreditBalance()) {
			throw new ConstraintViolationException(DExCode.INVALID_REPAYMENT_AMOUNT, creditAccount.getCreditBalance());
		}
		this.repaymentAmount = repaymentAmount;
	}

//	public CreditStatement getCreditStatement() {
//		return creditStatement;
//	}
//
//	public void setCreditStatement(CreditStatement creditStatement) {
//		this.creditStatement = creditStatement;
//	}

	

//	public int getForYear() {
//		return creditStatement.getYear();
//	}
//
//	public int getForMonth() {
//		return creditStatement.getMonth();
//	}
//
//	public int getDaysOfLate() {
//		Calendar cal = Calendar.getInstance();
//		if (forMonth == 12) {
//			cal.set(forYear+1, 1, 1);
//		} else {
//			cal.set(forYear, forMonth + 1, 1);
//		}
//		Date deadline = cal.getTime();
//		
//		int daysOfLate = (int) (this.getTime().getTime() - deadline.getTime() - 60L);
//		
//		return daysOfLate;
//	}

	public Long getRepaymentAmount() {
//		if (daysOfLate <= 0) {
//			repaymentAmount = creditStatement.getTotalDeptInMonth();
//		} else if (daysOfLate <= 120) {
//			repaymentAmount = (long) (creditStatement.getTotalDeptInMonth() * 1.05);
//		} else if (daysOfLate <= 365) {
//			repaymentAmount = (long) (creditStatement.getTotalDeptInMonth() * 1.2);
//		} else {
//			repaymentAmount = (long) (creditStatement.getTotalDeptInMonth() * 1.5);
//		}
		return repaymentAmount;
	}

	public CreditAccount getCreditAccount() {
		return creditAccount;
	}

	public void setCreditAccount(CreditAccount creditAccount) {
		this.creditAccount = creditAccount;
	}

	public void setRepaymentAmount(Long repaymentAmount) throws ConstraintViolationException {
		if (repaymentAmount > creditAccount.getCreditBalance()) {
			throw new ConstraintViolationException(DExCode.INVALID_REPAYMENT_AMOUNT, creditAccount.getCreditBalance());
		}
		this.repaymentAmount = repaymentAmount;
	}
	
		
	

}
