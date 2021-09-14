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
import domainapp.basics.model.meta.DAssoc.AssocEndType;
import domainapp.basics.model.meta.DAssoc.AssocType;
import domainapp.basics.model.meta.DAssoc.Associate;
import domainapp.basics.model.meta.DAttr.Type;
import exceptions.DExCode;

@DClass(schema = "banksystem")
public class Transfer extends Transactions{
	
	@DAttr(name = "spendAccount", type = Type.Domain, optional = false)
	@DAssoc(ascName = "spendAccount-has-transfer", role = "transfer", ascType = AssocType.One2Many, endType = AssocEndType.Many
	, associate = @Associate(type = SpendAccount.class, cardMin = 1, cardMax = 1))
	private SpendAccount spendAccount;
	
	@DAttr(name = "transferAmount", type = Type.Long, length = 20, optional = false, min = 10000L)
	private Long transferAmount;
	
	@DAttr(name = "receiveAccount", type = Type.Domain, optional = false)
	@DAssoc(ascName = "receiveAccount-has-receiveTransfer", role = "receiveTransfer", ascType = AssocType.One2Many, endType = AssocEndType.Many
	, associate = @Associate(type = SpendAccount.class, cardMin = 1, cardMax = 1))
	public SpendAccount receiveAccount;
	
//	@DAttr(name = "transferFee", type = Type.Long, length = 20, optional = false, mutable = false, auto = true)
//	private Long transferFee;
	
	//constructor
	
	public Transfer( 
			@AttrRef("time") Date time,
			@AttrRef("teller") Teller teller,
			@AttrRef("spendAccount") SpendAccount spendAccount,
			@AttrRef("transferAmount") Long transferAmount,
			@AttrRef("receiveAccount") SpendAccount receiveAccount
			)
			throws ConstraintViolationException {
		this(null, time, teller, spendAccount, transferAmount, receiveAccount);
	}
	
	@DOpt(type = DOpt.Type.DataSourceConstructor)
	public Transfer(@AttrRef("id") Integer id, 
			@AttrRef("time") Date time,
			@AttrRef("teller") Teller teller,
			@AttrRef("spendAccount") SpendAccount spendAccount,
			@AttrRef("transferAmount") Long transferAmount,
			@AttrRef("receiveAccount") SpendAccount receiveAccount
			)
			throws ConstraintViolationException {
		super(id, time, teller);
		this.spendAccount = spendAccount;
		
		if (transferAmount > (spendAccount.getBalance()-100000L)) {
			throw new ConstraintViolationException(DExCode.INVALID_TRANSFER_AMOUNT, transferAmount);
		}
		this.transferAmount = transferAmount;
		
		if (receiveAccount == spendAccount) {
			throw new ConstraintViolationException(DExCode.INVALID_RECEIVE_ACCOUNT);
		}
		this.receiveAccount = receiveAccount;

	}
	
	public SpendAccount getSpendAccount() {
		return spendAccount;
	}

	public void setSpendAccount(SpendAccount spendAccount) {
		this.spendAccount = spendAccount;
	}
	

	public Long getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(Long transferAmount)  throws ConstraintViolationException {
		if (transferAmount > (spendAccount.getBalance()-100000L)) {
			throw new ConstraintViolationException(DExCode.INVALID_TRANSFER_AMOUNT, transferAmount);
		}
		this.transferAmount = transferAmount;
	}

	public SpendAccount getReceiveAccount() {
		return receiveAccount;
	}

	public void setReceiveAccount(SpendAccount receiveAccount) throws ConstraintViolationException {
		if (receiveAccount == spendAccount) {
			throw new ConstraintViolationException(DExCode.INVALID_RECEIVE_ACCOUNT);
		}
		this.receiveAccount = receiveAccount;
	}

//	public Long getTransferFee() {
//		return transferFee;
//	}
	
	

	
		

}
