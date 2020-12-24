package smoke;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;

public class Start {
    public static void main(String[] args) {
        Selenide.open("/");
        auth("", "");
        Selenide.sleep(5000);

        Selenide.open("/projects/484/search/queries/14982");

        Selenide.sleep(5000);
//        ElementsCollection selenideElements = Selenide.$$("[aria-label=\"Press SPACE to select this row.\"]");
        ElementsCollection globalId = Selenide.$$("[aria-label=\"Press SPACE to select this row.\"] [col-id=\"globalId\"]");
        ElementsCollection name = Selenide.$$("[aria-label=\"Press SPACE to select this row.\"] [col-id=\"name\"] [class=\"ellipsis\"]");

//        for (int i = 0; i < 53; i++) {
//            System.out.println((i + 1) + " " +
//                    "/projects/484/tests/" + selenideElements.get(i).$("[col-id=\"globalId\"]").getText() + " , " +
//                    selenideElements.get(i).$("[col-id=\"name\"] [class=\"ellipsis\"]").getText());
//        }
        for (int i = 0; i < 53; i++) {
            System.out.println((i + 1) + " " +
                    "/projects/484/tests/" + globalId.get(i).getText() + " , " +
                    name.get(i).getText());
        }

    }

    private static void auth(String login, String password) {
        Selenide.$("[placeholder=\"Username\"]").val(login);
        Selenide.$("[placeholder=\"Password\"]").val(password);
        Selenide.$("[class=\"button_active button button_blue button_medium button_padding_both\"]").click();
    }


}
