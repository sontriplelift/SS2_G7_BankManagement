package services.transactions.model.loanTransactions;


import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import services.account.model.LoanAccount;
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
public class PeriodicRepayment extends Transactions{
	
	@DAttr(name = "loanAccount", type = Type.Domain, optional = false)
	@DAssoc(ascName = "loanAccount-has-periodicRepayment", role = "periodicRepayment", ascType = AssocType.One2Many, endType = AssocEndType.Many
	, associate = @Associate(type = LoanAccount.class, cardMin = 1, cardMax = 1))
	protected LoanAccount loanAccount;
	
//	@DAttr(name = "installment",auto=true, type = Type.Integer, optional = false, mutable = false)
//	private Integer installment;
	
	@DAttr(name = "repaymentAmount",auto=true, type = Type.Long, length = 20, optional = false, mutable = false)
	private Long repaymentAmount;
	
	//constructor
	
	@DOpt(type = DOpt.Type.ObjectFormConstructor)
	@DOpt(type = DOpt.Type.RequiredConstructor)
	public PeriodicRepayment( 		
			@AttrRef("time") Date time,
			@AttrRef("teller") Teller teller,
			@AttrRef("loanAccount") LoanAccount loanAccount
			)
			throws ConstraintViolationException {
		this(null, time, teller, loanAccount, null);
	}
	
	@DOpt(type = DOpt.Type.DataSourceConstructor)
	public PeriodicRepayment(@AttrRef("id") Integer id, 
			@AttrRef("time") Date time,
			@AttrRef("teller") Teller teller,
			@AttrRef("loanAccount") LoanAccount loanAccount,
			@AttrRef("repaymentAmount") Long repaymentAmount
			)
			throws ConstraintViolationException {
		super(id, time, teller);
		this.loanAccount = loanAccount;
		this.repaymentAmount = getRepaymentAmount();
//		if((loanAccount.getRepaidAmount() + this.repaymentAmount) > loanAccount.getTotalRepayment()) {
//			throw new ConstraintViolationException(DExCode.INVALID_REPAYMENT);
//		}
		if(loanAccount.getNumOfPaidInstallments() == loanAccount.getTotalInstallments()) {
			throw new ConstraintViolationException(DExCode.INVALID_REPAYMENT);
		}
	}
	
	public LoanAccount getLoanAccount() {
		return loanAccount;
	}

	public void setLoanAccount(LoanAccount loanAccount) {
		this.loanAccount = loanAccount;
	}

	public Long getRepaymentAmount() {
		repaymentAmount = loanAccount.getTotalRepayment() / loanAccount.getTotalInstallments();
		Long a = loanAccount.getRepaidAmount() + loanAccount.getTotalRepayment() / loanAccount.getTotalInstallments();
	
		int lateMonths = monthsBetween(loanAccount.getCreationDate(), this.getTime()) - (loanAccount.getNumOfPaidInstallments())*loanAccount.getRepaymentPeriod();
//		int lateMonths = (int) (monthsBetween(loanAccount.getCreationDate(), this.getTime()) - (loanAccount.getTotalRepayment() / a));
		if (lateMonths > 0) {
			repaymentAmount = (long) (repaymentAmount + repaymentAmount * 0.1 * lateMonths/12);
		}
		return repaymentAmount;
	}

	public static int monthsBetween(Date d1, Date d2){
	    if(d2==null || d1==null){
	        return -1;//Error
	    }
	    Calendar m_calendar=Calendar.getInstance();
	    m_calendar.setTime(d1);
	    int nMonth1=12*m_calendar.get(Calendar.YEAR)+m_calendar.get(Calendar.MONTH);
	    int a = m_calendar.get(Calendar.DAY_OF_MONTH);
	    m_calendar.setTime(d2);
	    int nMonth2=12*m_calendar.get(Calendar.YEAR)+m_calendar.get(Calendar.MONTH);
	    int b = m_calendar.get(Calendar.DAY_OF_MONTH);
	    if (a>b) {
	    	return nMonth2-nMonth1-1;
	    }
	    return nMonth2-nMonth1;
	}
	

}
