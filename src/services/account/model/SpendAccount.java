package services.account.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import domainapp.basics.exceptions.ConstraintViolationException;
import domainapp.basics.model.meta.AttrRef;
import domainapp.basics.model.meta.DAssoc;
import domainapp.basics.model.meta.DAttr;
import domainapp.basics.model.meta.DClass;
import domainapp.basics.model.meta.DOpt;
import domainapp.basics.model.meta.MetaConstants;
import domainapp.basics.model.meta.Select;
import domainapp.basics.model.meta.DAssoc.AssocEndType;
import domainapp.basics.model.meta.DAssoc.AssocType;
import domainapp.basics.model.meta.DAssoc.Associate;
import domainapp.basics.util.Tuple;
import exceptions.DExCode;
import services.person.model.customer.Customer;
import services.transactions.model.spendingTransactions.Deposit;
import services.transactions.model.spendingTransactions.Transfer;
import services.transactions.model.spendingTransactions.Withdraw;
import domainapp.basics.model.meta.DAttr.Type;

@DClass(schema = "banksystem")
public class SpendAccount extends Account {

	public static final String A_code = "code";
	public static final String A_balance = "balance";

	@DAttr(name = A_code, cid=true, type = Type.String, auto = true, length = 9, mutable = false, optional = false)
	private String code;
	// static variable to keep track of account code
	private static int codeCounter = 0;

	@DAttr(name = A_balance, type = Type.Long, length = 20, optional = false, min = 100000L)
	private Long balance;

	@DAttr(name = "deposit", type = Type.Collection, serialisable = false, optional = true, filter = @Select(clazz = Deposit.class))
	@DAssoc(ascName = "spendAccount-has-deposit", role = "spendAccount", ascType = AssocType.One2Many, endType = AssocEndType.One, associate = @Associate(type = Deposit.class, cardMin = 1, cardMax = MetaConstants.CARD_MORE))
	private Collection<Deposit> deposit;

	private int depositCount;
	
	@DAttr(name = "withdraw", type = Type.Collection, serialisable = false, optional = true, filter = @Select(clazz = Withdraw.class))
	@DAssoc(ascName = "spendAccount-has-withdraw", role = "spendAccount", ascType = AssocType.One2Many, endType = AssocEndType.One, associate = @Associate(type = Withdraw.class, cardMin = 1, cardMax = MetaConstants.CARD_MORE))
	private Collection<Withdraw> withdraw;

	private int withdrawCount;
	
	@DAttr(name = "transfer", type = Type.Collection, serialisable = false, optional = true, filter = @Select(clazz = Transfer.class))
	@DAssoc(ascName = "spendAccount-has-transfer", role = "spendAccount", ascType = AssocType.One2Many, endType = AssocEndType.One, associate = @Associate(type = Transfer.class, cardMin = 1, cardMax = MetaConstants.CARD_MORE))
	private Collection<Transfer> transfer;
	
	private int transferCount;
	
	@DAttr(name = "receiveTransfer", type = Type.Collection, serialisable = false, optional = true, filter = @Select(clazz = Transfer.class))
	@DAssoc(ascName = "receiveAccount-has-receiveTransfer", role = "receiveAccount", ascType = AssocType.One2Many, endType = AssocEndType.One, associate = @Associate(type = Transfer.class, cardMin = 1, cardMax = MetaConstants.CARD_MORE))
	private Collection<Transfer> receiveTransfer;

	// constructor

	@DOpt(type = DOpt.Type.ObjectFormConstructor)
	@DOpt(type = DOpt.Type.RequiredConstructor)
	public SpendAccount(@AttrRef("customer") Customer customer, @AttrRef("creationDate") Date creationDate,
			@AttrRef("balance") Long balance) throws ConstraintViolationException {
		this(null, customer,creationDate, null, balance);
	}

	@DOpt(type = DOpt.Type.DataSourceConstructor)
	public SpendAccount(@AttrRef("id") String id, @AttrRef("customer") Customer customer,
			@AttrRef("creationDate") Date creationDate,
			@AttrRef("code") String code, @AttrRef("balance") Long balance)
			throws ConstraintViolationException {
		// generate an code
		super(id, customer, creationDate);
		this.code = nextCode(code);
		this.balance = balance;
		deposit = new ArrayList<>();
		depositCount = 0;
		withdraw = new ArrayList<>();
		withdrawCount = 0;
		transfer = new ArrayList<>();
		transferCount = 0;
	}

	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}

	public String getCode() {
		return code;
	}

	
	
	//-----------------------------------------DEPOSIT----------------------------------------------------------
	
	@DOpt(type = DOpt.Type.LinkAdder)
	// only need to do this for reflexive association:
	// @MemberRef(name="deposit")
	public boolean addDeposit(Deposit s) {
		if (!this.deposit.contains(s)) {
			deposit.add(s);
			//balance += s.getDepositAmount();
		}

		// no other attributes changed
		return true;
	}

	@DOpt(type = DOpt.Type.LinkAdderNew)
	public boolean addNewDeposit(Deposit s) {
		deposit.add(s);
		depositCount++;
		balance += s.getDepositAmount();
		// no other attributes changed
		return true;
	}

	@DOpt(type = DOpt.Type.LinkAdder)
	public boolean addDeposit(Collection<Deposit> deposit) {
		for (Deposit s : deposit) {
			if (!this.deposit.contains(s)) {
				this.deposit.add(s);
				//balance += s.getDepositAmount();
			}
		}

		// no other attributes changed
		return true;
	}

	@DOpt(type = DOpt.Type.LinkAdderNew)
	public boolean addNewDeposit(Collection<Deposit> deposit) {
		this.deposit.addAll(deposit);
		depositCount += deposit.size();
		for(Deposit s: deposit) {
			balance += s.getDepositAmount();
		}

		// no other attributes changed
		return true;
	}

	@DOpt(type = DOpt.Type.LinkRemover)
	// only need to do this for reflexive association:
	// @MemberRef(name="deposit")
	public boolean removeDeposit(Deposit s) {
		boolean removed = deposit.remove(s);

		if (removed) {
			depositCount--;
			balance -= s.getDepositAmount();
		}

		// no other attributes changed
		return true;
	}

	@DOpt(type = DOpt.Type.Setter)
	public void setDeposit(Collection<Deposit> deposit) {
		this.deposit = deposit;

		depositCount = deposit.size();
		for(Deposit s: deposit) {
			balance += s.getDepositAmount();
		}
	}

	/**
	 * @effects return <tt>depositCount</tt>
	 */
	@DOpt(type = DOpt.Type.LinkCountGetter)
	public Integer getDepositCount() {
		return depositCount;
	}

	@DOpt(type = DOpt.Type.LinkCountSetter)
	public void setDepositCount(int count) {
		depositCount = count;
	}

	@DOpt(type = DOpt.Type.Getter)
	public Collection<Deposit> getDeposit() {
		return deposit;
	}
	
	
	
	
	//------------------------------------------WITHDRAW----------------------------------------------------
	
	@DOpt(type = DOpt.Type.LinkAdder)
	// only need to do this for reflexive association:
	// @MemberRef(name="withdraw")
	public boolean addWithdraw(Withdraw s){	
			withdraw.add(s);
//			balance -= s.getWithdrawAmount();
		

		// no other attributes changed
		return true;
	}

	@DOpt(type = DOpt.Type.LinkAdderNew)
	public boolean addNewWithdraw(Withdraw s) throws ConstraintViolationException {
		
		withdraw.add(s);
		withdrawCount++;
		balance -= s.getWithdrawAmount();
		
		// no other attributes changed
		return true;
	}

	@DOpt(type = DOpt.Type.LinkAdder)
	public boolean addWithdraw(Collection<Withdraw> withdraw) {
		for (Withdraw s : withdraw) {
			if (!this.withdraw.contains(s)) {
				this.withdraw.add(s);
//				balance -= s.getWithdrawAmount();
			}
		}

		// no other attributes changed
		return true;
	}

	@DOpt(type = DOpt.Type.LinkAdderNew)
	public boolean addNewWithdraw(Collection<Withdraw> withdraw) {
		this.withdraw.addAll(withdraw);
		withdrawCount += withdraw.size();
		for(Withdraw s: withdraw) {
			balance -= s.getWithdrawAmount();
		}

		// no other attributes changed
		return true;
	}

	@DOpt(type = DOpt.Type.LinkRemover)
	// only need to do this for reflexive association:
	// @MemberRef(name="withdraw")
	public boolean removeWithdraw(Withdraw s) {
		boolean removed = withdraw.remove(s);

		if (removed) {
			withdrawCount--;
			balance += s.getWithdrawAmount();
		}

		// no other attributes changed
		return true;
	}

	@DOpt(type = DOpt.Type.Setter)
	public void setWithdraw(Collection<Withdraw> withdraw) {
		this.withdraw = withdraw;

		withdrawCount = withdraw.size();
		for(Withdraw s: withdraw) {
			balance -= s.getWithdrawAmount();
		}
	}

	/**
	 * @effects return <tt>withdrawCount</tt>
	 */
	@DOpt(type = DOpt.Type.LinkCountGetter)
	public Integer getWithdrawCount() {
		return withdrawCount;
	}

	@DOpt(type = DOpt.Type.LinkCountSetter)
	public void setWithdrawCount(int count) {
		withdrawCount = count;
	}

	@DOpt(type = DOpt.Type.Getter)
	public Collection<Withdraw> getWithdraw() {
		return withdraw;
	}
	
	
	
	//------------------------------------TRANSFER------------------------------------------------------
	@DOpt(type = DOpt.Type.LinkAdder)
	// only need to do this for reflexive association:
	// @MemberRef(name="transfer")
	public boolean addTransfer(Transfer s) {
		if (!this.transfer.contains(s)) {
			transfer.add(s);
			//balance += s.getTransferAmount();
		}

		// no other attributes changed
		return true;
	}

	@DOpt(type = DOpt.Type.LinkAdderNew)
	public boolean addNewTransfer(Transfer s) {
		transfer.add(s);
		transferCount++;
		balance -= s.getTransferAmount();
		s.receiveAccount.balance += s.getTransferAmount();
		// no other attributes changed
		return true;
	}

	@DOpt(type = DOpt.Type.LinkAdder)
	public boolean addTransfer(Collection<Transfer> transfer) {
		for (Transfer s : transfer) {
			if (!this.transfer.contains(s)) {
				this.transfer.add(s);
				//balance += s.getTransferAmount();
			}
		}

		// no other attributes changed
		return true;
	}

	@DOpt(type = DOpt.Type.LinkAdderNew)
	public boolean addNewTransfer(Collection<Transfer> transfer) {
		this.transfer.addAll(transfer);
		transferCount += transfer.size();
		for(Transfer s: transfer) {
			balance -= s.getTransferAmount();
			s.receiveAccount.balance += s.getTransferAmount();
		}

		// no other attributes changed
		return true;
	}

	@DOpt(type = DOpt.Type.LinkRemover)
	// only need to do this for reflexive association:
	// @MemberRef(name="transfer")
	public boolean removeTransfer(Transfer s) {
		boolean removed = transfer.remove(s);

		if (removed) {
			transferCount--;
			balance += s.getTransferAmount();
			s.receiveAccount.balance -= s.getTransferAmount();
		}

		// no other attributes changed
		return true;
	}

	@DOpt(type = DOpt.Type.Setter)
	public void setTransfer(Collection<Transfer> transfer) {
		this.transfer = transfer;

		transferCount = transfer.size();
		for(Transfer s: transfer) {
			balance -= s.getTransferAmount();
			s.receiveAccount.balance += s.getTransferAmount();
		}
	}

	/**
	 * @effects return <tt>transferCount</tt>
	 */
	@DOpt(type = DOpt.Type.LinkCountGetter)
	public Integer getTransferCount() {
		return transferCount;
	}

	@DOpt(type = DOpt.Type.LinkCountSetter)
	public void setTransferCount(int count) {
		transferCount = count;
	}

	@DOpt(type = DOpt.Type.Getter)
	public Collection<Transfer> getTransfer() {
		return transfer;
	}
	
	
	//---------------------------------------------------------------------------------------------------------
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
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
		SpendAccount other = (SpendAccount) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

	// automatically generate the next account code
	private String nextCode(String code) throws ConstraintViolationException {
		if (code == null) { // generate a new code
//			if (codeCounter == 0) {
//				codeCounter = Calendar.getInstance().get(Calendar.YEAR);
//			} else {
//				codeCounter++;
//			}
			codeCounter++;
			String stringIdCounter = String.format("%06d", codeCounter);
			return "SPE" + stringIdCounter;
		} else {
			// update code
			int num;
			try {
				num = Integer.parseInt(code.substring(3));
			} catch (RuntimeException e) {
				throw new ConstraintViolationException(ConstraintViolationException.Code.INVALID_VALUE, e,
						new Object[] { code });
			}

			if (num > codeCounter) {
				codeCounter = num;
			}

			return code;
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
			if (attrib.name().equals("code")) {
				String maxCode = (String) maxVal;
	
				try {
					int maxCodeNum = Integer.parseInt(maxCode.substring(3));
	
					if (maxCodeNum > codeCounter) // extra check
						codeCounter = maxCodeNum;
	
				} catch (RuntimeException e) {
					throw new ConstraintViolationException(ConstraintViolationException.Code.INVALID_VALUE, e,
							new Object[] { maxCode });
				}
			}
		}
	}

}
