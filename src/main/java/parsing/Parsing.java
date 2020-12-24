package parsing;

import parsing.object.Step;
import parsing.object.TestCase;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class Parsing {

    public static List<TestCase> getListTestCase(String STREAM_NAME) throws Exception {
        HSSFSheet allRows = getAllRows("src/main/resources/" + STREAM_NAME + ".xls");


        List<TestCase> testCases = new ArrayList<>();
        String featureName = STREAM_NAME;
        for (int rowNumber = 0; rowNumber <= allRows.getLastRowNum(); rowNumber++) {
            HSSFRow row = allRows.getRow(rowNumber);

            if (getFeatureName(row) != null) {
                featureName = getFeatureName(row);
                System.out.println(featureName);
            }

            if (getNameTestCase(row) != null) {
                String nameTestCase = getNameTestCase(row);
                testCases.add(
                        new TestCase(
                                STREAM_NAME,
                                featureName,
                                nameTestCase,
//                                getTextInCell(allRows.getRow(rowNumber).getCell(2)),
//                                getTextInCell(allRows.getRow(rowNumber).getCell(16)),
//                                getTextInCell(allRows.getRow(rowNumber).getCell(15)),
//                                getTextInCell(allRows.getRow(rowNumber).getCell(0)),
                                allRows.getRow(rowNumber).getCell(2).toString(),
                                allRows.getRow(rowNumber).getCell(16).toString(),
                                allRows.getRow(rowNumber).getCell(15).toString(),
                                allRows.getRow(rowNumber).getCell(0).toString(),
                                getTestSteps(allRows, rowNumber)
                        )
                );
            }
        }
        return testCases;
    }

    private static String getTestPriority(HSSFSheet allRows, int rowNumber) {
        return allRows.getRow(rowNumber).getCell(2).toString();
    }

    private static List<Step> getTestSteps(HSSFSheet allRows, int rowNumber) {
        List<Step> steps = new ArrayList<Step>();

        for (int i = rowNumber + 1; i <= allRows.getLastRowNum(); i++) {
            HSSFRow row = allRows.getRow(i);

            HSSFCell priority = row.getCell(2);

            boolean testCaseNameRow = false;

            if (priority != null) {
                testCaseNameRow =
                        priority.toString().equals("Критичный")
                                || priority.toString().equals("Некритичный");
            }

            if (testCaseNameRow || getFeatureName(row) != null) {
                break;
            }

            steps.add(
                    new Step(
                            getTextInCell(row.getCell(0)),
                            getTextInCell(row.getCell(4)),
                            getTextInCell(row.getCell(5)),
                            getTextInCell(row.getCell(6))
                    )
            );
        }


        return steps;
    }


    private static String getTextInCell(HSSFCell hssfCell) {
        if (hssfCell == null) {
            return "";
        }
        return hssfCell.getStringCellValue()
                .replace("\r\n\r\n","\n")
                .replace("\r\n","\n")
                .replace("\t\n","\n")
                .replace("\t","\n")
                .replace("\n\n","\n")
                .replace("0. ","0.")
                .replace("1. ","1.")
                .replace("2. ","2.")
                .replace("3. ","3.")
                .replace("4. ","4.")
                .replace("5. ","5.")
                .replace("6. ","6.")
                .replace("7. ","7.")
                .replace("8. ","8.")
                .replace("9. ","9.")
                ;
    }


    private static String getFeatureName(HSSFRow row) {
        String featureName = null;

        boolean b0 = row.getCell(0) == null || row.getCell(0).toString().equals("");
        boolean b1 = row.getCell(1) == null || row.getCell(1).toString().equals("");
        boolean b2 = row.getCell(2) == null || row.getCell(2).toString().equals("");
        boolean b4 = row.getCell(4) == null || row.getCell(4).toString().equals("");
        boolean b5 = row.getCell(5) == null || row.getCell(5).toString().equals("");
        boolean b6 = row.getCell(6) == null || row.getCell(6).toString().equals("");
        boolean b7 = row.getCell(7) == null || row.getCell(7).toString().equals("");
        if (b0 && b1 && b2 && b4 && b5 && b6 && b7) {
            if (row.getCell(3) == null) {
                return null;
            }
            featureName = row.getCell(3).toString();

        }

        return featureName;
    }

    public static String getNameTestCase(HSSFRow row) {
        String nameTestCase = null;

        HSSFCell priority = row.getCell(2);

        boolean testCaseNameRow = false;

        if (priority != null) {
            testCaseNameRow =
                    priority.toString().equals("Критичный")
                            || priority.toString().equals("Некритичный");
        }

        if (testCaseNameRow) {
            nameTestCase = row.getCell(3).toString();
        }

        return nameTestCase;
    }


    public static HSSFSheet getAllRows(String filename) throws Exception {
        HSSFWorkbook myExcelBook = new HSSFWorkbook(new FileInputStream(filename));
        HSSFSheet sheetAt = myExcelBook.getSheetAt(0);

        myExcelBook.close();

        return sheetAt;
    }
}
