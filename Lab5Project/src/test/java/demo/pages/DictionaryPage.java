package demo.pages;

import net.thucydides.core.annotations.DefaultUrl;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import net.serenitybdd.core.pages.WebElementFacade;
import java.util.stream.Collectors;

import net.serenitybdd.core.annotations.findby.FindBy;

import net.thucydides.core.pages.PageObject;

import java.util.List;

@DefaultUrl("https://www.saucedemo.com")
public class DictionaryPage extends PageObject {

    @FindBy(name="user-name")
    private WebElementFacade usernameHolder;

    @FindBy(name="password")
    private WebElementFacade passwordHolder;

    @FindBy(name="login-button")
    private WebElementFacade loginButton;

    @FindBy(name="add-to-cart-sauce-labs-onesie")
    private WebElementFacade cart;

    public void pressLoginButton() {
        loginButton.waitUntilVisible().waitUntilEnabled();
        loginButton.click();
    }

    public void enterUsername(String username){
        usernameHolder.waitUntilVisible().waitUntilEnabled();
        usernameHolder.sendKeys(username);
    }

    public void enterPassword(String password){
        passwordHolder.waitUntilVisible().waitUntilEnabled();
        passwordHolder.sendKeys(password);
    }

    public boolean checkCart(){
        return cart.waitUntilVisible().isVisible();
    }
}