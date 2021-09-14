package exceptions;

import java.text.MessageFormat;
import domainapp.basics.util.InfoCode;

/**
 * @overview Capture domain-specific exception codes.
 * 
 *           <p>
 *           <b>Enum value format</b>: <tt>VALUE("...{0}...{1}...")</tt> where
 *           <t>{0}, {1}, ....</tt> are template variables that will be replaced
 *           by run-time values
 * 
 *           <p>
 *           <b>Example</b>:
 * 
 *           <br>
 *           enum value: INVALID_DOB("Date of birth {0} is not a valid date")
 * 
 *           <br>
 *           usage:
 * 
 *           <pre>
 * // an error discovered in some code that validates a dob would throw this exception:
 * 
 *    throw new ConstraintViolationException(INVALID_DOB, dob)
 *           </pre>
 * 
 *           Here, <tt>dob.toString()</tt> is the run-time value that replaces
 *           the template variable {0} in the enum INVALID_DOB.
 * 
 * @author Duc Minh Le (ducmle)
 *
 */
public enum DExCode implements InfoCode {

	/**
	 * 0: date of birth
	 */
	INVALID_DOB("Date of birth {0} is not a valid date"),
	
	/**
	 * 0: time
	 */
	INVALID_TIME("Time {0} is not a valid date"),
	
	/**
	 * 0: creationDate
	 */
	INVALID_CREATION_DATE("Creation date {0} is not a valid date"),
	
	/**
	 * 0: finalization
	 */
	INVALID_FINALIZATION("You can finalize only after due date: {0}"),
	
	/**
	 * 0: periodic repayment
	 */
	INVALID_REPAYMENT("You have already paid off all your loan "),
	
	/**
	 * 0: repayment amount
	 */
	INVALID_REPAYMENT_AMOUNT("Repayment Amount must <= Credit Balance : {0}"),
	
	/**
	 * 0: repayment amount
	 */
	INVALID_SPENDING("Spending make credit balance exceed limit : {0}"),
	
	/**
	 * 0: loan amount
	 */
	INVALID_LOAN_AMOUNT("Loan Amount {0} exceeds the limit {1}"),
	
	/**
	 * 0: receive account
	 */
	INVALID_RECEIVE_ACCOUNT("SpendAccount and ReceiveAccount cannot be the same, please choose another ReceiveAccount"),
	
	/**
	 * 0: withdraw amount
	 */
	INVALID_WITHDRAW_AMOUNT("Withdraw amount {0} is not allowed (balance must >= 100000 for maintenance fee)"),
	
	/**
	 * 0: transfer amount
	 */
	INVALID_TRANSFER_AMOUNT("Transfer amount {0} is not allowed (balance of SpendAccount must >= 100000 for maintenance fee)");
	
	

	/**
	 * THE FOLLOWING CODE (EXCEPT FOR THE CONSTRUCTOR NAME) MUST BE KEPT AS IS
	 */
	private String text;

	/**
	 * The {@link MessageFormat} object for formatting {@link #text} using
	 * context-specific data arguments
	 */
	private MessageFormat messageFormat;

	private DExCode(String text) {
		this.text = text;
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public MessageFormat getMessageFormat() {
		if (messageFormat == null) {
			messageFormat = new MessageFormat(text);
		}

		return messageFormat;
	}
}
