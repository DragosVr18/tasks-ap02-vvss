package demo.features.search;

import io.github.bonigarcia.wdm.WebDriverManager;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Issue;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Pending;
import net.thucydides.core.annotations.Steps;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import demo.steps.serenity.EndUserSteps;

@RunWith(SerenityRunner.class)
public class SearchByKeywordStory {

    @Managed(uniqueSession = true)
    public WebDriver webdriver;

    @Steps
    public EndUserSteps anna;

    @Test
    public void login() {
        anna.is_the_home_page();
        anna.login("standard_user", "secret_sauce");
        assert anna.assertShoppingCartExists();
    }

    @Test
    public void addToCard(){
        login();
        anna.addToCartStep();
        assert anna.assertButtonChangedRemove();
    }

    @Test
    public void logout(){
        login();
        anna.pressLogoutButton();
        assert anna.assertLoginButtonAfterLogout();
    }

    @Test
    public void removeFromCart(){
        login();
        addToCard();
        anna.removeFromCart();
        assert anna.assertButtonAddToCart();
    }

} 