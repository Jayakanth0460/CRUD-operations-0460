package org.example;
import com.mongodb.client.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.Document;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExportSelectedFieldsToExcel {
    public static void main(String[] args) {

        MongoClient mongoClient=MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database=mongoClient.getDatabase("companyDB");
        MongoCollection<Document> collection=database.getCollection("employees");

        Workbook workbook=new XSSFWorkbook();
        Sheet sheet=workbook.createSheet("SelectedFields");
        String[] fields={"empId","name","age","role","salary"};
        Row header=sheet.createRow(0);
        int idx=0;
        for(String x:fields){
            header.createCell(idx).setCellValue(x);
            idx++;
        }

        idx=1;
        for(Document doc:collection.find()){
            Row r=sheet.createRow(idx++);
            for(int i=0;i<fields.length;i++){
                Object o=doc.get(fields[i]);
                r.createCell(i).setCellValue(o!=null?o.toString():"");
            }
        }


        try (FileOutputStream fileOut = new FileOutputStream("SelectedFields.xlsx")) {
            workbook.write(fileOut);
            workbook.close();
            System.out.println("Selected fields exported to Excel successfully!");
        } catch (IOException e) {
            e.fillInStackTrace();
        }
        mongoClient.close();
    }
}