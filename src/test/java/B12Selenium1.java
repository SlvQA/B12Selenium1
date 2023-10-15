import com.github.javafaker.Faker;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Random;

public class B12Selenium1 {
    @Test
    public void navigateToWebsite() throws InterruptedException {
        //navigate to the webpage
        WebDriver driver = new ChromeDriver();
        driver.get("http://duotify.us-east-2.elasticbeanstalk.com/register.php");

        //verify the title
        String expectedTitle = "Welcome to Duotify!";
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, expectedTitle);

        //click on sign up button
        driver.findElement(By.id("hideLogin")).click();

        //complete the form
        Faker faker = new Faker();
        String alphanumericCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuv";
        StringBuilder randomString = new StringBuilder(3);
        Random random = new Random();

        for (int i = 0; i < 3; i++) {
            int randomIndex = random.nextInt(alphanumericCharacters.length());
            char randomChar = alphanumericCharacters.charAt(randomIndex);
            randomString.append(randomChar);
        }
        String username = faker.name().username() + randomString.toString(); //creating a more unique username
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();

        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("firstname")).sendKeys(firstName);
        driver.findElement(By.id("lastname")).sendKeys(lastName);

        String emailAddress = faker.internet().emailAddress();

        driver.findElement(By.id("email")).sendKeys(emailAddress);
        driver.findElement(By.id("email2")).sendKeys(emailAddress);

        String password = faker.internet().password();
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("password2")).sendKeys(password);

        //click on sign up
        driver.findElement(By.name("registerButton")).click();

        //verify the URL
        String expectedURL = "http://duotify.us-east-2.elasticbeanstalk.com/browse.php?";
        String actualURL = driver.getCurrentUrl();
        Assert.assertEquals(actualURL, expectedURL);

        //verify the first and last name
        String actualFullName = driver.findElement(By.id("nameFirstAndLast")).getText();
        String expectedFullName = firstName + " " + lastName;
        Assert.assertEquals(actualFullName, expectedFullName);

        //Click on the username on the left and verify the username on the main window and then click logout.
        driver.findElement(By.id("nameFirstAndLast")).click();
        Thread.sleep(500);
        String profilePageFullName = driver.findElement(By.tagName("h1")).getText();
        Assert.assertEquals(profilePageFullName, expectedFullName);
        driver.findElement(By.id("rafael")).click();

        //Verify that you are logged out
        Thread.sleep(500);
        Assert.assertEquals(driver.getCurrentUrl(), "http://duotify.us-east-2.elasticbeanstalk.com/register.php");

        //Login using the same username and password when you signed up
        driver.findElement(By.id("loginUsername")).sendKeys(username);
        driver.findElement(By.id("loginPassword")).sendKeys(password);
        driver.findElement(By.name("loginButton")).click();

        //Verify successful login by verifying that the home page contains the text "You Might Also Like"
        Thread.sleep(500);
        Assert.assertEquals(driver.findElement(By.tagName("h1")).getText(), "You Might Also Like");

        //log out
        driver.findElement(By.id("nameFirstAndLast")).click();
        Thread.sleep(500);
        driver.findElement(By.id("rafael")).click();

        //Verify that you are logged out
        Thread.sleep(500);
        Assert.assertEquals(driver.getCurrentUrl(), "http://duotify.us-east-2.elasticbeanstalk.com/register.php");
    }
}
