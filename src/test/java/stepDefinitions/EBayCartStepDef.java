package stepDefinitions;

import io.cucumber.java.en.Then;
import org.openqa.selenium.WebDriver;
import utils.Manager;

public class EBayCartStepDef {
private WebDriver driver;
    public EBayCartStepDef(Manager manager){
        this.driver = manager.getDriver();
    }

}
