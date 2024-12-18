package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.util.Iterator;
import java.util.List;

public class FindElement {
    private InfoWeb infoWeb;
    private WebDriver driver;
    private WebElement element;

    private String manual_value;

    public FindElement(InfoWeb infoWeb) {
        this.infoWeb = infoWeb;
        EdgeOptions options = new EdgeOptions();
       options.addArguments("--headless");
        driver = new EdgeDriver(options);
        setWebPage(infoWeb.getURL());
        findElement_by();
    }

    public void refreshWebPage() {
        driver.navigate().refresh();
        findElement_by();
    }

    public double getRef_Price() {
        return infoWeb.getRef_price();
    }

    public String getWebName() {
        return infoWeb.getWeb_Name();
    }

    public String getProdName() {
        return infoWeb.getProdotto();
    }

    public void closeWebPage() {
        driver.quit();
    }

    public String getElementValue() {
        if (infoWeb.getFind_type().equals("recursive")) {
            return element.getText();
        } else {    //if (infoWeb.getFind_type().equals("manual"))
            if (infoWeb.getWeb_Name().equals("Amazon")) {
                return manual_value;
            } else
                return "";
        }
    }

    public WebElement getElement() {
        return element;
    }

    private void setWebPage(String url) {
        driver.get(url);
    }

    private void findElement_by() {
        Iterator<String> iterator = infoWeb.getByParameters().getIterator();
        try {
            if (infoWeb.getFind_type().equals("recursive")) {
                if (!infoWeb.getByParameters().getByClass().isEmpty()) {
                    element = driver.findElement(By.className(iterator.next()));
                    while (iterator.hasNext()) {
                        element = element.findElement(By.className(iterator.next()));
                    }
                } else if (!infoWeb.getByParameters().getById().isEmpty()) {
                    element = driver.findElement(By.id(iterator.next()));
                    while (iterator.hasNext()) {
                        element = element.findElement(By.id(iterator.next()));
                    }
                } else {
                    element = driver.findElement(By.xpath(iterator.next()));
                    while (iterator.hasNext()) {
                        element = element.findElement(By.xpath(iterator.next()));
                    }
                }
            }
            if (infoWeb.getFind_type().equals("manual")) {
                if (infoWeb.getWeb_Name().equals("Amazon")) {
                    StringBuilder builder = new StringBuilder();
                    List<WebElement> elements = driver.findElements(By.xpath("//span[contains(@class, 'a-price " + "aok-align-center reinventPricePriceToPayMargin priceToPay')]"));
                    WebElement tmp = elements.get(0);
                    tmp = tmp.findElement(By.xpath("//span[@class='a-price-whole']"));
                    builder.append(tmp.getText() + ",");
                    tmp = tmp.findElement(By.xpath("//span[@class='a-price-fraction']"));
                    builder.append(tmp.getText());
                    manual_value = builder.toString();
                }
            }
        } catch (NoSuchElementException e) {
            //todo
        }
    }

}
