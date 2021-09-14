package services.transactions.model.spendingTransactions;

import java.util.Date;

import services.account.model.SpendAccount;
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

@DClass(schema = "banksystem")
public class Deposit extends Transactions{
	
	@DAttr(name = "spendAccount", type = Type.Domain, optional = false)
	@DAssoc(ascName = "spendAccount-has-deposit", role = "deposit", ascType = AssocType.One2Many, endType = AssocEndType.Many
	, associate = @Associate(type = SpendAccount.class, cardMin = 1, cardMax = 1))
	protected SpendAccount spendAccount;
	
	@DAttr(name = "depositAmount", type = Type.Long, length = 20, optional = false, min = 10000L)
	private Long depositAmount;
	
	
	//constructor
	
	public Deposit( 
			@AttrRef("time") Date time,
			@AttrRef("teller") Teller teller,
			@AttrRef("spendAccount") SpendAccount spendAccount,
			@AttrRef("depositAmount") Long depositAmount
			)
			throws ConstraintViolationException {
		this(null, time, teller, spendAccount, depositAmount);
	}
	
	@DOpt(type = DOpt.Type.DataSourceConstructor)
	public Deposit(@AttrRef("id") Integer id, 
			@AttrRef("time") Date time,
			@AttrRef("teller") Teller teller,
			@AttrRef("spendAccount") SpendAccount spendAccount,
			@AttrRef("depositAmount") Long depositAmount
			)
			throws ConstraintViolationException {
		super(id, time, teller);
		this.spendAccount = spendAccount;
		this.depositAmount = depositAmount;
	}
	
	public SpendAccount getSpendAccount() {
		return spendAccount;
	}

	public void setSpendAccount(SpendAccount spendAccount) {
		this.spendAccount = spendAccount;
	}
	

	public Long getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(Long depositAmount) {
		this.depositAmount = depositAmount;
	}
	
//	public void updateBalance() {
//		this.spendAccount.balance += depositAmount;
//	}

}
