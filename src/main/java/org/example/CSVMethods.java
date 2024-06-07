package org.example;

public interface CSVMethods {
    void readAndPrintCSV(String path) throws Exception;
    void readAndPrintSpecificColumn(String path, int columnIndex) throws Exception;
    void PrintLineWhereMatchesValue(String path, String columnName, String value) throws Exception;
    void WriteCSVFile(String path, ClasaTest obiect) throws Exception;

}
