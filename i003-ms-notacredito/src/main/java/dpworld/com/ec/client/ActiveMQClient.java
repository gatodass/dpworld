package dpworld.com.ec.client;
import com.google.gson.Gson;
import dpworld.com.ec.models.Factura;
import dpworld.com.ec.models.InvoiceDFF;
import dpworld.com.ec.models.InvoiceLineDFF;
import dpworld.com.ec.models.Lines;
import dpworld.com.ec.models.Receivableinvoices;
import dpworld.com.ec.models.TaxLines;
import dpworld.com.ec.service.IFacturaService;
import org.apache.activemq.util.StopWatch;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class ActiveMQClient {

    Logger logger = LoggerFactory.getLogger(ActiveMQClient.class);

    @Autowired
    private IFacturaService iFacturaService;

    @Autowired
    ActiveMQProducerLogger activeMQProducerLogger;

    @JmsListener(destination = "N4CREDITNOTES")
    public void processMessage(String content) {

        String uuid = UUID.randomUUID().toString();

        activeMQProducerLogger.sendLogger(uuid, content, "IN_N4CREDITNOTES", "REQUEST XML", "200", "0");

        logger.info("REQUEST XML: " + content);

        StopWatch watch = new StopWatch();
        watch.restart();

        try {

            JSONObject xmlJSONObj = XML.toJSONObject(content, true);

            JSONObject invoice = xmlJSONObj.getJSONObject("invoice");

            activeMQProducerLogger.sendLogger(uuid, new Gson().toJson(xmlJSONObj), "COLA - N4CREDITNOTES", "REQUEST", "200", "0");

            logger.info("REQUEST MAPEADO: " + new Gson().toJson(xmlJSONObj));

            List<Receivableinvoices> listaReceivableinvoices = this.obtenerReceivableinvoices(invoice);

            Factura factura = new Factura();
            factura.setReceivableinvoices(listaReceivableinvoices);

            iFacturaService.facturaCobrar(factura, uuid);

        } catch (Exception e) {

            activeMQProducerLogger.sendLogger(uuid, e.getMessage(), "https://fapidev.dpworld.com/amrlatmec/n4/fin/CreateARInvoice", "ERROR N4CREDITNOTES", "400", String.valueOf(watch.taken()));

            logger.error("ERROR N4CREDITNOTES: " + e.getMessage());

        }

    }

    private List<Receivableinvoices> obtenerReceivableinvoices(JSONObject jsonObject){

        JSONObject additional = jsonObject.getJSONObject("additional");

        List<Receivableinvoices> lista = new ArrayList<>();

        Receivableinvoices receivableinvoices = new Receivableinvoices();

        receivableinvoices.setSource(jsonObject.getString("Source"));
        receivableinvoices.setRegion("AMR-EC");
        receivableinvoices.setInvoiceCurrencyCode(jsonObject.getString("currency"));
        receivableinvoices.setTrxClass("CM");
        receivableinvoices.setTransactionNumber("");
        try{
            receivableinvoices.setComments(additional.getString("email"));
        } catch (Exception e){
            receivableinvoices.setComments("");
        }
        receivableinvoices.setTransactionDate(jsonObject.getString("create"));
        receivableinvoices.setTransactionType("");

        if(jsonObject.getString("Source").equals("N4")){
            receivableinvoices.setTransactionSource("EC_N4_BILLING");
        } else if (jsonObject.getString("Source").equals("GEKO")) {
            receivableinvoices.setTransactionSource("EC_GEKO_BILLING");
        }

        try{
            String numeroFacturaRecortada = this.obtenerNumeroFacturaRecortada(jsonObject.getJSONObject("additional").getString("CustomsId"));
            receivableinvoices.setParentInvoiceTrxNumber(numeroFacturaRecortada);
            receivableinvoices.setCrossReference(numeroFacturaRecortada);
        } catch (Exception e){
            receivableinvoices.setParentInvoiceTrxNumber("");
            receivableinvoices.setCrossReference("");
        }
        try {
            receivableinvoices.setParentInvoiceTrxType(jsonObject.getString("InvoiceType"));
        } catch (Exception e){
            receivableinvoices.setParentInvoiceTrxType("");
        }
        receivableinvoices.setAmountApplied("");
        receivableinvoices.setApplyDate("");
        receivableinvoices.setBillToCustomerNumber(jsonObject.getString("customerId"));
        receivableinvoices.setBusinessUnit(this.obtenerBusinessUnit(jsonObject.getString("InvoiceType")));
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

        JSONObject details = jsonObject.getJSONObject("details");

        var invoiceItemTipo = details.get("invoiceItem");

        if(invoiceItemTipo instanceof JSONArray){

            JSONArray invoiceItems = details.getJSONArray("invoiceItem");

            for (int i = 0; i < invoiceItems.length(); i++) {

                JSONObject invoiceItem = invoiceItems.getJSONObject(i);

                Lines lines = new Lines();

                lines.setLineNumber(String.valueOf(i+1));
                lines.setDescription(invoiceItem.getString("description"));
                lines.setQuantity(invoiceItem.getString("quantity"));
                lines.setUnitSellingPrice(invoiceItem.getString("amount"));
                lines.setMemoLine(invoiceItem.getString("tariff"));
                lines.setTaxClassificationCode(invoiceItem.getString("TaxCode"));
                lines.setLineAmount("");

                List<TaxLines> ListaTaxLines = new ArrayList<>();
                TaxLines taxLines = new TaxLines();
                ListaTaxLines.add(taxLines);

                lines.setTaxLines(ListaTaxLines);
                lines.setInvoiceLineDFF(this.obtenerInvoiceLineDFF(jsonObject));

                lista.add(lines);
            }
        }

        if(invoiceItemTipo instanceof JSONObject){

            JSONObject invoiceItem = details.getJSONObject("invoiceItem");

            Lines lines = new Lines();

            lines.setLineNumber("1");
            lines.setDescription(invoiceItem.getString("description"));
            lines.setQuantity(invoiceItem.getString("quantity"));
            lines.setUnitSellingPrice(invoiceItem.getString("amount"));
            lines.setMemoLine(invoiceItem.getString("tariff"));
            lines.setTaxClassificationCode(invoiceItem.getString("TaxCode"));
            lines.setLineAmount("");

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
        invoiceDFF.setInvoiceTransactionsFlexfield_Segment1("");
        invoiceDFF.setInvoiceTransactionsFlexfield_Segment2("");
        invoiceDFF.setInvoiceTransactionsFlexfield_Segment3("");
        invoiceDFF.setInvoiceTransactionsFlexfield_Segment4("");
        invoiceDFF.setInvoiceTransactionsFlexfield_Segment5("");
        invoiceDFF.setInvoiceTransactionsFlexfield_Segment6("");
        try{
            invoiceDFF.setInvoiceTransactionsFlexfield_Segment8(this.recortarPalabra(additional.getString("vesselArrival")));
        } catch (Exception e){
            invoiceDFF.setInvoiceTransactionsFlexfield_Segment8("");
        }
        try{
            invoiceDFF.setInvoiceTransactionsFlexfield_Segment9(this.recortarPalabra(additional.getString("vesselName")));
        } catch (Exception e){
            invoiceDFF.setInvoiceTransactionsFlexfield_Segment9("");
        }
        invoiceDFF.setInvoiceTransactionsFlexfield_Segment9(this.recortarPalabra(additional.getString("vesselName")));
        try{
            invoiceDFF.setInvoiceTransactionsFlexfield_Segment10(this.recortarPalabra(additional.getString("NaveReferencia")));
        } catch (Exception e){
            invoiceDFF.setInvoiceTransactionsFlexfield_Segment10("");
        }
        try{
            invoiceDFF.setInvoiceTransactionsFlexfield_Segment11(this.recortarPalabra(additional.getString("DaeDai")));
        } catch (Exception e){
            invoiceDFF.setInvoiceTransactionsFlexfield_Segment11("");
        }
        try{
            invoiceDFF.setInvoiceTransactionsFlexfield_Segment12(this.recortarPalabra(additional.getString("Notes")));
        } catch (Exception e){
            invoiceDFF.setInvoiceTransactionsFlexfield_Segment12("");
        }
        try{
            invoiceDFF.setInvoiceTransactionsFlexfield_Segment13(this.recortarPalabra(additional.getString("vesselVisitId")));
        } catch (Exception e){
            invoiceDFF.setInvoiceTransactionsFlexfield_Segment13("");
        }
        invoiceDFF.setInvoiceTransactionsFlexfield_Segment14("");
        invoiceDFF.setInvoiceTransactionsFlexfield_Segment15("");
        invoiceDFF.setInvoiceTransactionsFlexfield_Date_Segment1("");
        invoiceDFF.setInvoiceTransactionsFlexfield_Date_Segment2("");
        invoiceDFF.setInvoiceTransactionsFlexfield_Date_Segment3("");
        invoiceDFF.setInvoiceTransactionsFlexfield_Date_Segment4("");
        invoiceDFF.setInvoiceTransactionsFlexfield_Date_Segment5("");
        invoiceDFF.setInvoiceTransactionsFlexfield_Number_Segment1(jsonObject.getString("draftId"));

        lista.add(invoiceDFF);

        return lista;

    }

    private List<InvoiceLineDFF> obtenerInvoiceLineDFF(JSONObject jsonObject){

        List<InvoiceLineDFF> lista = new ArrayList<>();

        if(!jsonObject.getString("InvoiceType").contains("REEMBOLSO")){

            InvoiceLineDFF invoiceLineDFF = new InvoiceLineDFF();
            invoiceLineDFF.setInvoiceLinesFlexfield_Context("");
            invoiceLineDFF.setInvoiceLinesFlexfield_Segment1("");
            invoiceLineDFF.setInvoiceLinesFlexfield_Segment2("");
            invoiceLineDFF.setInvoiceLinesFlexfield_Segment3("");
            invoiceLineDFF.setInvoiceLinesFlexfield_Segment4("");
            invoiceLineDFF.setInvoiceLinesFlexfield_Segment5("");
            invoiceLineDFF.setInvoiceLinesFlexfield_Segment6("");
            invoiceLineDFF.setInvoiceLinesFlexfield_Segment7("");
            invoiceLineDFF.setInvoiceLinesFlexfield_Segment8("");
            invoiceLineDFF.setInvoiceLinesFlexfield_Segment9("");
            invoiceLineDFF.setInvoiceLinesFlexfield_Segment10("");
            invoiceLineDFF.setInvoiceLinesFlexfield_Segment11("");
            invoiceLineDFF.setInvoiceLinesFlexfield_Segment12("");
            invoiceLineDFF.setInvoiceLinesFlexfield_Segment13("");
            invoiceLineDFF.setInvoiceLinesFlexfield_Segment14("");
            invoiceLineDFF.setInvoiceLinesFlexfield_Segment15("");

            lista.add(invoiceLineDFF);

            return lista;
        }

        JSONObject reembolsos = jsonObject.getJSONObject("reembolsos");

        var invoicesTipo = reembolsos.get("invoices");

        if(invoicesTipo instanceof JSONArray){

            JSONArray invoices = reembolsos.getJSONArray("invoices");

            for (int i = 0; i < invoices.length(); i++) {

                JSONObject invoice = invoices.getJSONObject(i);

                InvoiceLineDFF invoiceLineDFF = new InvoiceLineDFF();

                invoiceLineDFF.setInvoiceLinesFlexfield_Context("Ecuador");
                invoiceLineDFF.setInvoiceLinesFlexfield_Segment1("01");
                invoiceLineDFF.setInvoiceLinesFlexfield_Segment2(invoice.getString("identificacionProveedorReembolso"));
                invoiceLineDFF.setInvoiceLinesFlexfield_Segment3("");
                invoiceLineDFF.setInvoiceLinesFlexfield_Segment4(invoice.getString("estabDocReembolso"));
                invoiceLineDFF.setInvoiceLinesFlexfield_Segment5(invoice.getString("ptoEmiDocReembolso"));
                invoiceLineDFF.setInvoiceLinesFlexfield_Segment6(invoice.getString("secuencialDocReembolso"));
                invoiceLineDFF.setInvoiceLinesFlexfield_Segment7(invoice.getString("numeroautorizacionDocReemb"));
                invoiceLineDFF.setInvoiceLinesFlexfield_Segment8(invoice.getString("fechaEmisionDocReembolso"));
                invoiceLineDFF.setInvoiceLinesFlexfield_Segment9(invoice.getString("codPaisPagoProveedorReembolso"));
                invoiceLineDFF.setInvoiceLinesFlexfield_Segment10("");
                invoiceLineDFF.setInvoiceLinesFlexfield_Segment11("");
                invoiceLineDFF.setInvoiceLinesFlexfield_Segment12("");
                invoiceLineDFF.setInvoiceLinesFlexfield_Segment13("");
                invoiceLineDFF.setInvoiceLinesFlexfield_Segment14("");
                invoiceLineDFF.setInvoiceLinesFlexfield_Segment15("");

                lista.add(invoiceLineDFF);
            }
        }

        if(invoicesTipo instanceof JSONObject){

            JSONObject invoice = reembolsos.getJSONObject("invoices");

            InvoiceLineDFF invoiceLineDFF = new InvoiceLineDFF();

            invoiceLineDFF.setInvoiceLinesFlexfield_Context("Ecuador");
            invoiceLineDFF.setInvoiceLinesFlexfield_Segment1("01");
            invoiceLineDFF.setInvoiceLinesFlexfield_Segment2(invoice.getString("identificacionProveedorReembolso"));
            invoiceLineDFF.setInvoiceLinesFlexfield_Segment3("");
            invoiceLineDFF.setInvoiceLinesFlexfield_Segment4(invoice.getString("estabDocReembolso"));
            invoiceLineDFF.setInvoiceLinesFlexfield_Segment5(invoice.getString("ptoEmiDocReembolso"));
            invoiceLineDFF.setInvoiceLinesFlexfield_Segment6(invoice.getString("secuencialDocReembolso"));
            invoiceLineDFF.setInvoiceLinesFlexfield_Segment7(invoice.getString("numeroautorizacionDocReemb"));
            invoiceLineDFF.setInvoiceLinesFlexfield_Segment8(invoice.getString("fechaEmisionDocReembolso"));
            invoiceLineDFF.setInvoiceLinesFlexfield_Segment9(invoice.getString("codPaisPagoProveedorReembolso"));
            invoiceLineDFF.setInvoiceLinesFlexfield_Segment10("");
            invoiceLineDFF.setInvoiceLinesFlexfield_Segment11("");
            invoiceLineDFF.setInvoiceLinesFlexfield_Segment12("");
            invoiceLineDFF.setInvoiceLinesFlexfield_Segment13("");
            invoiceLineDFF.setInvoiceLinesFlexfield_Segment14("");
            invoiceLineDFF.setInvoiceLinesFlexfield_Segment15("");

            lista.add(invoiceLineDFF);
        }

        return lista;

    }

    private String obtenerBusinessUnit(String invoiceType){

        String[] parts = invoiceType.split("_");
        String valorBusiness = parts[0];

        return switch (valorBusiness) {
            case "6204" -> "ECPSJ - DP World Posorja";
            case "6244" -> "ECGYE - DP World SOLUCAI";
            case "6209" -> "ECDUN - Duranports";
            case "6243" -> "ECGYE - Soltrans";
            case "6224" -> "ECDUN - DP World Logistics Duran";
            case "6210" -> "ECDUN - CentroLog Duran";
            case "6225" -> "ECGYE - DP World NVOCC & 3PL";
            default -> "";
        };
    }

    private String recortarPalabra(String campo){
        try {
            return campo.substring(0, 30);
        } catch (Exception e){
            return campo;
        }
    }

    private String obtenerNumeroFacturaRecortada(String campo){

        try {
            String[] parts = campo.split("-");
            String campoRecortado = parts[2];
            return String.valueOf(Integer.parseInt(campoRecortado));
        } catch (Exception e){
            return campo;
        }

    }

}
