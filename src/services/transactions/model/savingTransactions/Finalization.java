package services.transactions.model.savingTransactions;

import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import services.account.model.SavingAccount;
import services.account.model.TermOfSaving;
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
public class Finalization extends Transactions{
	
	@DAttr(name = "savingAccount", type = Type.Domain, optional = false)
	@DAssoc(ascName = "savingAccount-has-finalization", role = "finalization", ascType = AssocType.One2One, endType = AssocEndType.One
	, associate = @Associate(type = SavingAccount.class, cardMin = 1, cardMax = 1))
	protected SavingAccount savingAccount;
	
	@DAttr(name = "finalizationAmount", type = Type.Long, length = 20, optional = false, auto = true, mutable = false)
	private Long finalizationAmount;
	
	//constructor
	
	@DOpt(type = DOpt.Type.ObjectFormConstructor)
	@DOpt(type = DOpt.Type.RequiredConstructor)
	public Finalization( 		
			@AttrRef("time") Date time,
			@AttrRef("teller") Teller teller,
			@AttrRef("savingAccount") SavingAccount savingAccount
			)
			throws ConstraintViolationException {
		this(null, time, teller, savingAccount, null);
	}
	
	@DOpt(type = DOpt.Type.DataSourceConstructor)
	public Finalization(@AttrRef("id") Integer id, 
			@AttrRef("time") Date time,
			@AttrRef("teller") Teller teller,
			@AttrRef("savingAccount") SavingAccount savingAccount,
			@AttrRef("finalizationAmount") Long finalizationAmount
			)
			throws ConstraintViolationException {
		super(id, time, teller);
		this.savingAccount = savingAccount;
		this.finalizationAmount = getFinalizationAmount();
	}
	
	public SavingAccount getSavingAccount() {
		return savingAccount;
	}

	public void setSavingAccount(SavingAccount savingAccount) {
		this.savingAccount = savingAccount;
	}

	public Long getFinalizationAmount() throws ConstraintViolationException {
		
		int numOfMonths = monthsBetween(savingAccount.getCreationDate(), this.getTime());
		
		if (savingAccount.getTermOfSaving() == TermOfSaving.Indefinite) {
			finalizationAmount = (long) (savingAccount.getSavingAmount() + savingAccount.getSavingAmount() * savingAccount.getInterestRate() * numOfMonths / 12);
		
		} else {
			
			int termOfSavingInMonths = monthsBetween(savingAccount.getCreationDate(), savingAccount.getDueDate());
			
			if (termOfSavingInMonths <= numOfMonths) {
				finalizationAmount = (long) (savingAccount.getSavingAmount() + savingAccount.getSavingAmount() * savingAccount.getInterestRate() * termOfSavingInMonths / 12);
			} else { 
				throw new ConstraintViolationException(DExCode.INVALID_FINALIZATION, savingAccount.getDueDate());
			}
		}
			
		return finalizationAmount;
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
	
//	private static int calculateDays(Date from, Date to) {
//		long diff = to.getTime() - from.getTime();
//
//        TimeUnit time = TimeUnit.DAYS; 
//        int diffrence = (int) time.convert(diff, TimeUnit.MILLISECONDS);
//		
//        return diffrence;
//        }
	

}
