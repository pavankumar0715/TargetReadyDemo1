package com.targetready.bankService.runner;

import com.targetready.orderService.model.Invoice;
import com.targetready.bankService.service.InvoiceService;
import com.targetready.bankService.service.DatabaseConnectionTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//@Component
//public class TestRunner implements CommandLineRunner {

//    @Autowired
//    private InvoiceService invoiceService;
//
//    @Autowired
//    private DatabaseConnectionTestService databaseConnectionTestService;
//
//    @Override
//    public void run(String... args) throws Exception {
//        databaseConnectionTestService.testConnection();
//
//        Invoice invoice = new Invoice();
//        invoice.setOrderId("5");
//        invoice.setAmount(100.00);
//        invoice.setTransactionId("tx122");
//        invoice.setStatus(true);
//        invoice.setBank("Bank");
//
//        invoiceService.saveInvoice(invoice);
//        System.out.println("Invoice saved: " + invoice);
//    }
//}
public class TestRunner {
}
