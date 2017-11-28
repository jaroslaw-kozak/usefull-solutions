import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class XPathFetcher {
    public static String getElementXPath(WebDriver driver, WebElement element) {

        String javaScript = "function getElementXPath(elt){" + "var path = \"\";"
                + "for (; elt && elt.nodeType == 1; elt = elt.parentNode){"
                + "idx = getElementIdx(elt);" + "xname = elt.tagName;" + "if (idx > 1){"
                + "xname += \"[\" + idx + \"]\";" + "}" + "path = \"/\" + xname + path;" + "}"
                + "return path;" + "}" + "function getElementIdx(elt){" + "var count = 1;"
                + "for (var sib = elt.previousSibling; sib ; sib = sib.previousSibling){"
                + "if(sib.nodeType == 1 && sib.tagName == elt.tagName){" + "count++;" + "}" + "}"
                + "return count;" + "}" + "return getElementXPath(arguments[0]).toLowerCase();";

        return (String) ((JavascriptExecutor) driver).executeScript(javaScript, element);
    }
}
