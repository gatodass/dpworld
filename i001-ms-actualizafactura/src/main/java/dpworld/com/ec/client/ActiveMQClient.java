package dpworld.com.ec.client;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dpworld.com.ec.models.Factura;
import dpworld.com.ec.models.InvoiceDFF;
import dpworld.com.ec.models.InvoiceLineDFF;
import dpworld.com.ec.models.Lines;
import dpworld.com.ec.models.Receivableinvoices;
import dpworld.com.ec.models.TaxLines;
import dpworld.com.ec.service.IFacturaService;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ActiveMQClient {

    @Autowired
    private IFacturaService iFacturaService;

	@JmsListener(destination = "N4INVOICES")
	public void processMessage(String content) {

        try {

            JSONObject xmlJSONObj = XML.toJSONObject(content, true);

            JSONObject invoice = xmlJSONObj.getJSONObject("invoice");

            List<Receivableinvoices> listaReceivableinvoices = this.obtenerReceivableinvoices(invoice);

            Factura factura = new Factura();
            factura.setReceivableinvoices(listaReceivableinvoices);

            iFacturaService.facturaCobrar(factura);

        } catch (JSONException je) {

            System.out.println(je.toString());

        }

	}

    private List<Receivableinvoices> obtenerReceivableinvoices(JSONObject jsonObject){

        List<Receivableinvoices> lista = new ArrayList<>();

        Receivableinvoices receivableinvoices = new Receivableinvoices();

        receivableinvoices.setSource(jsonObject.getString("source"));
        receivableinvoices.setRegion("AMR-EC");
        receivableinvoices.setInvoiceCurrencyCode(jsonObject.getString("currency"));
        receivableinvoices.setTrxClass("INV");
        receivableinvoices.setTransactionDate(jsonObject.getString("create"));
        receivableinvoices.setTransactionType(jsonObject.getString("InvoiceType"));

        if(jsonObject.getString("source").equals("N4")){
            receivableinvoices.setTransactionSource("EC_N4_BILLING");
        } else if (jsonObject.getString("source").equals("GEKO")) {
            receivableinvoices.setTransactionSource("EC_GEKO_BILLING");
        }

        receivableinvoices.setBillToCustomerNumber(jsonObject.getString("customerId"));
        receivableinvoices.setBusinessUnit(jsonObject.getString("BusinessUnit"));
        receivableinvoices.setAccountingDate(jsonObject.getString("create"));
        receivableinvoices.setDefaultTaxationCountry("EC");
        receivableinvoices.setRemitToAddress("");
        //receivableinvoices.setEmail("");
        receivableinvoices.setLines(this.obtenerLines(jsonObject));
        receivableinvoices.setInvoiceDFF(this.obtenerInvoiceDFF(jsonObject));

        lista.add(receivableinvoices);

        return lista;

    }

    private List<Lines> obtenerLines(JSONObject jsonObject){

        List<Lines> lista = new ArrayList<>();

        JSONObject additional = jsonObject.getJSONObject("additional");

        JSONObject details = jsonObject.getJSONObject("details");
        JSONArray invoiceItems = details.getJSONArray("invoiceItem");

        for (int i = 0; i < invoiceItems.length(); i++) {

            JSONObject invoiceItem = invoiceItems.getJSONObject(i);

            Lines lines = new Lines();

            lines.setLineNumber(String.valueOf(i+1));
            lines.setDescription(invoiceItem.getString("description"));
            lines.setQuantity(invoiceItem.getString("quantity"));
            lines.setUnitSellingPrice(invoiceItem.getString("amount"));
            lines.setMemoLine(invoiceItem.getString("tariff"));
            lines.setTaxClassificationCode(additional.getString("TaxCode"));
            lines.setLineAmount(invoiceItem.getString("TotalAmount"));

            List<TaxLines> ListaTaxLines = new ArrayList<>();
            TaxLines taxLines = new TaxLines();
            ListaTaxLines.add(taxLines);

            lines.setTaxLines(ListaTaxLines);
            lines.setInvoiceLineDFF(this.obtenerInvoiceLineDFF(jsonObject));

            lista.add(lines);

        }

        return lista;
    }

    private List<InvoiceDFF> obtenerInvoiceDFF(JSONObject jsonObject){

        JSONObject additional = jsonObject.getJSONObject("additional");

        List<InvoiceDFF> lista = new ArrayList<>();

        InvoiceDFF invoiceDFF = new InvoiceDFF();
        invoiceDFF.setInvoiceTransactionsFlexfield_Context("Ecuador");
        invoiceDFF.setInvoiceTransactionsFlexfield_Segment1(jsonObject.getString("draftId"));
        invoiceDFF.setInvoiceTransactionsFlexfield_Segment2("SENT");
        invoiceDFF.setInvoiceTransactionsFlexfield_Segment3("");
        invoiceDFF.setInvoiceTransactionsFlexfield_Segment4("");
        invoiceDFF.setInvoiceTransactionsFlexfield_Segment5("");
        //TODO PREGUNTAR QUE DATO VA
        invoiceDFF.setInvoiceTransactionsFlexfield_Segment6("");
        invoiceDFF.setInvoiceTransactionsFlexfield_Segment7(additional.getString("address"));
        invoiceDFF.setInvoiceTransactionsFlexfield_Segment8(additional.getString("email"));
        invoiceDFF.setInvoiceTransactionsFlexfield_Segment9(additional.getString("vesselName"));
        invoiceDFF.setInvoiceTransactionsFlexfield_Segment10(additional.getString("vesselVisitId"));
        invoiceDFF.setInvoiceTransactionsFlexfield_Segment11(additional.getString("vesselArrival"));
        invoiceDFF.setInvoiceTransactionsFlexfield_Segment12(additional.getString("DaeDai"));
        invoiceDFF.setInvoiceTransactionsFlexfield_Segment13(additional.getString("Notes"));
        invoiceDFF.setInvoiceTransactionsFlexfield_Segment14(additional.getString("NaveReferencia"));
        invoiceDFF.setInvoiceTransactionsFlexfield_Segment15("Y");
        invoiceDFF.setInvoiceTransactionsFlexfield_Date_Segment1(additional.getString("vesselArrival"));
        invoiceDFF.setInvoiceTransactionsFlexfield_Date_Segment2("");
        invoiceDFF.setInvoiceTransactionsFlexfield_Date_Segment3("");
        invoiceDFF.setInvoiceTransactionsFlexfield_Date_Segment4("");
        invoiceDFF.setInvoiceTransactionsFlexfield_Date_Segment5("");
        //TODO PREGUNTAR QUE DATO VA
        invoiceDFF.setInvoiceTransactionsFlexfield_Number_Segment1("96823");

        lista.add(invoiceDFF);

        return lista;

    }

    private List<InvoiceLineDFF> obtenerInvoiceLineDFF(JSONObject jsonObject){

        List<InvoiceLineDFF> lista = new ArrayList<>();

        InvoiceLineDFF invoiceLineDFF = new InvoiceLineDFF();
        invoiceLineDFF.setInvoiceLinesFlexfield_Context("Ecuador");
        invoiceLineDFF.setInvoiceLinesFlexfield_Segment1("41");
        //TODO PREGUNTAR A PABLO
        invoiceLineDFF.setInvoiceLinesFlexfield_Segment2("300001441855607");
        invoiceLineDFF.setInvoiceLinesFlexfield_Segment3("");
        invoiceLineDFF.setInvoiceLinesFlexfield_Segment4("");
        invoiceLineDFF.setInvoiceLinesFlexfield_Segment5("");
        invoiceLineDFF.setInvoiceLinesFlexfield_Segment6("");
        invoiceLineDFF.setInvoiceLinesFlexfield_Segment7("");
        invoiceLineDFF.setInvoiceLinesFlexfield_Segment8("");
        invoiceLineDFF.setInvoiceLinesFlexfield_Segment9("593");
        invoiceLineDFF.setInvoiceLinesFlexfield_Segment10("");
        invoiceLineDFF.setInvoiceLinesFlexfield_Segment11("");
        invoiceLineDFF.setInvoiceLinesFlexfield_Segment12("");
        invoiceLineDFF.setInvoiceLinesFlexfield_Segment13("");
        invoiceLineDFF.setInvoiceLinesFlexfield_Segment14("");
        invoiceLineDFF.setInvoiceLinesFlexfield_Segment15("");

        lista.add(invoiceLineDFF);

        return lista;

    }

}