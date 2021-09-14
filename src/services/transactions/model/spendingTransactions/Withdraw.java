package services.transactions.model.spendingTransactions;

import java.util.Collection;
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
import exceptions.DExCode;
import domainapp.basics.model.meta.DAssoc.AssocEndType;
import domainapp.basics.model.meta.DAssoc.AssocType;
import domainapp.basics.model.meta.DAssoc.Associate;
import domainapp.basics.model.meta.DAttr.Type;

@DClass(schema = "banksystem")
public class Withdraw extends Transactions{
	
	@DAttr(name = "spendAccount", type = Type.Domain, optional = false)
	@DAssoc(ascName = "spendAccount-has-withdraw", role = "withdraw", ascType = AssocType.One2Many, endType = AssocEndType.Many
	, associate = @Associate(type = SpendAccount.class, cardMin = 1, cardMax = 1))
	private SpendAccount spendAccount;
	
	@DAttr(name = "withdrawAmount", type = Type.Long, length = 20, optional = false, min = 10000L)
	private Long withdrawAmount;
	
	
	//constructor
	
	public Withdraw( 
			@AttrRef("time") Date time,
			@AttrRef("teller") Teller teller,
			@AttrRef("spendAccount") SpendAccount spendAccount,
			@AttrRef("withdrawAmount") Long withdrawAmount
			)
			throws ConstraintViolationException {
		this(null, time, teller, spendAccount, withdrawAmount);
	}
	
	@DOpt(type = DOpt.Type.DataSourceConstructor)
	public Withdraw(@AttrRef("id") Integer id, 
			@AttrRef("time") Date time,
			@AttrRef("teller") Teller teller,
			@AttrRef("spendAccount") SpendAccount spendAccount,
			@AttrRef("withdrawAmount") Long withdrawAmount
			)
			throws ConstraintViolationException {
		super(id, time, teller);
		this.spendAccount = spendAccount;
		if (withdrawAmount > (spendAccount.getBalance()-100000L)) {
			throw new ConstraintViolationException(DExCode.INVALID_WITHDRAW_AMOUNT, withdrawAmount);
		}
		this.withdrawAmount = withdrawAmount;
	}
	
	public SpendAccount getSpendAccount() {
		return spendAccount;
	}

	public void setSpendAccount(SpendAccount spendAccount) {
		this.spendAccount = spendAccount;
	}
	

	public Long getWithdrawAmount() {
		return withdrawAmount;
	}

	public void setWithdrawAmount(Long withdrawAmount) throws ConstraintViolationException {
		if (withdrawAmount > (spendAccount.getBalance()-100000L)) {
			throw new ConstraintViolationException(DExCode.INVALID_WITHDRAW_AMOUNT, withdrawAmount);
		}
		this.withdrawAmount = withdrawAmount;
	}
	

}
