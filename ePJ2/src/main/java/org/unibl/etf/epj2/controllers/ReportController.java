package org.unibl.etf.epj2.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.unibl.etf.epj2.models.reports.DailyReport;
import org.unibl.etf.epj2.models.reports.DailyReportTableData;
import org.unibl.etf.epj2.models.reports.SummaryReport;
import org.unibl.etf.epj2.utils.FinancialReportUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Aleksandar Drljaca
 */
public class ReportController {

    /**
     * Table with daily reports data
     */
    public TableView dailyTable;
    /**
     * Date column
     */
    public TableColumn dateCol;
    /**
     * Income column
     */
    public TableColumn incomeCol;
    /**
     * Discounts column
     */
    public TableColumn discountsCol;
    /**
     * Promo column
     */
    public TableColumn promCol;
    /**
     * Distances column
     */
    public TableColumn distCol;
    /**
     * Maintenance column
     */
    public TableColumn maintCol;
    /**
     * Malfunction repairs column
     */
    public TableColumn repairsCol;
    // Labels to show summary report values
    /**
     * Income label
     */
    public Label incomeLbl;
    /**
     * Discounts label
     */
    public Label discountsLbl;
    /**
     * Promo label
     */
    public Label promoLbl;
    /**
     * Distances label
     */
    public Label distancesLbl;
    /**
     * Maintenance label
     */
    public Label maintenanceLbl;
    /**
     * Repairs label
     */
    public Label repairsLbl;
    /**
     * Company costs label
     */
    public Label companyCostsLbl;
    /**
     * Total tax label
     */
    public Label taxLbl;

    /**
     * Init method
     * View shouldn't be opened before simulation finishes
     * Because we wait for all receipts to be created
     */
    @FXML
    private void initialize(){
        prepareSummaryReport();
        prepareDailyReportTable();
    }

    /**
     * Preparing table for summary report
     */
    private void prepareSummaryReport(){
        SummaryReport summaryReport= FinancialReportUtil.generateSummaryFinancialReport();
        incomeLbl.setText(summaryReport.getIncomeTotal()+"");
        discountsLbl.setText(summaryReport.getDiscountsTotal()+"");
        promoLbl.setText(summaryReport.getPromosTotal()+"");
        distancesLbl.setText(summaryReport.getDistanceAmountTotal()+"");
        maintenanceLbl.setText(summaryReport.getMaintenanceTotal()+"");
        repairsLbl.setText(summaryReport.getMalfunctionRepairsTotal()+"");
        companyCostsLbl.setText(summaryReport.getCompanyCostsTotal()+"");
        taxLbl.setText(summaryReport.getTaxTotal()+"");

    }

    /**
     * Preparing daily report table
     * Before presenting data hashmaps from financial report util class
     * must be reorganized in a way they can be listed inside tableview
     * DailyReportTableData is created - contains hashmap key with all values
     * from different hashmaps with that same key
     * Not the best solution but...
     */
    private void prepareDailyReportTable(){
        DailyReport dailyReport=FinancialReportUtil.generateDailyFinancialReport();
        Set<LocalDate> allDates=new HashSet<>();
        allDates.addAll(dailyReport.getIncomeTotal().keySet());
        allDates.addAll(dailyReport.getDiscountsTotal().keySet());
        allDates.addAll(dailyReport.getPromoTotal().keySet());
        allDates.addAll(dailyReport.getDistanceTotal().keySet());
        allDates.addAll(dailyReport.getMaintenanceTotal().keySet());
        allDates.addAll(dailyReport.getMalfunctionRepairsTotal().keySet());
        List<DailyReportTableData> data=new ArrayList<>();
        for(LocalDate d:allDates)
            data.add(new DailyReportTableData(d,dailyReport.getIncomeTotal().getOrDefault(d,0.0),dailyReport.getDiscountsTotal().getOrDefault(d,0.0),
                    dailyReport.getPromoTotal().getOrDefault(d,0.0),dailyReport.getDistanceTotal().getOrDefault(d,0.0),dailyReport.getMaintenanceTotal().getOrDefault(d,0.0),
                    dailyReport.getMalfunctionRepairsTotal().getOrDefault(d,0.0)));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        incomeCol.setCellValueFactory(new PropertyValueFactory<>("income"));
        discountsCol.setCellValueFactory(new PropertyValueFactory<>("discounts"));
        promCol.setCellValueFactory(new PropertyValueFactory<>("promo"));
        distCol.setCellValueFactory(new PropertyValueFactory<>("distance"));
        maintCol.setCellValueFactory(new PropertyValueFactory<>("maintenance"));
        repairsCol.setCellValueFactory(new PropertyValueFactory<>("repairs"));
        dailyTable.getItems().clear();
        data.forEach(e->dailyTable.getItems().add(e));

    }
}
