package dpworld.com.ec.models;

import com.fasterxml.jackson.annotation.JsonGetter;

public class TaxLines {

	private String CustomerTransactionLineId;
	private String TaxJurisdictionCode;
	private String TaxRate;
	private String TaxRateCode;
	private String TaxRegimeCode;
	private String TaxStatusCode;
	private String Tax;
	private String TaxAmount;
	private String TaxInclusiveIndicator;

	@JsonGetter("CustomerTransactionLineId")
	public String getCustomerTransactionLineId() {
		return CustomerTransactionLineId;
	}

	public void setCustomerTransactionLineId(String customerTransactionLineId) {
		CustomerTransactionLineId = customerTransactionLineId;
	}

	@JsonGetter("TaxJurisdictionCode")
	public String getTaxJurisdictionCode() {
		return TaxJurisdictionCode;
	}

	public void setTaxJurisdictionCode(String taxJurisdictionCode) {
		TaxJurisdictionCode = taxJurisdictionCode;
	}

	@JsonGetter("TaxRate")
	public String getTaxRate() {
		return TaxRate;
	}

	public void setTaxRate(String taxRate) {
		TaxRate = taxRate;
	}

	@JsonGetter("TaxRateCode")
	public String getTaxRateCode() {
		return TaxRateCode;
	}

	public void setTaxRateCode(String taxRateCode) {
		TaxRateCode = taxRateCode;
	}

	@JsonGetter("TaxRegimeCode")
	public String getTaxRegimeCode() {
		return TaxRegimeCode;
	}

	public void setTaxRegimeCode(String taxRegimeCode) {
		TaxRegimeCode = taxRegimeCode;
	}

	@JsonGetter("TaxStatusCode")
	public String getTaxStatusCode() {
		return TaxStatusCode;
	}

	public void setTaxStatusCode(String taxStatusCode) {
		TaxStatusCode = taxStatusCode;
	}

	@JsonGetter("Tax")
	public String getTax() {
		return Tax;
	}

	public void setTax(String tax) {
		Tax = tax;
	}

	@JsonGetter("TaxAmount")
	public String getTaxAmount() {
		return TaxAmount;
	}

	public void setTaxAmount(String taxAmount) {
		TaxAmount = taxAmount;
	}

	@JsonGetter("TaxInclusiveIndicator")
	public String getTaxInclusiveIndicator() {
		return TaxInclusiveIndicator;
	}

	public void setTaxInclusiveIndicator(String taxInclusiveIndicator) {
		TaxInclusiveIndicator = taxInclusiveIndicator;
	}
}
