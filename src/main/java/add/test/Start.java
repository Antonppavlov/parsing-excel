package add.test;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import parsing.Parsing;
import parsing.object.Step;
import parsing.object.TestCase;

import java.util.List;

import static com.sun.deploy.util.SessionState.save;

public class Start {

    private final static String STREAM_NAME = "Сбережения";

    public static void main(String[] args) throws Exception {
        List<TestCase> listTestCase = Parsing.getListTestCase(STREAM_NAME);
        Configuration.startMaximized = true;
        Configuration.timeout = 60000;

        Selenide.open("/");
        auth("", "");
        Selenide.sleep(5000);

        System.out.println("Всего тест кейсов: " + listTestCase.size());

        for (int i = 0; i < listTestCase.size(); i++) {
            System.out.println("Тест кейс: " + (i + 1) + " из: " + listTestCase.size());

            TestCase testCases = listTestCase.get(i);


            Selenide.open("/projects/484/tests");

            pressButtonAddTestCase();
            addNameTest(testCases.testName);

            selectFeatureName(testCases.streamName, testCases.featureName);
            selectPriority(testCases.priority);
            addLabel(testCases.authorTK);
            addLabel(testCases.statusAT);
            addLabel(testCases.typeTestSuite);
            addLabel(testCases.steps.get(0).numberBR);

            addSteps(testCases.steps);
            addPrecondition(testCases.steps);

            saveTest();
            Selenide.sleep(3000);
            System.out.println();
        }

    }

    private static void saveTest() {
        Selenide.$("[class=\"button_active button button_blue button_medium button_padding_both\"]").click();
        System.out.println("СОЗДАНИЕ ТК");
    }

    private static void selectPriority(String priority) {
        Selenide.$("[formcontrolname=\"priority\"]").click();
        if (priority.equals("Критичный")) {
            Selenide.$x("//app-menu-option//div[text()=' High ']").click();
        } else {
            Selenide.$x("//app-menu-option//div[text()=' Medium ']").click();
        }
    }

    private static void selectFeatureName(String streamName, String featureName) {
        Selenide.$("app-control-container app-select [name=\"arrow-dropdown\"]").click();
        Selenide.$x("//app-menu-option//div[text()=' " + featureName + " ']").click();
    }

    private static void addLabel(String label) {
        Selenide.$("[formcontrolname=\"newTag\"]").click();
        Selenide.$("[formcontrolname=\"newTag\"]").val(label).pressEnter();
    }

    private static void addSteps(List<Step> steps) {
        for (Step step : steps) {
            String action = step.action;
            String check = step.check;
            Selenide.$("[class=\"step-row__element greyed-out step-row__action\"]").click();
            Selenide.$("[class=\"ql-editor ql-blank\"]")
                    .val(action)
                    .pressTab()
                    .val(check);
        }
    }

    private static void addPrecondition(List<Step> steps) {
        boolean pre = false;
        for (Step step : steps) {
            String precondition = step.precondition;
            if (precondition != null && !(precondition.replace(" ", "").equals(""))) {
                pre = true;
            }
        }

        if(pre){
            Selenide.$("[id=\"preConditionList\"]").click();

            for (Step step : steps) {
                String precondition = step.precondition;
                Selenide.$("[class=\"step-row__element step-row__checklist greyed-out\"]").click();
                Selenide.$("[class=\"ql-editor ql-blank\"]")
                        .val(precondition)
                        .pressEnter();
            }
        }
    }

    private static void addNameTest(String testName) {
        System.out.println("СОЗДАНИЕ ТК: " + testName);
        Selenide.$("[placeholder=\"Enter name\"]").val(testName);
    }

    private static void pressButtonAddTestCase() {
        Selenide.$("app-floating-button").click();
        Selenide.$$("[class=\"create-work-item-button ng-star-inserted\"]").get(2).click();
    }

    private static void auth(String login, String password) {
        Selenide.$("[placeholder=\"Username\"]").val(login);
        Selenide.$("[placeholder=\"Password\"]").val(password);
        Selenide.$("[class=\"button_active button button_blue button_medium button_padding_both\"]").click();
    }
}
