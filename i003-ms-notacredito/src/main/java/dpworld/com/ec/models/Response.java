package dpworld.com.ec.models;

import com.fasterxml.jackson.annotation.JsonSetter;

public class Response {

	private String CustomerTransactionId;
	private String DraftTrxNumber;
	private String TransactionNumber;
	private String DocumentNumber;
	private String TransactionDate;
	private String TransactionType;
	private String TransactionSource;
	private String PaymentTerms;
	private String BusinessUnit;
	private String AccountingDate;
	private String N4OperatorID;
	private String N4ComplexID;
	private String N4FacilityID;
	private String N4YardID;
	private String Status;
	private String Error;

	public String getCustomerTransactionId() {
		return CustomerTransactionId;
	}

	@JsonSetter("CustomerTransactionId")
	public void setCustomerTransactionId(String customerTransactionId) {
		CustomerTransactionId = customerTransactionId;
	}

	public String getDraftTrxNumber() {
		return DraftTrxNumber;
	}

	@JsonSetter("DraftTrxNumber")
	public void setDraftTrxNumber(String draftTrxNumber) {
		DraftTrxNumber = draftTrxNumber;
	}

	public String getTransactionNumber() {
		return TransactionNumber;
	}

	@JsonSetter("TransactionNumber")
	public void setTransactionNumber(String transactionNumber) {
		TransactionNumber = transactionNumber;
	}

	public String getDocumentNumber() {
		return DocumentNumber;
	}

	@JsonSetter("DocumentNumber")
	public void setDocumentNumber(String documentNumber) {
		DocumentNumber = documentNumber;
	}

	public String getTransactionDate() {
		return TransactionDate;
	}

	@JsonSetter("TransactionDate")
	public void setTransactionDate(String transactionDate) {
		TransactionDate = transactionDate;
	}

	public String getTransactionType() {
		return TransactionType;
	}

	@JsonSetter("TransactionType")
	public void setTransactionType(String transactionType) {
		TransactionType = transactionType;
	}

	public String getTransactionSource() {
		return TransactionSource;
	}

	@JsonSetter("TransactionSource")
	public void setTransactionSource(String transactionSource) {
		TransactionSource = transactionSource;
	}

	public String getPaymentTerms() {
		return PaymentTerms;
	}

	@JsonSetter("PaymentTerms")
	public void setPaymentTerms(String paymentTerms) {
		PaymentTerms = paymentTerms;
	}

	public String getBusinessUnit() {
		return BusinessUnit;
	}

	@JsonSetter("BusinessUnit")
	public void setBusinessUnit(String businessUnit) {
		BusinessUnit = businessUnit;
	}

	public String getAccountingDate() {
		return AccountingDate;
	}

	@JsonSetter("AccountingDate")
	public void setAccountingDate(String accountingDate) {
		AccountingDate = accountingDate;
	}

	public String getN4OperatorID() {
		return N4OperatorID;
	}

	@JsonSetter("N4OperatorID")
	public void setN4OperatorID(String n4OperatorID) {
		N4OperatorID = n4OperatorID;
	}

	public String getN4ComplexID() {
		return N4ComplexID;
	}

	@JsonSetter("N4ComplexID")
	public void setN4ComplexID(String n4ComplexID) {
		N4ComplexID = n4ComplexID;
	}

	public String getN4FacilityID() {
		return N4FacilityID;
	}

	@JsonSetter("N4FacilityID")
	public void setN4FacilityID(String n4FacilityID) {
		N4FacilityID = n4FacilityID;
	}

	public String getN4YardID() {
		return N4YardID;
	}

	@JsonSetter("N4YardID")
	public void setN4YardID(String n4YardID) {
		N4YardID = n4YardID;
	}

	public String getStatus() {
		return Status;
	}

	@JsonSetter("Status")
	public void setStatus(String status) {
		Status = status;
	}

	public String getError() {
		return Error;
	}

	@JsonSetter("Error")
	public void setError(String error) {
		Error = error;
	}
}
