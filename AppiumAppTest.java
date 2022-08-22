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

	/** // 1. test: Anasayfa */
	public boolean test1() {
		// 1. test: Anasayfa
		if (!this.isConnected()) {
			System.err.println("Once server'a baglanin lutfen");
			return false;
		}
		WebElement element = null;
		String elementText = new String();
		try {
			System.out.println("Test1");
			// anasayfa basligini kontrol et
			element = driver.findElement(AppiumBy.xpath(
					"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[1]/android.view.ViewGroup/android.widget.TextView"));
			elementText = element.getText();
			System.out.println("element: " + elementText);
			System.out.println("result : " + elementText.equalsIgnoreCase("            KARLILIK HESAPLAMA"));

			// urunler ekranina git
			element = driver.findElement(AppiumBy.xpath(
					"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ScrollView/android.widget.LinearLayout/android.widget.FrameLayout[1]/android.widget.LinearLayout"));
			element.click();
			// barkod basligini bul
			Thread.sleep(500);
			WebElement productAttributes[] = new WebElement[5];
			for (int index = 0, attempt = 0; index < 5;) {
				try {
					productAttributes[index] = driver.findElement(AppiumBy.xpath(
							"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.HorizontalScrollView/android.widget.LinearLayout/android.widget.LinearLayout[2]/android.widget.LinearLayout/android.widget.TextView["
									+ (index + 1) + "]"));
					System.out.println("element found: " + productAttributes[index].getText());
					++index;
					attempt = 0;
				} catch (Exception e) {
					System.err.println("Error on index: " + index + ". Retrying " + (++attempt) + "/3...");
					if (attempt == 3) {
						System.err.println("Test1 Başarısız");
						return false;
					}

				}
			}
		} catch (Exception e) {
			System.err.println("Test1 Başarısız");
			return false;
		}
		System.out.println("Test1 Başarılı");
		return true;
	}

	public boolean test2a() {
		System.out.println("\nTest 2a");
		try {
			// test2a
			WebElement elementFilter1 = driver
					.findElement(AppiumBy.id("com.edasinar.profitability_proje_2:id/barkodEditText"));
			String barcodeList[] = { "3lugoldset", "4LuGoldset4", "4LuGoldset3",
					"5LiGoldset7", "asimetrikcentik",
					"hasıryzk1", "YvrlkYzk1", "DgmYzk1", "incezincir1", "MrsVnsYzk",
					"YunusBlkYzk04", "İnceHltYzk03",
					"GozlukYzk02", "MinimalKalp" };
			WebElement filterButton = driver.findElement(AppiumBy.id("com.edasinar.profitability_proje_2:id/button"));

			for (int index = 0, attempt = 0; index < barcodeList.length; ++index) {
				elementFilter1.sendKeys(barcodeList[index]);
				filterButton.click();
				try {
					WebElement barcodeFound = driver.findElement(AppiumBy.xpath(
							("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.HorizontalScrollView/android.widget.LinearLayout/android.widget.LinearLayout[2]/androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout/android.widget.TextView[1]")));
					System.out.println(barcodeFound.getText());
					System.out.println("Barkod " + (index + 1) + " urunu : "
							+ ((barcodeList[index].equals(barcodeFound.getText())) ? "uyusuyor" : "uyusmuyor"));
				} catch (Exception e) {
					System.err.println("barkod " + (index + 1) + " bulunamadi. Tekrar deneniyor " + (++attempt) + "/3");
					if (attempt == 3) {
						System.err.println("Test2a Başarısız");
						return false;
					}
				}
			}
		} catch (Exception e) {
			System.err.println("Test2a Başarısız Aranılan bazı ögeler bulunamadı.");
			return false;
		}
		System.out.println("Test2a Başarılı");
		return true;
	}

	public boolean test2b() {
		System.out.println("\nTest2b");
		try {
			// test2b
			WebElement elementFilter2 = driver
					.findElement(AppiumBy.id("com.edasinar.profitability_proje_2:id/isimEditText"));
			elementFilter2.sendKeys("has");
			WebElement filterButton = driver.findElement(AppiumBy.id("com.edasinar.profitability_proje_2:id/button"));
			filterButton.click();
			try {
				WebElement result = driver
						.findElement(AppiumBy.id("com.edasinar.profitability_proje_2:id/barkodTextViewR"));

				System.out.println("Kısmi isimle ürün arama: "
						+ ((result.getText().equals("hasıryzk1")) ? "Başarılı" : "Başarısız"));
			} catch (Exception e) {
				System.err.println("Ürün bulunamadı");
			}

		} catch (Exception e) {
			System.err.println("Test2b Başarısız.");
			return false;
		}
		System.out.println("Test2b Başarılı");
		return true;
	}

	// test 3 için farklı bir yol izleyeceğiz, yol şu:
	// tam bir barkod gireceğiz tek bir ürün gelmesi lazım xpath değişkeninde
	// bir alt satıra gelecek degeri gireceğiz eğer ki driver o elemanı bulamazsa
	// test başarılıdır.
	/** // test3 */
	public boolean test3() {
		System.out.println("\nTest3");
		WebElement barcodeInput = null;
		WebElement filterButton = null;
		try {
			barcodeInput = driver.findElement(AppiumBy.id("com.edasinar.profitability_proje_2:id/barkodEditText"));
			filterButton = driver.findElement(AppiumBy.id("com.edasinar.profitability_proje_2:id/button"));
			barcodeInput.sendKeys("4LuGoldset4");
			filterButton.click();
			String xpathPrefixBarcode = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.HorizontalScrollView/android.widget.LinearLayout/android.widget.LinearLayout[2]/androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[";
			String xpathPostfixBarcode = "]/android.widget.TextView[1]";
			// burada xpath kullanarak ilk elemanı bulacağız ikinciyi bulmamamız gerekiyor
			WebElement elementExist = driver.findElement(AppiumBy.xpath(xpathPrefixBarcode + 1 + xpathPostfixBarcode));
			System.out.println("İlk eleman bulundu. Eleman barkod: " + elementExist.getText());
			WebElement elementNonExist = null;
			// bu eleman bulunamayacağı için catch bloğuna girmeli
			// normale göre ters bir şekilde işliyor
			try {
				elementNonExist = driver.findElement(AppiumBy.xpath(xpathPrefixBarcode + 2 + xpathPostfixBarcode));
				System.err.println("Test3 Hatalı. Eleman: " + elementNonExist.getText());
			} catch (Exception e) {
				System.out.println("Verilen barkod ile başka eleman bulunmadı");
				System.out.println("Test3 Başarılı");
				return true;
			}
		} catch (Exception e) {
			System.err.println("Aranılan eleman/elemanlar bulunamadı. Test3 Başarısız.");
			return false;
		}
		return false;
	}

	/** Test4 */
	public boolean test4() {
		WebElement filterButton = null;
		List<WebElement> elements = null;

		try {
			System.out.println("\nTest4");
			filterButton = driver.findElement(AppiumBy.id("com.edasinar.profitability_proje_2:id/button"));
			filterButton.click();
			elements = driver
					.findElements(AppiumBy.id("com.edasinar.profitability_proje_2:id/barkodTextViewR"));
			System.out.println("Eleman bulundu. Ekran kaydırmadan bulunan eleman sayısı: " + elements.size());

		} catch (Exception e) {
			System.err.println("Eleman bulunamadı. Test4 Başarısız.");
			return false;
		}
		System.out.println("Test4 başarılı.");
		return true;
	}

	/** test5 - devamı var */
	public boolean test5() {
		WebElement element = null;
		WebElement removedElement = null;
		try {
			System.out.println("\nTest5");
			removedElement = driver.findElement(AppiumBy.xpath(
					"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.HorizontalScrollView/android.widget.LinearLayout/android.widget.LinearLayout[2]/androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[5]/android.widget.TextView[1]"));
			String text = removedElement.getAttribute("text");
			System.out.println("Listenin 5. elemanının barkodu: " + text);
			System.out.println("Listenin 5. sıradaki elemanı için sil butonu aranıyor..");
			element = driver.findElement(AppiumBy.xpath(
					"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.HorizontalScrollView/android.widget.LinearLayout/android.widget.LinearLayout[2]/androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[5]/android.widget.Button"));
			System.out.println("Eleman bulundu buton adı: " + element.getText());
			element.click();
			System.out.println("Buton tıklandı. Eleman silindi mi kontrol ediliyor...");
			System.out.println("Ana menüye dönülüyor...");
			driver.navigate().back();
			System.out.println("Tekrar ürünler ekranına gidiliyor...");
			element = driver.findElement(AppiumBy.xpath(
					"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ScrollView/android.widget.LinearLayout/android.widget.FrameLayout[1]/android.widget.LinearLayout"));
			element.click();
			System.out.println("Silinen ürün aranıyor...");
			Thread.sleep(1000);
			removedElement = driver.findElement(AppiumBy.xpath(
					"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.HorizontalScrollView/android.widget.LinearLayout/android.widget.LinearLayout[2]/androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[5]/android.widget.TextView[1]"));

			String text_2 = removedElement.getAttribute("text");
			System.out.println("Listenin yeni 5. elemanının barkodu: " + text_2);
			System.out.println(!(text.equalsIgnoreCase(text_2)) ? "Test5 Başarılı" : "Test5 Başarısız");
			return (!(text.equalsIgnoreCase(text_2))) ? true : false;

		} catch (Exception e) {
			System.err.println("Eleman bulunamadı. Test5 Başarısız");
			return false;
		}
	}

	public boolean test6() {
		WebElement element = null;

		try {
			System.out.println("\nTest6");
			element = driver.findElement(AppiumBy.id("com.edasinar.profitability_proje_2:id/kaydet"));
			element.click();
			String prefix = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.TextView[";
			int counter = 0;
			for (int i = 1; i <= 6; ++i) {
				try {
					element = driver.findElement(AppiumBy.xpath(prefix + i + "]"));
					++counter;
					System.out.println("element found: " + element.getText());
				} catch (Exception e) {
					System.err.println("element not found: " + i);
				}
			}
			element = driver.findElement(AppiumBy.id("com.edasinar.profitability_proje_2:id/galeridenSecButton"));
			System.out.println(element.getText() + " butonu bulundu");
			System.out.println((counter == 0) ? "Test6 Başarısız" : "Test6 Başarılı");
			return (counter == 0) ? false : true;
		} catch (Exception e) {
			System.err.println("Test6 Başarısız");
			return false;
		}
	}

	public boolean test7() {
		String activity = null;
		try {
			System.out.println("\nTest7");
			activity = driver.currentActivity();
			System.out.println("Mevcut ekran aktivitesi: " + activity);
			WebElement element = driver
					.findElement(AppiumBy.id("com.edasinar.profitability_proje_2:id/galeridenSecButton"));
			element.click();
			System.out.println("Galeriden seç butonu tıklandı, galeriye giriliyor...");
			activity = driver.currentActivity();
			System.out.println("Resim seçiliyor...");
			element = driver.findElement(AppiumBy.id("com.google.android.apps.photos:id/image"));
			element.click();
			Boolean done = false;
			int attempt = 0;
			while (!done) {
				try {
					element = driver.findElement(AppiumBy.accessibilityId("Photo taken on Dec 18, 2021 1:52:27 AM"));
					element.click();
					done = true;
					System.out.println("Resim Bulundu");
				} catch (Exception e) {
					System.err.println("Resim bulunamadı tekrar deneniyor " + (++attempt) + "/3 ...");
					if (attempt == 3) {
						System.err.println("Test7 Resim bulunamadı. Test Başarısız.");
						return false;
					}
				}
			}
		} catch (Exception e) {
			System.err.println("Test7 Başarısız");
			return false;
		}
		System.out.println(((activity.equals(".picker.external.ExternalPickerActivity")) ? "Test7 Başarılı"
				: "Test7 Başarısız") + ". Mevcut Ekran Aktivitesi: " + activity);
		return (activity.equals(".picker.external.ExternalPickerActivity")) ? true : false;
	}

	public boolean test8() {
		try {
			System.out.println("\nTest8");
			WebElement element = null;
			Boolean done = false;
			int attempt = 0;
			while (!done) {
				try {
					element = driver
							.findElement(AppiumBy.id("com.edasinar.profitability_proje_2:id/newBarkodEditText"));
					System.out.println("Barkod girdisi bulundu.");
					done = true;
				} catch (Exception e) {
					System.err.println("Barkod girdisi bulunamadı" + (++attempt) + "/3. Tekrar deneniyor...");
					if (attempt == 3) {
						System.err.println("Test8 başarısız. Barkod girdisi bulunamadı.");
						return false;
					}
				}
			}
			element.sendKeys("bosluklu id");
			System.out.println("barkod girildi...");
			element = driver.findElement(AppiumBy.id("com.edasinar.profitability_proje_2:id/newKayit"));
			element.click();
			System.out.println("buton tıklandı, mesaj aranıyor...");
			String warningMessage = driver.switchTo().alert().getText();
			driver.switchTo().alert().accept();
			System.out.println("Uyarı mesajı,\n" + warningMessage);
		} catch (Exception e) {
			System.err.println("Test8 Başarısız");
			return false;
		}
		System.out.println("Test8 Başarılı");
		return true;
	}

	public boolean test9() {
		System.out.println("\nTest9");
		String prefix = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.EditText[";
		final int inputCount = 6;
		String inputs[] = { "ürünadi", "ürün216", "22", "Beyaz", "ürünmarka", "32" };
		WebElement element = null;
		System.out.println("Ekran aktivitesi: " + driver.currentActivity());
		for (int inputIndex = 1, attempt = 0; inputIndex <= inputCount;) {
			try {
				element = driver.findElement(AppiumBy.xpath(prefix + inputIndex + "]"));
				element.sendKeys(inputs[inputIndex - 1]);
				++inputIndex;
				attempt = 0;
			} catch (Exception e) {
				System.err.println("Error on element: " + inputIndex + (". (") + (++attempt) + "/3) Retrying...");
				if (attempt == 3) {
					System.err.println("Girdi alanları bulunamadı.");
					return false;
				}
			}
		}
		System.out.println("Girdi alanları dolduruldu.");
		try {
			element = driver.findElement(AppiumBy.id("com.edasinar.profitability_proje_2:id/newKayit"));
			element.click();
			System.out.println("Ürün Kaydedildi.");
			System.out.println("Mevcut ekran kontrol ediliyor...");
			System.out.println("Ekran aktivitesi: " + driver.currentActivity());
			System.out.println("Test9 Başarılı.");
		} catch (Exception e) {
			System.err.println("Test 9 Başarısız.");
			return false;
		}
		return true;
	}

	public boolean test10() {
		// ana menüye dön

		WebElement stockButton = null;
		WebElement inputs[] = new WebElement[6];
		System.out.println("\nTest10");
		// back to main menu
		System.out.println("Ana menüye dönülüyor...");
		for (int back = 0; back < 3; ++back) {
			driver.navigate().back();
		}

		try {
			System.out.println("Stok giriş ekranına geçiliyor...");
			stockButton = driver.findElement(AppiumBy.xpath(
					"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ScrollView/android.widget.LinearLayout/android.widget.FrameLayout[3]/android.widget.LinearLayout"));
			stockButton.click();
			System.out.println("Girişler yapılıyor...");
			String prefix = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.EditText[";
			String inputList[] = { "2022", "01", "01", "3lugoldset", "19.99", "10" };
			for (int index = 1, attempt = 0; index <= 6;) {
				try {
					inputs[index - 1] = driver.findElement(AppiumBy.xpath(prefix + index + "]"));
					inputs[index - 1].sendKeys(inputList[index - 1]);
					System.out.println("input : " + inputList[index - 1]);
					++index;
					attempt = 0;
				} catch (Exception e) {
					System.err.println("Error on index: " + index + (". (") + (++attempt) + "/3) Retrying...");
					if (attempt == 3) {
						System.err.println("Girdi alanları bulunamadı.");
						return false;
					}
				}
			}
			Thread.sleep(2000);
			WebElement saveButton = driver
					.findElement(AppiumBy.id("com.edasinar.profitability_proje_2:id/stokKaydetButton"));
			saveButton.click();
			System.out.println("Kaydedildi.");
			Thread.sleep(2000);
			for (int i = 0; i < 3; ++i) {
				try {
					stockButton = driver.findElement(AppiumBy.xpath(
							"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ScrollView/android.widget.LinearLayout/android.widget.FrameLayout[3]/android.widget.LinearLayout"));
					stockButton.click();
					break;
				} catch (Exception e) {
					System.err.println("stok ekranına giriş tekrar deneniyor (" + (i + 1) + "/3)...");
				}
			}
			Thread.sleep(2000);
			System.out.println("Stok ekranı kontrol ediliyor...");
			WebElement testElement = driver.findElement(AppiumBy.xpath(
					"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.EditText[1]"));
			System.out.println("Girdi kutucuğu değeri: " + testElement.getAttribute("text"));
			System.out.println((testElement.getAttribute("text").equalsIgnoreCase("Örnek: 2020"))
					? "Yertutucu değerine eşit yani Test10 Başarılı"
					: "Yertutucuyla eşit değil, Test10 Başarısız");
			return (testElement.getAttribute("text").equalsIgnoreCase("Örnek: 2020"))
					? true
					: false;
		} catch (Exception e) {
			System.err.println("Test10 Başarısız.");
			return false;
		}
	}

	public boolean test11() {
		driver.navigate().back();
		System.out.println("\nTest11");
		String prefix = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.EditText[";
		WebElement ordersMenu = null;
		String inputList[] = { "3lugoldset", "20220201", "73456822", "ayse yilmaz", "1", "19.99" };

		System.out.println("Ekrana gidiliyor...");

		for (int attempt = 0;;) {
			try {
				ordersMenu = driver.findElement(AppiumBy.xpath(
						"/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ScrollView/android.widget.LinearLayout/android.widget.FrameLayout[2]/android.widget.LinearLayout"));
				ordersMenu.click();
				break;
			} catch (Exception e) {
				System.err.println("Eleman bulunurken hata tekar deneniyor " + (++attempt) + ("/3. "));
				if (attempt == 3) {
					System.err.println("Test11 Başarısız. Eleman bulunamadı.");
					return false;
				}
			}
		}

		try {
			WebElement element = null;
			for (int index = 1; index <= inputList.length;) {
				try {
					element = driver.findElement(AppiumBy.xpath(prefix + index + "]"));
					System.out.println("Girdi veriliyor: " + inputList[index - 1]);
					element.sendKeys(inputList[index - 1]);
					++index;
					Thread.sleep(2000);
				} catch (Exception e) {
					System.err.println("Error on index: " + index + ". Retrying...");
				}
			}
			WebElement saveButton = driver
					.findElement(AppiumBy.id("com.edasinar.profitability_proje_2:id/siparisKaydetButton"));
			saveButton.click();
			System.out.println("Test11 Başarılı");
		} catch (Exception e) {
			System.err.println("Test11 Başarısız.");
			return false;
		}
		return true;
	}

	public boolean test12() {
		String prefix = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.EditText[";

		try {
			System.out.println("\nTest12");
			System.out.println("Kaydet butonuna basıldıktan sonra alanlar temizlendi mi kontrol ediliyor...");
			WebElement element = driver.findElement(AppiumBy.xpath(prefix + 1 + "]"));
			System.out.println("Girdi kutucuğu değeri: " + element.getAttribute("text"));
			System.out.println("Test12 " + ((element.getAttribute("text").equalsIgnoreCase("Örnek: 2020")) ? "başarılı."
					: "yarı başarılı. Çünkü alan temizlenmemiş."));

		} catch (Exception e) {
			System.err.println("Test12 başarısız. Eleman bulunamadı.");
			return false;
		}
		return true;
	}

	public boolean test13_14() {
		// ana menüye dön
		driver.navigate().back();
		String xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ScrollView/android.widget.LinearLayout/android.widget.FrameLayout[4]/android.widget.LinearLayout";
		String prefix = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.EditText[";
		WebElement element = null;
		WebElement saveButton = null;
		String inputList[] = { "2022", "10", "02", "3432", "İstege Bagli" };
		try {
			System.out.println("\nTest13");
			element = driver.findElement(AppiumBy.xpath(xpath));
			element.click();
			Thread.sleep(1000);
			saveButton = driver.findElement(AppiumBy.id("com.edasinar.profitability_proje_2:id/kaydetButton"));
			Thread.sleep(1000);
			for (int index = 1; index <= inputList.length;) {
				try {
					element = driver.findElement(AppiumBy.xpath(prefix + index + "]"));
					element.sendKeys(inputList[index - 1]);
					saveButton.click();
					System.out.println("Verilen girdi: " + inputList[index - 1]);
					Thread.sleep(2000);
					String warningText = driver.switchTo().alert().getText();
					System.out.println("Boş alan için verilen hata: " + warningText);
					driver.switchTo().alert().accept();
					++index;
				} catch (Exception e) {
					System.err.println("Eleman bulunurken hata. Tekrar deneniyor...");
				}
			}
		} catch (Exception e) {
			System.err.println("Test13 Başarısız eleman bulunamadı");
			return false;
		}

		try {
			System.out.println("\nTest14");
			for (int index = 1; index <= inputList.length;) {
				try {
					element = driver.findElement(AppiumBy.xpath(prefix + index + "]"));
					element.sendKeys(inputList[index - 1]);
					System.out.println("Verilen girdi: " + inputList[index - 1]);
					++index;
				} catch (Exception e) {
					System.err.println("Eleman bulunurken hata. Tekrar deneniyor...");
				}
			}
			saveButton.click();
			element = driver.findElement(AppiumBy.id("com.edasinar.profitability_proje_2:id/yilEditText"));
			Boolean result = element.getAttribute("text").equalsIgnoreCase("ÖRNEK: 2021");
			System.out.println("Test14 " + ((result) ? "Başarılı" : "Başarısız, kayıt tamamlanmadı"));
			driver.navigate().back();
		} catch (Exception e) {
			System.err.println("Test14 Başarısız, aranılan eleman bulunamadı.");
			return false;
		}
		return true;
	}

	/** Geri butonlarını tespit etme */
	public boolean test15() {
		WebElement element = null;
		final int count = 3;
		int counter = 0;
		System.out.println("\nTest15");
		String prefix = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ScrollView/android.widget.LinearLayout/android.widget.FrameLayout[";
		String postfix = "]/android.widget.LinearLayout";

		System.out.println("Ürünler ekranı geri butonu kontrol ediliyor...");
		try {
			element = driver.findElement(AppiumBy.xpath(prefix + 1 + postfix));
			element.click();
			Thread.sleep(1000);
			for (int attempt = 0; attempt < count; ++attempt) {
				try {
					element = driver.findElement(AppiumBy.accessibilityId("Navigate up"));
					System.out.println("Eleman bulundu. Açıklama: " + element.getAttribute("content-desc"));
					++counter;
					break;
				} catch (Exception e) {
					System.err.println("Eleman bulunamadı tekrar deneniyor");
				}
			}

			element = driver.findElement(AppiumBy.id("com.edasinar.profitability_proje_2:id/kaydet"));
			element.click();
			System.out.println("Ürün ekleme ekranı geri butonu kontrol ediliyor...");
			Thread.sleep(1000);
			for (int attempt = 0; attempt < count; ++attempt) {
				try {
					element = driver.findElement(AppiumBy.accessibilityId("Navigate up"));
					System.out.println("Eleman bulundu. Açıklama: " + element.getAttribute("content-desc"));
					++counter;
					break;
				} catch (Exception e) {
					System.err.println("Eleman bulunamadı tekrar deneniyor");
				}
			}

			System.out.println("Ana sayfaya dönülüyor...");
			driver.navigate().back();
			driver.navigate().back();

			System.out.println("Sipariş ekranına giriliyor...");
			element = driver.findElement(AppiumBy.xpath(prefix + 2 + postfix));
			element.click();
			System.out.println("Sipariş ekleme ekranı geri butonu kontrol ediliyor...");
			Thread.sleep(1000);
			for (int attempt = 0; attempt < count; ++attempt) {
				try {
					element = driver.findElement(AppiumBy.accessibilityId("Navigate up"));
					System.out.println("Eleman bulundu. Açıklama: " + element.getAttribute("content-desc"));
					++counter;
					break;
				} catch (Exception e) {
					System.err.println("Eleman bulunamadı tekrar deneniyor");
				}
			}
			System.out.println("Ana sayfaya dönülüyor...");
			driver.navigate().back();

			System.out.println("Stok giriş ekranına giriliyor...");
			element = driver.findElement(AppiumBy.xpath(prefix + 3 + postfix));
			element.click();
			System.out.println("Stok giriş ekranı geri butonu kontrol ediliyor...");
			Thread.sleep(1000);
			for (int attempt = 0; attempt < count; ++attempt) {
				try {
					element = driver.findElement(AppiumBy.accessibilityId("Navigate up"));
					System.out.println("Eleman bulundu. Açıklama: " + element.getAttribute("content-desc"));
					++counter;
					break;
				} catch (Exception e) {
					System.err.println("Eleman bulunamadı tekrar deneniyor");
				}
			}
			System.out.println("Ana sayfaya dönülüyor...");
			driver.navigate().back();

			System.out.println("Harcamalar giriş ekranına giriliyor...");
			element = driver.findElement(AppiumBy.xpath(prefix + 4 + postfix));
			element.click();
			System.out.println("Harcamalar giriş ekranı geri butonu kontrol ediliyor...");
			Thread.sleep(1000);
			for (int attempt = 0; attempt < count; ++attempt) {
				try {
					element = driver.findElement(AppiumBy.accessibilityId("Navigate up"));
					System.out.println("Eleman bulundu. Açıklama: " + element.getAttribute("content-desc"));
					++counter;
					break;
				} catch (Exception e) {
					System.err.println("Eleman bulunamadı tekrar deneniyor");
				}
			}
			System.out.println("Ana sayfaya dönülüyor...");
			driver.navigate().back();

			Thread.sleep(2000);
			System.out.println("Net kar ekranına giriliyor...");
			element = driver.findElement(AppiumBy.xpath(prefix + 5 + postfix));
			element.click();
			System.out.println("Net kar ekranı geri butonu kontrol ediliyor...");
			Thread.sleep(1000);
			for (int attempt = 0; attempt < count; ++attempt) {
				try {
					element = driver.findElement(AppiumBy.accessibilityId("Navigate up"));
					System.out.println("Eleman bulundu. Açıklama: " + element.getAttribute("content-desc"));
					++counter;
					break;
				} catch (Exception e) {
					System.err.println("Eleman bulunamadı tekrar deneniyor");
				}
			}
			System.out.println("Ana sayfaya dönülüyor...");
			driver.navigate().back();
			System.out.println("Başarılı geri butonu testi sayısı : " + counter + "/6");
		} catch (Exception e) {
			System.err.println("Test15 başarısız");
			return false;
		}
		return true;
	}

	public boolean new_test1() {
		if (!isConnected()) {
			System.err.println("Önce server'a bağlanın. Test başarısız");
			return false;
		}
		System.out.println("Test1 - Toplu Satış Giriş Ekranına Giriş Yapılacak");
		final String xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ScrollView/android.widget.LinearLayout/android.widget.FrameLayout[2]/android.widget.LinearLayout";
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
			if (!activity.equalsIgnoreCase(".CrowdsaleActivity")) {
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
		if (!isConnected()) {
			System.err.println("Önce server'a bağlanın. Test başarısız");
			return false;
		}
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
		if (!isConnected()) {
			System.err.println("Önce server'a bağlanın. Test başarısız");
			return false;
		}
		System.out.println("\nTest3 - Dosya Uzantısı Kontrol Edilecek");
		List<WebElement> choiceList = null;
		try {
			choiceList = driver.findElements(AppiumBy.className("android.widget.Button"));
			System.out.print("Seçimler: ");
			Vector<String[]> extList = new Vector<String[]>();
			int index = 0, loc = 0;
			for (WebElement e : choiceList) {
				System.out.print("[" + e.getAttribute("text") + "] ");
				extList.add(e.getAttribute("text").split("\\."));
				if (!extList.get(index)[1].equalsIgnoreCase("csv")) {
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
			if (!warningMessage.equalsIgnoreCase("HATALI DOSYA TÜRÜ UYARISI!\n"
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
		if (!isConnected()) {
			System.err.println("Önce server'a bağlanın. Test başarısız");
			return false;
		}
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
			for (WebElement choice : choiceList) {
				String str = choice.getAttribute("text").replaceAll("\s", "");
				if (str.equalsIgnoreCase(invalidFilename)) {
					choice.click();
					System.out.println("Seçim: " + str);
					break;
				}
			}
			Thread.sleep(2000);
			String warningMessage = driver.switchTo().alert().getText();
			System.out.println(warningMessage);
			driver.switchTo().alert().accept();
			if (!warningMessage.equalsIgnoreCase("EKSİK BİLGİ UYARISI!\n"
					+ "Eklemek istediğiniz dosyalarda boşluklar vardır lütfen tüm boşlukları doldurup tekrar yüklemeyi deneyiniz.")) {
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
		if (!isConnected()) {
			System.err.println("Önce server'a bağlanın. Test başarısız");
			return false;
		}
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
			String xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.ScrollView/android.widget.LinearLayout/android.widget.FrameLayout[5]/android.widget.LinearLayout";
			element = driver.findElement(AppiumBy.xpath(xpath));
			element.click();
			Thread.sleep(2000);
			System.out.println("Mayıs verileri kontrol ediliyor...");
			xpath = "/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.view.ViewGroup/android.widget.HorizontalScrollView/android.widget.LinearLayout/androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[5]/android.widget.TextView[2]";
			element = driver.findElement(AppiumBy.xpath(xpath));
			final int valueBeforeIntegration = 0;
			System.out.println("Mayıs brüt değeri: " + element.getAttribute("text"));
			if (element.getAttribute("text").equals(Integer.toString(valueBeforeIntegration))) {
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

	// public static void main(String[] args) {
	// 	final int sleep_time = 2000;

	// 	AppiumAppTest a1 = new AppiumAppBuilder(AppiumAppBuilder.defaultDevice)
	// 			.udid(AppiumAppBuilder.default_udid)
	// 			.platformName(AppiumAppBuilder.defaultPlatform)
	// 			.platformVersion(AppiumAppBuilder.defaultVersion)
	// 			.appPackage(AppiumAppBuilder.defaultPackage)
	// 			.appActivity(AppiumAppBuilder.defaultActivity)
	// 			.build();
	// 	try {
	// 		a1.connectServer(AppiumAppTest.defaultUrl);
	// 		a1.test15();
	// 		Thread.sleep(sleep_time);
	// 		a1.test1();
	// 		Thread.sleep(sleep_time);
	// 		a1.test2a();
	// 		Thread.sleep(sleep_time);
	// 		a1.test2b();
	// 		Thread.sleep(sleep_time);
	// 		a1.test3();
	// 		Thread.sleep(sleep_time);
	// 		a1.test4();
	// 		Thread.sleep(sleep_time);
	// 		a1.test5();
	// 		Thread.sleep(sleep_time);
	// 		a1.test6();
	// 		Thread.sleep(sleep_time);
	// 		a1.test7();
	// 		Thread.sleep(sleep_time);
	// 		a1.test8();
	// 		Thread.sleep(sleep_time);
	// 		a1.test9();
	// 		Thread.sleep(sleep_time);
	// 		a1.test10();
	// 		Thread.sleep(sleep_time);
	// 		a1.test11();
	// 		Thread.sleep(sleep_time);
	// 		a1.test12();
	// 		Thread.sleep(sleep_time);
	// 		a1.test13_14();
	// 		Thread.sleep(sleep_time);
			
	// 	} catch (MalformedURLException e1) {
	// 		System.err.println("Baglanti Basarisiz");
	// 	} catch (InterruptedException e) {
	// 		System.err.println("Sleep Çalışmadı");
	// 	}

	// 	try {
	// 		Thread.sleep(5000);
	// 	} catch (InterruptedException e) {
	// 		e.printStackTrace();
	// 	}

	// }

}
