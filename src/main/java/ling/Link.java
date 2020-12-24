package ling;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import org.apache.xmlbeans.impl.jam.JMethod;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;

public class Link {

    public static void main(String[] args) {
        Configuration.startMaximized = true;
        Configuration.timeout = 160000;

        authJira("", "");
        Selenide.sleep(3000);
        auth("", "");
        Selenide.sleep(3000);

        Selenide.switchTo().window(0);
        Selenide.open("/browse/QR-316?jql=%22Epic%20Link%22%20%3D%20WEBAPPS-60");

        for (int i = 0; i < 400; i++) {
            try {
                Selenide.switchTo().window(0);
                Selenide.$("[id=\"next-issue\"]").click();
                Selenide.sleep(10000);
                String linkTestInJira = WebDriverRunner.url().replace("?jql=%22Epic%20Link%22%20%3D%20WEBAPPS-60", "");
                String testNameInJira = getNameTest(Selenide.$("[id=\"summary-val\"]").getText());
                System.out.println(linkTestInJira);

                Selenide.switchTo().window(1);
                Selenide.open("/projects/484/tests");
                Selenide.sleep(5000);
                Selenide.$("app-search-input input").val(testNameInJira);

                Selenide.sleep(2000);

                if (Selenide.$("app-menu-option span").is(Condition.not(Condition.visible))) {
                    System.out.println("НЕ найден ТК в Test IT: " + testNameInJira);
                    System.out.println("НЕ найден URL: " + linkTestInJira);
                } else {
                    Selenide.$("app-search-input input").sendKeys(Keys.DOWN);
                    Selenide.$("app-search-input input").pressEnter();
                    Selenide.sleep(2000);
                    String linkTestInTestIT = WebDriverRunner.url();
                    Selenide.$("[placeholder=\"Enter URL\"]").val(linkTestInJira).pressEnter();
                    Selenide.sleep(1000);
                    Selenide.$("[class=\"button_active button button_blue button_medium button_padding_both\"]").click();
                    Selenide.sleep(2000);


                    Selenide.switchTo().window(0);
                    if (Selenide.$("[id=\"status-val\"] span").is(Condition.not(Condition.text("Закрыто")))) {
                        Selenide.$("[id=\"description-val\"]").hover();
                        Selenide.$("[id=\"description-val\"] [class=\"overlay-icon aui-icon aui-icon-small aui-iconfont-edit\"]").click();
                        Selenide.switchTo().window(0);
                        Selenide.switchTo().frame(0);
                        Selenide.$("[id=\"tinymce\"]").sendKeys(linkTestInTestIT + "\n\n");
                        Selenide.switchTo().window(0);
                        Selenide.$("[class=\"aui-button aui-button-primary submit\"]").click();
                        Selenide.sleep(5000);
                    } else {
                        Selenide.sleep(1000);
                    }
                }
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
    }

    private static String getNameTest(String text) {
        return text
                .replace("[Новый]", "")
                .replace("[Автокредиты]", "")
                .replace("[ДКиС]", "")
                .replace("[Инвестиции]", "")
                .replace("[Интернет банк]", "")
                .replace("[Ипотека]", "")
                .replace("[Кредитные карты]", "")
                .replace("[Кредиты]", "")
                .replace("[Мессенджеры и чат-боты]", "")
                .replace("[Нотификация]", "")
                .replace("[Партнерские сервисы]", "")
                .replace("[Переводы]", "")
                .replace("[Платежи]", "")
                .replace("[Сквозные компоненты]", "")
                .replace("[Страхование]", "")
                .replace("[Цифровые продажи]", "")
                .replace("[Качество и безопасность]", "")
                .replace("[Сбережения]", "");
    }

    private static void authJira(String login, String password) {
        Selenide.open("/login.jsp");
        Selenide.$("[id=\"login-form-username\"]").val(login);
        Selenide.$("[id=\"login-form-password\"]").val(password);
        Selenide.$("[id=\"login-form-submit\"]").click();

    }


    private static void auth(String login, String password) {
        String url = "/";
        ((JavascriptExecutor) WebDriverRunner.getWebDriver()).executeScript("window.open('" + url + "','_blank');", new Object[0]);
//        int numberThisTab = WebDriverRunner.getWebDriver().getWindowHandles().size() - 1;
        Selenide.switchTo().window(1);
        Selenide.$("[placeholder=\"Username\"]").val(login);
        Selenide.$("[placeholder=\"Password\"]").val(password);
        Selenide.$("[class=\"button_active button button_blue button_medium button_padding_both\"]").click();
    }
}
