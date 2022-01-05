
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Vector;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;

public class AppiumAppTest {

    public static final String defaultUrl = "http://127.0.0.1:4723/wd/hub";
    public AndroidDriver driver;
    public DesiredCapabilities dc;

    public static class AppiumAppBuilder {
        private String deviceName;
        private String udid;
        private String platformName;
        private String platformVersion;
        private String appPackage;
        private String appActivity;

        public static final String defaultDevice = "Pixel_3a_API_30";
        public static final String default_udid = "emulator-5554";
        public static final String defaultPlatform = "Android";
        public static final String defaultVersion = "11.0";
        public static final String defaultPackage = "com.edasinar.profitability_proje_2";
        public static final String defaultActivity = "com.edasinar.profitability_proje_2.MainMenuActivity";

        /** build pattern - device name */
        public AppiumAppBuilder(String deviceName) {
            this.deviceName = deviceName;
        }

        public AppiumAppBuilder udid(String udid) {
            this.udid = udid;
            return this;
        }

        public AppiumAppBuilder platformName(String platformName) {
            this.platformName = platformName;
            return this;
        }

        public AppiumAppBuilder platformVersion(String platformVersion) {
            this.platformVersion = platformVersion;
            return this;
        }

        public AppiumAppBuilder appPackage(String appPackage) {
            this.appPackage = appPackage;
            return this;
        }

        public AppiumAppBuilder appActivity(String appActivity) {
            this.appActivity = appActivity;
            return this;
        }

        public AppiumAppTest build() {
            AppiumAppTest app = new AppiumAppTest();

            app.dc.setCapability("deviceName", this.deviceName);
            app.dc.setCapability("udid", this.udid);
            app.dc.setCapability("platformName", this.platformName);
            app.dc.setCapability("autoGrantPermissions", true);
            app.dc.setCapability("platformVersion", this.platformVersion);
            app.dc.setCapability("appPackage", this.appPackage);
            app.dc.setCapability("appActivity", this.appActivity);
            return app;
        }

    }

    private AppiumAppTest() {
        dc = new DesiredCapabilities();
    }

    public boolean isConnected() {
        if (driver == null)
            return false;
        return true;
    }

    public void connectServer(String url) throws MalformedURLException {
        driver = new AndroidDriver(new URL(url), dc);
        System.out.println("Connection established to server");

    }

    public boolean new_test1() {
    	System.out.println("Test1 - Toplu Satış Giriş Ekranına Giriş Yapılacak");
    	final String xpath = 
    			"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ScrollView/android.widget.LinearLayout/android.widget.FrameLayout[2]/android.widget.LinearLayout";
    	final String id = "com.edasinar.profitability_proje_2:id/toplusatisButon";
    	WebElement element = null;
    	try {
			element = driver.findElement(AppiumBy.xpath(xpath));
			element.click();
			Thread.sleep(1000);
			element = driver.findElement(AppiumBy.id(id));
			element.click();
			String activity = driver.currentActivity();
			System.out.println("Aktivite adı: " + activity);
			if(!activity.equalsIgnoreCase(".CrowdsaleActivity")) {
				System.err.println("Test1 Başarısız");
				return false;
			}
			
		} catch (Exception e) {
			System.err.println("Test1 Başarısız");
			return false;
		}
    	System.out.println("Test1 Başarılı");
    	return true;
    }
    
    public boolean new_test2() {
    	System.out.println("\nTest2 - Seçim Ekranına Gidilecek");
    	final String buttonId = "com.edasinar.profitability_proje_2:id/dosyaSecButon";
    	WebElement element = null;
    	try {
			element = driver.findElement(AppiumBy.id(buttonId));
			element.click();
			Thread.sleep(2000);
			String alertText = driver.switchTo().alert().getText();
			System.out.println("Uyarı Metni: " + alertText);
			int buttonCount = driver.findElements(AppiumBy.className("android.widget.Button")).size();
			System.out.println(buttonCount + " seçim bulundu");
		} catch (Exception e) {
			System.err.println("Test2 Başarısız");
			return false;
		}
    	System.out.println("Test2 Başarılı");
    	return true;
    }
    
    public boolean new_test3() {
    	System.out.println("\nTest3 - Dosya Uzantısı Kontrol Edilecek");
    	List<WebElement> choiceList = null;
    	try {
    		choiceList = driver.findElements(AppiumBy.className("android.widget.Button"));
    		System.out.print("Seçimler: ");
    		Vector<String[]> extList = new Vector<String[]>();
    		int index = 0, loc = 0;
    		for(WebElement e:choiceList) {
    			System.out.print("[" + e.getAttribute("text") + "] ");
    			extList.add(e.getAttribute("text").split("\\."));
    			if(!extList.get(index)[1].equalsIgnoreCase("csv")) {
    				loc = index++;
    			}
    		}	
    		System.out.println("\nCsv uzantılı olmayan dosya seçiliyor...");
    		System.out.println("Seçim: " + choiceList.get(loc).getAttribute("text"));
    		choiceList.get(loc).click();
    		Thread.sleep(2000);
    		String warningMessage = driver.switchTo().alert().getText();
    		System.out.println(warningMessage);
    		driver.switchTo().alert().accept();
    		if(!warningMessage.equalsIgnoreCase("HATALI DOSYA TÜRÜ UYARISI!\n"
    				+ "Eklemek istediğiniz dosya csv uzantılı değildir. Lütfen başka dosya ekleyiniz")) {
    			System.err.println("Test3 Başarısız");
    			return false;
    		}
    			
    		
		} catch (Exception e) {
			System.err.println("Test3 Başarısız");
			return false;
		}
    	System.out.println("Test3 Başarılı");
    	return true;
    }
    
    public boolean new_test4() {
    	System.out.println("\nTest4 - Hatalı Dosya Seçilecek");
    	final String buttonId = "com.edasinar.profitability_proje_2:id/dosyaSecButon";
    	WebElement selectFileButton = null;
    	List<WebElement> choiceList = null;
    	final String invalidFilename = "MART.CSV";
    	try {
			selectFileButton = driver.findElement(AppiumBy.id(buttonId));
			selectFileButton.click();
			System.out.println("Dosya seçiliyor...");
			Thread.sleep(2000);
			choiceList = driver.findElements(AppiumBy.className("android.widget.Button"));
			System.out.println("Dosyalar bulundu");
			for(WebElement choice:choiceList) {
				String str = choice.getAttribute("text").replaceAll("\s", "");
				if(str.equalsIgnoreCase(invalidFilename)) {
					choice.click();
					System.out.println("Seçim: " + str);
					break;
				}
			}
			Thread.sleep(2000);
			String warningMessage = driver.switchTo().alert().getText();
			System.out.println(warningMessage);
			driver.switchTo().alert().accept();
			if(!warningMessage.equalsIgnoreCase("EKSİK BİLGİ UYARISI!\n"
					+ "Eklemek istediğiniz dosyalarda boşluklar vardır lütfen tüm boşlukları doldurup tekrar yüklemeyi deneyiniz.")){
    			System.err.println("Test4 Başarısız");
    			return false;
    		}
				
		} catch (Exception e) {
			System.err.println("Test4 Başarısız");
			return false;
		}
    	System.out.println("Test4 Başarılı");
    	return true;
    }
    
    public boolean new_test5() {
    	System.out.println("\nTest5 - Veritabanına Aktarım Kontrol Edilecek");
    	final String selectId = "com.edasinar.profitability_proje_2:id/dosyaSecButon";
    	final String integrateId = "com.edasinar.profitability_proje_2:id/entegreButon";
    	
    	WebElement filePicker = null;
    	WebElement integrateButton = null;
    	WebElement element = null;
    	try {
    		System.out.println("Dosya seçiliyor..");
			filePicker = driver.findElement(AppiumBy.id(selectId));
			filePicker.click();
			System.out.println("Seçim yapılıyor..");
			Thread.sleep(2000);
			element = driver.findElement(AppiumBy.id("android:id/button2"));
			element.click();
			System.out.println("Entegre ediliyor..");
			Thread.sleep(2000);
			integrateButton = driver.findElement(AppiumBy.id(integrateId));
			integrateButton.click();
			Thread.sleep(2000);
			String warningText = driver.switchTo().alert().getText();
			System.out.println("Sonuç: " + warningText);
			driver.switchTo().alert().accept();
			Thread.sleep(2000);
			System.out.println("Ana Menüye Dönülüyor");
			driver.navigate().back();
			driver.navigate().back();
			Thread.sleep(2000);
			String xpath = 
					"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ScrollView/android.widget.LinearLayout/android.widget.FrameLayout[5]/android.widget.LinearLayout";
			element = driver.findElement(AppiumBy.xpath(xpath));
			element.click();
			Thread.sleep(2000);
			System.out.println("Mayıs verileri kontrol ediliyor...");
			xpath = 
					"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.HorizontalScrollView/android.widget.LinearLayout/androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[5]/android.widget.TextView[2]";
			element = driver.findElement(AppiumBy.xpath(xpath));
			final int valueBeforeIntegration = 0;
			System.out.println("Mayıs brüt değeri: " + element.getAttribute("text"));
			if(element.getAttribute("text").equals(Integer.toString(valueBeforeIntegration))) {
				System.err.println("Test5 Başarısız");
				return false;
			}
		} catch (Exception e) {
			System.err.println("Test5 Başarısız");
			return false;
		}
    	System.out.println("Test5 Başarılı");
    	return true;
    }

    public static void main(String[] args) {
        AppiumAppTest a1 = new AppiumAppBuilder(AppiumAppBuilder.defaultDevice)
                .udid(AppiumAppBuilder.default_udid)
                .platformName(AppiumAppBuilder.defaultPlatform)
                .platformVersion(AppiumAppBuilder.defaultVersion)
                .appPackage(AppiumAppBuilder.defaultPackage)
                .appActivity(AppiumAppBuilder.defaultActivity)
                .build();
        try {
            a1.connectServer(AppiumAppTest.defaultUrl);
            final int wait_time = 2000; //milliseconds
            a1.new_test1();
            Thread.sleep(wait_time);
            a1.new_test2();
            Thread.sleep(wait_time);
            a1.new_test3();
            Thread.sleep(wait_time);
            a1.new_test4();
            Thread.sleep(wait_time);
            a1.new_test5();
            Thread.sleep(wait_time);
        } catch (MalformedURLException e1) {
            System.err.println("Baglanti Basarisiz");
        } catch (InterruptedException e) {
            System.err.println("Sleep Çalışmadı");
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
