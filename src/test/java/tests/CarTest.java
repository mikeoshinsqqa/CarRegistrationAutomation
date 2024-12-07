package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import utils.FileIO;
import utils.RegistrationUtil;

import java.util.List;

public class CarTest
{
    WebDriver driver;

    @Test
    public void testCarValuation()
    {
        System.setProperty("webdriver.chrome.driver", "src/drivers/chromedriver/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        boolean cookiesAccepted  =false;

        try
        {
            String text = FileIO.readFile("src/files/car_input.txt");
            String outPath = "src/files/car_output.txt";
            FileIO.initOutFile(outPath);


            List<String> registrationNumbers = RegistrationUtil.getVehicleRegistrations(text);

            if(!registrationNumbers.isEmpty())
            {
                for (String reg : registrationNumbers)
                {
                    boolean notFound = false;

                    driver.get("https://www.webuyanycar.com");
                    Thread.sleep(2000);

                    System.out.println("Registration Key is: " + reg);

                    if(!cookiesAccepted)
                    {
                        WebElement cookiesBtn = driver.findElement(By.id("onetrust-accept-btn-handler"));
                        Thread.sleep(1000);

                        if (cookiesBtn != null)
                        {
                            cookiesBtn.click();
                            cookiesAccepted = true;
                            Thread.sleep(2000);
                        }
                    }

                    WebElement regField = driver.findElement(By.id("vehicleReg"));
                    Thread.sleep(2000);

                    if(regField != null)
                    {
                        regField.sendKeys(reg);
                    }
                    WebElement mileageField = driver.findElement(By.id("Mileage"));
                    Thread.sleep(2000);

                    if(mileageField != null)
                    {
                        String mileage = RegistrationUtil.getRandomMileage();
                        mileageField.sendKeys(mileage);
                    }


                    WebElement searchButton = driver.findElement(By.id("btn-go"));
                    Thread.sleep(2000);
                    searchButton.click();
                    Thread.sleep(5000);


                    try
                    {
                        WebElement element = driver.findElement(By.xpath("/html/body/div[1]/wbac-app/div[1]/div/div/vehicle-registration-check/section[1]/div/div[1]/div/div[1]/h1"));
                        notFound = true;
                    }
                    catch (Exception e)
                    {

                    }

                    if(!notFound)
                    {
                        try
                        {
                            String manufacturer = driver.findElement(By.xpath("/html/body/div[1]/wbac-app/div[1]/div/div/vehicle-questions/div/section[1]/div/div[1]/div/div[3]/div/vehicle-details/div[3]/div[2]/div[1]/div[2]")).getText().trim();
                            String model = driver.findElement(By.xpath("/html/body/div[1]/wbac-app/div[1]/div/div/vehicle-questions/div/section[1]/div/div[1]/div/div[3]/div/vehicle-details/div[3]/div[2]/div[2]/div[2]")).getText().trim();
                            String year = driver.findElement(By.xpath("/html/body/div[1]/wbac-app/div[1]/div/div/vehicle-questions/div/section[1]/div/div[1]/div/div[3]/div/vehicle-details/div[3]/div[2]/div[3]/div[2]")).getText().trim();
                            String value = reg+","+manufacturer+","+model+","+year;
                            if(value != "")
                            {
                                FileIO.appendToFile(outPath, value);
                            }
                        }
                        catch (Exception e) {

                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

}