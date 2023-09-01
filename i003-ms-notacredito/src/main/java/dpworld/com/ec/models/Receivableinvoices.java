package dpworld.com.ec.models;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Receivableinvoices {

	private String Source;                                 
	private String Region;                                  
	private String ConversionDate;                     
	private String ConversionRate;                     
	private String InvoiceCurrencyCode;             
	private String TrxClass;                               
	private String TransactionNumber;               
	private String TransactionDate;                    
	private String TransactionType;                   
	private String TransactionSource;
	private String ParentInvoiceTrxNumber;
	private String AmountApplied;
	private String ApplyDate;
	private String BillToCustomerNumber;
	private String BillToSite;                              
	private String PaymentTerms;                     
	private String LegalEntityIdentifier;               
	private String ConversionRateType;             
	private String FirstPartyRegistrationNumber; 
	private String ShipToCustomerName;           
	private String ShipToCustomerNumber;        
	private String BillingDate;                            
	private String BusinessUnit;                        
	private String AccountingDate;                     
	private String ShipToSite;                            
	private String BillToCustomerName;             
	private String FreightAmount;                      
	private String Carrier;                                  
	private String ShipDate;                              
	private String ShippingReference;                
	private String BillToContact;                        
	private String ShipToContact;                      
	private String PrintOption;                           
	private String DefaultTaxationCountry;          
	private String RemitToAddress;                   
	private String SalesPersonNumber;              
	private String CrossReference;                    
	private String AllowCompletion;                    
	private String BankAccountNumber;             
	private String Comments;                            
	private String ControlCompletionReason;      
	private String CreditCardAuthorizationRequestIdentifier;
	private String CreditCardErrorCode;              
	private String CreditCardErrorText;               
	private String CreditCardExpirationDate;       
	private String CreditCardIssuerCode;            
	private String CreditCardTokenNumber;        
	private String CreditCardVoiceAuthorizationCode;
	private String CustomerTransactionId;          
	private String DeliveryMethod;                     
	private String DocumentFiscalClassification; 
	private String DocumentNumber;                  
	private String DueDate;                               
	private String Email;                                   
	private String Intercompany;                        
	private String InternalNotes;                        
	private String InvoicePrinted;                       
	private String InvoiceStatus;                        
	private String InvoicingRule;                        
	private String LastPrintDate;                        
	private String OriginalPrintDate;                   
	private String PayingCustomerAccount;        
	private String PayingCustomerName;           
	private String PayingCustomerSite ;             
	private String Prepayment;                          
	private String PurchaseOrder;                      
	private String PurchaseOrderDate;               
	private String PurchaseOrderRevision;         
	private String ReceiptMethod;                      
	private String SoldToPartyNumber;              
	private String SpecialInstructions;                
	private String StructuredPaymentReference; 
	private String ThirdPartyRegistrationNumber;
	private String ParentInvoiceTrxType;
	private List<Lines> Lines;
	private List<InvoiceDFF> InvoiceDFF;

	@JsonGetter("Source")
	public String getSource() {
		return Source;
	}

	public void setSource(String source) {
		Source = source;
	}

	@JsonGetter("Region")
	public String getRegion() {
		return Region;
	}

	public void setRegion(String region) {
		Region = region;
	}

	@JsonGetter("ConversionDate")
	public String getConversionDate() {
		return ConversionDate;
	}

	public void setConversionDate(String conversionDate) {
		ConversionDate = conversionDate;
	}

	@JsonGetter("ConversionRate")
	public String getConversionRate() {
		return ConversionRate;
	}

	public void setConversionRate(String conversionRate) {
		ConversionRate = conversionRate;
	}

	@JsonGetter("InvoiceCurrencyCode")
	public String getInvoiceCurrencyCode() {
		return InvoiceCurrencyCode;
	}

	public void setInvoiceCurrencyCode(String invoiceCurrencyCode) {
		InvoiceCurrencyCode = invoiceCurrencyCode;
	}

	@JsonGetter("TrxClass")
	public String getTrxClass() {
		return TrxClass;
	}

	public void setTrxClass(String trxClass) {
		TrxClass = trxClass;
	}

	@JsonGetter("TransactionNumber")
	public String getTransactionNumber() {
		return TransactionNumber;
	}

	public void setTransactionNumber(String transactionNumber) {
		TransactionNumber = transactionNumber;
	}

	@JsonGetter("TransactionDate")
	public String getTransactionDate() {
		return TransactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		TransactionDate = transactionDate;
	}

	@JsonGetter("TransactionType")
	public String getTransactionType() {
		return TransactionType;
	}

	public void setTransactionType(String transactionType) {
		TransactionType = transactionType;
	}

	@JsonGetter("TransactionSource")
	public String getTransactionSource() {
		return TransactionSource;
	}

	public void setTransactionSource(String transactionSource) {
		TransactionSource = transactionSource;
	}

	@JsonGetter("BillToCustomerNumber")
	public String getBillToCustomerNumber() {
		return BillToCustomerNumber;
	}

	public void setBillToCustomerNumber(String billToCustomerNumber) {
		BillToCustomerNumber = billToCustomerNumber;
	}

	@JsonGetter("BillToSite")
	public String getBillToSite() {
		return BillToSite;
	}

	public void setBillToSite(String billToSite) {
		BillToSite = billToSite;
	}

	@JsonGetter("PaymentTerms")
	public String getPaymentTerms() {
		return PaymentTerms;
	}

	public void setPaymentTerms(String paymentTerms) {
		PaymentTerms = paymentTerms;
	}

	@JsonGetter("LegalEntityIdentifier")
	public String getLegalEntityIdentifier() {
		return LegalEntityIdentifier;
	}

	public void setLegalEntityIdentifier(String legalEntityIdentifier) {
		LegalEntityIdentifier = legalEntityIdentifier;
	}

	@JsonGetter("ConversionRateType")
	public String getConversionRateType() {
		return ConversionRateType;
	}

	public void setConversionRateType(String conversionRateType) {
		ConversionRateType = conversionRateType;
	}

	@JsonGetter("FirstPartyRegistrationNumber")
	public String getFirstPartyRegistrationNumber() {
		return FirstPartyRegistrationNumber;
	}

	public void setFirstPartyRegistrationNumber(String firstPartyRegistrationNumber) {
		FirstPartyRegistrationNumber = firstPartyRegistrationNumber;
	}

	@JsonGetter("ShipToCustomerName")
	public String getShipToCustomerName() {
		return ShipToCustomerName;
	}

	public void setShipToCustomerName(String shipToCustomerName) {
		ShipToCustomerName = shipToCustomerName;
	}

	@JsonGetter("ShipToCustomerNumber")
	public String getShipToCustomerNumber() {
		return ShipToCustomerNumber;
	}

	public void setShipToCustomerNumber(String shipToCustomerNumber) {
		ShipToCustomerNumber = shipToCustomerNumber;
	}

	@JsonGetter("BillingDate")
	public String getBillingDate() {
		return BillingDate;
	}

	public void setBillingDate(String billingDate) {
		BillingDate = billingDate;
	}

	@JsonGetter("BusinessUnit")
	public String getBusinessUnit() {
		return BusinessUnit;
	}

	public void setBusinessUnit(String businessUnit) {
		BusinessUnit = businessUnit;
	}

	@JsonGetter("AccountingDate")
	public String getAccountingDate() {
		return AccountingDate;
	}

	public void setAccountingDate(String accountingDate) {
		AccountingDate = accountingDate;
	}

	@JsonGetter("ShipToSite")
	public String getShipToSite() {
		return ShipToSite;
	}

	public void setShipToSite(String shipToSite) {
		ShipToSite = shipToSite;
	}

	@JsonGetter("BillToCustomerName")
	public String getBillToCustomerName() {
		return BillToCustomerName;
	}

	public void setBillToCustomerName(String billToCustomerName) {
		BillToCustomerName = billToCustomerName;
	}

	@JsonGetter("FreightAmount")
	public String getFreightAmount() {
		return FreightAmount;
	}

	public void setFreightAmount(String freightAmount) {
		FreightAmount = freightAmount;
	}

	@JsonGetter("Carrier")
	public String getCarrier() {
		return Carrier;
	}

	public void setCarrier(String carrier) {
		Carrier = carrier;
	}

	@JsonGetter("ShipDate")
	public String getShipDate() {
		return ShipDate;
	}

	public void setShipDate(String shipDate) {
		ShipDate = shipDate;
	}

	@JsonGetter("ShippingReference")
	public String getShippingReference() {
		return ShippingReference;
	}

	public void setShippingReference(String shippingReference) {
		ShippingReference = shippingReference;
	}

	@JsonGetter("BillToContact")
	public String getBillToContact() {
		return BillToContact;
	}

	public void setBillToContact(String billToContact) {
		BillToContact = billToContact;
	}

	@JsonGetter("ShipToContact")
	public String getShipToContact() {
		return ShipToContact;
	}

	public void setShipToContact(String shipToContact) {
		ShipToContact = shipToContact;
	}

	@JsonGetter("PrintOption")
	public String getPrintOption() {
		return PrintOption;
	}

	public void setPrintOption(String printOption) {
		PrintOption = printOption;
	}

	@JsonGetter("DefaultTaxationCountry")
	public String getDefaultTaxationCountry() {
		return DefaultTaxationCountry;
	}

	public void setDefaultTaxationCountry(String defaultTaxationCountry) {
		DefaultTaxationCountry = defaultTaxationCountry;
	}

	@JsonGetter("RemitToAddress")
	public String getRemitToAddress() {
		return RemitToAddress;
	}

	public void setRemitToAddress(String remitToAddress) {
		RemitToAddress = remitToAddress;
	}

	@JsonGetter("SalesPersonNumber")
	public String getSalesPersonNumber() {
		return SalesPersonNumber;
	}

	public void setSalesPersonNumber(String salesPersonNumber) {
		SalesPersonNumber = salesPersonNumber;
	}

	@JsonGetter("CrossReference")
	public String getCrossReference() {
		return CrossReference;
	}

	public void setCrossReference(String crossReference) {
		CrossReference = crossReference;
	}

	@JsonGetter("AllowCompletion")
	public String getAllowCompletion() {
		return AllowCompletion;
	}

	public void setAllowCompletion(String allowCompletion) {
		AllowCompletion = allowCompletion;
	}

	@JsonGetter("BankAccountNumber")
	public String getBankAccountNumber() {
		return BankAccountNumber;
	}

	public void setBankAccountNumber(String bankAccountNumber) {
		BankAccountNumber = bankAccountNumber;
	}

	@JsonGetter("Comments")
	public String getComments() {
		return Comments;
	}

	public void setComments(String comments) {
		Comments = comments;
	}

	@JsonGetter("ControlCompletionReason")
	public String getControlCompletionReason() {
		return ControlCompletionReason;
	}

	public void setControlCompletionReason(String controlCompletionReason) {
		ControlCompletionReason = controlCompletionReason;
	}

	@JsonGetter("CreditCardAuthorizationRequestIdentifier")
	public String getCreditCardAuthorizationRequestIdentifier() {
		return CreditCardAuthorizationRequestIdentifier;
	}

	public void setCreditCardAuthorizationRequestIdentifier(String creditCardAuthorizationRequestIdentifier) {
		CreditCardAuthorizationRequestIdentifier = creditCardAuthorizationRequestIdentifier;
	}

	@JsonGetter("CreditCardErrorCode")
	public String getCreditCardErrorCode() {
		return CreditCardErrorCode;
	}

	public void setCreditCardErrorCode(String creditCardErrorCode) {
		CreditCardErrorCode = creditCardErrorCode;
	}

	@JsonGetter("CreditCardErrorText")
	public String getCreditCardErrorText() {
		return CreditCardErrorText;
	}

	public void setCreditCardErrorText(String creditCardErrorText) {
		CreditCardErrorText = creditCardErrorText;
	}

	@JsonGetter("CreditCardExpirationDate")
	public String getCreditCardExpirationDate() {
		return CreditCardExpirationDate;
	}

	public void setCreditCardExpirationDate(String creditCardExpirationDate) {
		CreditCardExpirationDate = creditCardExpirationDate;
	}

	@JsonGetter("CreditCardIssuerCode")
	public String getCreditCardIssuerCode() {
		return CreditCardIssuerCode;
	}

	public void setCreditCardIssuerCode(String creditCardIssuerCode) {
		CreditCardIssuerCode = creditCardIssuerCode;
	}

	@JsonGetter("CreditCardTokenNumber")
	public String getCreditCardTokenNumber() {
		return CreditCardTokenNumber;
	}

	public void setCreditCardTokenNumber(String creditCardTokenNumber) {
		CreditCardTokenNumber = creditCardTokenNumber;
	}

	@JsonGetter("CreditCardVoiceAuthorizationCode")
	public String getCreditCardVoiceAuthorizationCode() {
		return CreditCardVoiceAuthorizationCode;
	}

	public void setCreditCardVoiceAuthorizationCode(String creditCardVoiceAuthorizationCode) {
		CreditCardVoiceAuthorizationCode = creditCardVoiceAuthorizationCode;
	}

	@JsonGetter("CustomerTransactionId")
	public String getCustomerTransactionId() {
		return CustomerTransactionId;
	}

	public void setCustomerTransactionId(String customerTransactionId) {
		CustomerTransactionId = customerTransactionId;
	}

	@JsonGetter("DeliveryMethod")
	public String getDeliveryMethod() {
		return DeliveryMethod;
	}

	public void setDeliveryMethod(String deliveryMethod) {
		DeliveryMethod = deliveryMethod;
	}

	@JsonGetter("DocumentFiscalClassification")
	public String getDocumentFiscalClassification() {
		return DocumentFiscalClassification;
	}

	public void setDocumentFiscalClassification(String documentFiscalClassification) {
		DocumentFiscalClassification = documentFiscalClassification;
	}

	@JsonGetter("DocumentNumber")
	public String getDocumentNumber() {
		return DocumentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		DocumentNumber = documentNumber;
	}

	@JsonGetter("DueDate")
	public String getDueDate() {
		return DueDate;
	}

	public void setDueDate(String dueDate) {
		DueDate = dueDate;
	}

	@JsonGetter("Email")
	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	@JsonGetter("Intercompany")
	public String getIntercompany() {
		return Intercompany;
	}

	public void setIntercompany(String intercompany) {
		Intercompany = intercompany;
	}

	@JsonGetter("InternalNotes")
	public String getInternalNotes() {
		return InternalNotes;
	}

	public void setInternalNotes(String internalNotes) {
		InternalNotes = internalNotes;
	}

	@JsonGetter("InvoicePrinted")
	public String getInvoicePrinted() {
		return InvoicePrinted;
	}

	public void setInvoicePrinted(String invoicePrinted) {
		InvoicePrinted = invoicePrinted;
	}

	@JsonGetter("InvoiceStatus")
	public String getInvoiceStatus() {
		return InvoiceStatus;
	}

	public void setInvoiceStatus(String invoiceStatus) {
		InvoiceStatus = invoiceStatus;
	}

	@JsonGetter("InvoicingRule")
	public String getInvoicingRule() {
		return InvoicingRule;
	}

	public void setInvoicingRule(String invoicingRule) {
		InvoicingRule = invoicingRule;
	}

	@JsonGetter("LastPrintDate")
	public String getLastPrintDate() {
		return LastPrintDate;
	}

	public void setLastPrintDate(String lastPrintDate) {
		LastPrintDate = lastPrintDate;
	}

	@JsonGetter("OriginalPrintDate")
	public String getOriginalPrintDate() {
		return OriginalPrintDate;
	}

	public void setOriginalPrintDate(String originalPrintDate) {
		OriginalPrintDate = originalPrintDate;
	}

	@JsonGetter("PayingCustomerAccount")
	public String getPayingCustomerAccount() {
		return PayingCustomerAccount;
	}

	public void setPayingCustomerAccount(String payingCustomerAccount) {
		PayingCustomerAccount = payingCustomerAccount;
	}

	@JsonGetter("PayingCustomerName")
	public String getPayingCustomerName() {
		return PayingCustomerName;
	}

	public void setPayingCustomerName(String payingCustomerName) {
		PayingCustomerName = payingCustomerName;
	}

	@JsonGetter("PayingCustomerSite")
	public String getPayingCustomerSite() {
		return PayingCustomerSite;
	}

	public void setPayingCustomerSite(String payingCustomerSite) {
		PayingCustomerSite = payingCustomerSite;
	}

	@JsonGetter("Prepayment")
	public String getPrepayment() {
		return Prepayment;
	}

	public void setPrepayment(String prepayment) {
		Prepayment = prepayment;
	}

	@JsonGetter("PurchaseOrder")
	public String getPurchaseOrder() {
		return PurchaseOrder;
	}

	public void setPurchaseOrder(String purchaseOrder) {
		PurchaseOrder = purchaseOrder;
	}

	@JsonGetter("PurchaseOrderDate")
	public String getPurchaseOrderDate() {
		return PurchaseOrderDate;
	}

	public void setPurchaseOrderDate(String purchaseOrderDate) {
		PurchaseOrderDate = purchaseOrderDate;
	}

	@JsonGetter("PurchaseOrderRevision")
	public String getPurchaseOrderRevision() {
		return PurchaseOrderRevision;
	}

	public void setPurchaseOrderRevision(String purchaseOrderRevision) {
		PurchaseOrderRevision = purchaseOrderRevision;
	}

	@JsonGetter("ReceiptMethod")
	public String getReceiptMethod() {
		return ReceiptMethod;
	}

	public void setReceiptMethod(String receiptMethod) {
		ReceiptMethod = receiptMethod;
	}

	@JsonGetter("SoldToPartyNumber")
	public String getSoldToPartyNumber() {
		return SoldToPartyNumber;
	}

	public void setSoldToPartyNumber(String soldToPartyNumber) {
		SoldToPartyNumber = soldToPartyNumber;
	}

	@JsonGetter("SpecialInstructions")
	public String getSpecialInstructions() {
		return SpecialInstructions;
	}

	public void setSpecialInstructions(String specialInstructions) {
		SpecialInstructions = specialInstructions;
	}

	@JsonGetter("StructuredPaymentReference")
	public String getStructuredPaymentReference() {
		return StructuredPaymentReference;
	}

	public void setStructuredPaymentReference(String structuredPaymentReference) {
		StructuredPaymentReference = structuredPaymentReference;
	}

	@JsonGetter("ThirdPartyRegistrationNumber")
	public String getThirdPartyRegistrationNumber() {
		return ThirdPartyRegistrationNumber;
	}

	public void setThirdPartyRegistrationNumber(String thirdPartyRegistrationNumber) {
		ThirdPartyRegistrationNumber = thirdPartyRegistrationNumber;
	}

	@JsonGetter("Lines")
	public List<dpworld.com.ec.models.Lines> getLines() {
		return Lines;
	}

	public void setLines(List<dpworld.com.ec.models.Lines> lines) {
		Lines = lines;
	}

	@JsonGetter("InvoiceDFF")
	public List<dpworld.com.ec.models.InvoiceDFF> getInvoiceDFF() {
		return InvoiceDFF;
	}

	public void setInvoiceDFF(List<dpworld.com.ec.models.InvoiceDFF> invoiceDFF) {
		InvoiceDFF = invoiceDFF;
	}

	@JsonProperty("ParentInvoiceTrxNumber(for CM use only)")
	public String getParentInvoiceTrxNumber() {
		return ParentInvoiceTrxNumber;
	}

	public void setParentInvoiceTrxNumber(String parentInvoiceTrxNumber) {
		ParentInvoiceTrxNumber = parentInvoiceTrxNumber;
	}

	@JsonProperty("AmountApplied(for CM use only)")
	public String getAmountApplied() {
		return AmountApplied;
	}

	public void setAmountApplied(String amountApplied) {
		AmountApplied = amountApplied;
	}

	@JsonProperty("ApplyDate(for CM use only)")
	public String getApplyDate() {
		return ApplyDate;
	}

	public void setApplyDate(String applyDate) {
		ApplyDate = applyDate;
	}

	@JsonProperty("ParentInvoiceTrxType(for CM use only)")
	public String getParentInvoiceTrxType() {
		return ParentInvoiceTrxType;
	}

	public void setParentInvoiceTrxType(String parentInvoiceTrxType) {
		ParentInvoiceTrxType = parentInvoiceTrxType;
	}

}
