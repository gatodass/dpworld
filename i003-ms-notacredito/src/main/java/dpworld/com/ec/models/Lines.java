package dpworld.com.ec.models;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.List;

public class Lines {

	private String LineNumber;
	private String Description;
	private String Quantity;
	private String UnitSellingPrice;
	private String MemoLine;
	private String UnitOfMeasure;
	private String ItemNumber;
	private String TaxInvoiceDate;
	private String TaxInvoiceNumber;
	private String TaxExemptionCertificateNumber;
	private String TaxExemptionHandling;
	private String TaxExemptionReason;
	private String TaxClassificationCode;
	private String TransacationBusinessCategory;
	private String ProductFiscalClassification;
	private String ProductCategory;
	private String ProductType;
	private String AssessableValue;
	private String AccountingRule;
	private String AccountingRuleDuration;
	private String AllocatedFreightAmount;
	private String CustomerTransactionLineId;
	private String LineAmount;
	private String LineAmountIncludesTax;
	private String LineIntendedUse;
	private String RuleEndDate;
	private String RuleStartDate;
	private String SalesOrder;
	private String SalesOrderDate;
	private String UserDefinedFiscalClassification;
	private String Warehouse;
	private List<TaxLines> TaxLines;
	private List<InvoiceLineDFF> InvoiceLineDFF;

	@JsonGetter("LineNumber")
	public String getLineNumber() {
		return LineNumber;
	}

	public void setLineNumber(String lineNumber) {
		LineNumber = lineNumber;
	}

	@JsonGetter("Description")
	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	@JsonGetter("Quantity")
	public String getQuantity() {
		return Quantity;
	}

	public void setQuantity(String quantity) {
		Quantity = quantity;
	}

	@JsonGetter("UnitSellingPrice")
	public String getUnitSellingPrice() {
		return UnitSellingPrice;
	}

	public void setUnitSellingPrice(String unitSellingPrice) {
		UnitSellingPrice = unitSellingPrice;
	}

	@JsonGetter("MemoLine")
	public String getMemoLine() {
		return MemoLine;
	}

	public void setMemoLine(String memoLine) {
		MemoLine = memoLine;
	}

	@JsonGetter("UnitOfMeasure")
	public String getUnitOfMeasure() {
		return UnitOfMeasure;
	}

	public void setUnitOfMeasure(String unitOfMeasure) {
		UnitOfMeasure = unitOfMeasure;
	}

	@JsonGetter("ItemNumber")
	public String getItemNumber() {
		return ItemNumber;
	}

	public void setItemNumber(String itemNumber) {
		ItemNumber = itemNumber;
	}

	@JsonGetter("TaxInvoiceDate")
	public String getTaxInvoiceDate() {
		return TaxInvoiceDate;
	}

	public void setTaxInvoiceDate(String taxInvoiceDate) {
		TaxInvoiceDate = taxInvoiceDate;
	}

	@JsonGetter("TaxInvoiceNumber")
	public String getTaxInvoiceNumber() {
		return TaxInvoiceNumber;
	}

	public void setTaxInvoiceNumber(String taxInvoiceNumber) {
		TaxInvoiceNumber = taxInvoiceNumber;
	}

	@JsonGetter("TaxExemptionCertificateNumber")
	public String getTaxExemptionCertificateNumber() {
		return TaxExemptionCertificateNumber;
	}

	public void setTaxExemptionCertificateNumber(String taxExemptionCertificateNumber) {
		TaxExemptionCertificateNumber = taxExemptionCertificateNumber;
	}

	@JsonGetter("TaxExemptionHandling")
	public String getTaxExemptionHandling() {
		return TaxExemptionHandling;
	}

	public void setTaxExemptionHandling(String taxExemptionHandling) {
		TaxExemptionHandling = taxExemptionHandling;
	}

	@JsonGetter("TaxExemptionReason")
	public String getTaxExemptionReason() {
		return TaxExemptionReason;
	}

	public void setTaxExemptionReason(String taxExemptionReason) {
		TaxExemptionReason = taxExemptionReason;
	}

	@JsonGetter("TaxClassificationCode")
	public String getTaxClassificationCode() {
		return TaxClassificationCode;
	}

	public void setTaxClassificationCode(String taxClassificationCode) {
		TaxClassificationCode = taxClassificationCode;
	}

	@JsonGetter("TransacationBusinessCategory")
	public String getTransacationBusinessCategory() {
		return TransacationBusinessCategory;
	}

	public void setTransacationBusinessCategory(String transacationBusinessCategory) {
		TransacationBusinessCategory = transacationBusinessCategory;
	}

	@JsonGetter("ProductFiscalClassification")
	public String getProductFiscalClassification() {
		return ProductFiscalClassification;
	}

	public void setProductFiscalClassification(String productFiscalClassification) {
		ProductFiscalClassification = productFiscalClassification;
	}

	@JsonGetter("ProductCategory")
	public String getProductCategory() {
		return ProductCategory;
	}

	public void setProductCategory(String productCategory) {
		ProductCategory = productCategory;
	}

	@JsonGetter("ProductType")
	public String getProductType() {
		return ProductType;
	}

	public void setProductType(String productType) {
		ProductType = productType;
	}

	@JsonGetter("AssessableValue")
	public String getAssessableValue() {
		return AssessableValue;
	}

	public void setAssessableValue(String assessableValue) {
		AssessableValue = assessableValue;
	}

	@JsonGetter("AccountingRule")
	public String getAccountingRule() {
		return AccountingRule;
	}

	public void setAccountingRule(String accountingRule) {
		AccountingRule = accountingRule;
	}

	@JsonGetter("AccountingRuleDuration")
	public String getAccountingRuleDuration() {
		return AccountingRuleDuration;
	}

	public void setAccountingRuleDuration(String accountingRuleDuration) {
		AccountingRuleDuration = accountingRuleDuration;
	}

	@JsonGetter("AllocatedFreightAmount")
	public String getAllocatedFreightAmount() {
		return AllocatedFreightAmount;
	}

	public void setAllocatedFreightAmount(String allocatedFreightAmount) {
		AllocatedFreightAmount = allocatedFreightAmount;
	}

	@JsonGetter("CustomerTransactionLineId")
	public String getCustomerTransactionLineId() {
		return CustomerTransactionLineId;
	}

	public void setCustomerTransactionLineId(String customerTransactionLineId) {
		CustomerTransactionLineId = customerTransactionLineId;
	}

	@JsonGetter("LineAmount")
	public String getLineAmount() {
		return LineAmount;
	}

	public void setLineAmount(String lineAmount) {
		LineAmount = lineAmount;
	}

	@JsonGetter("LineAmountIncludesTax")
	public String getLineAmountIncludesTax() {
		return LineAmountIncludesTax;
	}

	public void setLineAmountIncludesTax(String lineAmountIncludesTax) {
		LineAmountIncludesTax = lineAmountIncludesTax;
	}

	@JsonGetter("LineIntendedUse")
	public String getLineIntendedUse() {
		return LineIntendedUse;
	}

	public void setLineIntendedUse(String lineIntendedUse) {
		LineIntendedUse = lineIntendedUse;
	}

	@JsonGetter("RuleEndDate")
	public String getRuleEndDate() {
		return RuleEndDate;
	}

	public void setRuleEndDate(String ruleEndDate) {
		RuleEndDate = ruleEndDate;
	}

	@JsonGetter("RuleStartDate")
	public String getRuleStartDate() {
		return RuleStartDate;
	}

	public void setRuleStartDate(String ruleStartDate) {
		RuleStartDate = ruleStartDate;
	}

	@JsonGetter("SalesOrder")
	public String getSalesOrder() {
		return SalesOrder;
	}

	public void setSalesOrder(String salesOrder) {
		SalesOrder = salesOrder;
	}

	@JsonGetter("SalesOrderDate")
	public String getSalesOrderDate() {
		return SalesOrderDate;
	}

	public void setSalesOrderDate(String salesOrderDate) {
		SalesOrderDate = salesOrderDate;
	}

	@JsonGetter("UserDefinedFiscalClassification")
	public String getUserDefinedFiscalClassification() {
		return UserDefinedFiscalClassification;
	}

	public void setUserDefinedFiscalClassification(String userDefinedFiscalClassification) {
		UserDefinedFiscalClassification = userDefinedFiscalClassification;
	}

	@JsonGetter("Warehouse")
	public String getWarehouse() {
		return Warehouse;
	}

	public void setWarehouse(String warehouse) {
		Warehouse = warehouse;
	}

	@JsonGetter("TaxLines")
	public List<dpworld.com.ec.models.TaxLines> getTaxLines() {
		return TaxLines;
	}

	public void setTaxLines(List<dpworld.com.ec.models.TaxLines> taxLines) {
		TaxLines = taxLines;
	}

	@JsonGetter("InvoiceLineDFF")
	public List<dpworld.com.ec.models.InvoiceLineDFF> getInvoiceLineDFF() {
		return InvoiceLineDFF;
	}

	public void setInvoiceLineDFF(List<dpworld.com.ec.models.InvoiceLineDFF> invoiceLineDFF) {
		InvoiceLineDFF = invoiceLineDFF;
	}
}
