import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.MalformedURLException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestClass {
    private static AppiumAppTest a1;
    private static Boolean resultList[];
    private static int counter;
    
    @BeforeAll
    public static void initTest() {
        a1 = new AppiumAppTest.AppiumAppBuilder(AppiumAppTest.AppiumAppBuilder.defaultDevice)
                .udid(AppiumAppTest.AppiumAppBuilder.default_udid)
                .platformName(AppiumAppTest.AppiumAppBuilder.defaultPlatform)
                .platformVersion(AppiumAppTest.AppiumAppBuilder.defaultVersion)
                .appPackage(AppiumAppTest.AppiumAppBuilder.defaultPackage)
                .appActivity(AppiumAppTest.AppiumAppBuilder.defaultActivity)
                .build();
        resultList = new Boolean[5];
        counter = 0;
        try {
            a1.connectServer(AppiumAppTest.defaultUrl);
        } catch (MalformedURLException e1) {
            System.err.println("Baglanti Basarisiz");
        } 
    }
    
    @Order(1)
    @Test
    public void test1() {
        final int wait_time = 2000; // milliseconds
            
            resultList[0] = a1.new_test1();
            assertTrue(resultList[0], "Test 1");
            if(resultList[0])
                ++counter;
            try {
                Thread.sleep(wait_time);
            } catch (InterruptedException e) {
                
                e.printStackTrace();
            }
    }
    
    @Order(2)
    @Test
    public void test2() {
        final int wait_time = 2000; // milliseconds
            
            resultList[1] = a1.new_test2();
            assertTrue(resultList[1], "Test 2");
            if(resultList[1])
                ++counter;
            try {
                Thread.sleep(wait_time);
            } catch (InterruptedException e) {
                
                e.printStackTrace();
            }
    }

    @Order(3)
    @Test
    public void test3() {
        final int wait_time = 2000; // milliseconds
            
            resultList[2] = a1.new_test3();
            assertTrue(resultList[2], "Test 3");
            if(resultList[2])
                ++counter;
            try {
                Thread.sleep(wait_time);
            } catch (InterruptedException e) {
                
                e.printStackTrace();
            }
    }

    @Order(4)
    @Test
    public void test4() {
        final int wait_time = 2000; // milliseconds
            
            resultList[3] = a1.new_test4();
            assertTrue(resultList[3], "Test 4");
            if(resultList[3])
                ++counter;
            try {
                Thread.sleep(wait_time);
            } catch (InterruptedException e) {
                
                e.printStackTrace();
            }
    }

    @Order(5)
    @Test
    public void test5() {
        final int wait_time = 2000; // milliseconds
            
            resultList[4] = a1.new_test5();
            a1.driver.navigate().back();
            assertTrue(resultList[4], "Test 5");
            if(resultList[4])
                ++counter;
            try {
                Thread.sleep(wait_time);
            } catch (InterruptedException e) {
                
                e.printStackTrace();
            }
    }
//
//    @Order(6)
//    @Test
//    public void test_old_1() {
//        final int wait_time = 2000; // milliseconds
//            
//            resultList[5] = a1.test15();
//            assertTrue(resultList[5], "Old Test 15");
//            if(resultList[5])
//                ++counter;
//            try {
//                Thread.sleep(wait_time);
//            } catch (InterruptedException e) {
//                
//                e.printStackTrace();
//            }
//    }
//
//    @Order(7)
//    @Test
//    public void test_old_2() {
//        final int wait_time = 2000; // milliseconds
//            
//            resultList[6] = a1.test1();
//            assertTrue(resultList[6], "Old Test 1");
//            if(resultList[5])
//                ++counter;
//            try {
//                Thread.sleep(wait_time);
//            } catch (InterruptedException e) {
//                
//                e.printStackTrace();
//            }
//    }
//
//    @Order(8)
//    @Test
//    public void test_old_3() {
//        final int wait_time = 2000; // milliseconds
//            
//            resultList[7] = (a1.test2a() && a1.test2b());
//            assertTrue(resultList[7], "Old Test 2");
//            if(resultList[7])
//                ++counter;
//            try {
//                Thread.sleep(wait_time);
//            } catch (InterruptedException e) {
//                
//                e.printStackTrace();
//            }
//    }
//
//    @Order(9)
//    @Test
//    public void test_old_4() {
//        final int wait_time = 2000; // milliseconds
//            
//            resultList[8] = a1.test3();
//            assertTrue(resultList[8], "Old Test 3");
//            if(resultList[8])
//                ++counter;
//            try {
//                Thread.sleep(wait_time);
//            } catch (InterruptedException e) {
//                
//                e.printStackTrace();
//            }
//    }
//
//    @Order(10)
//    @Test
//    public void test_old_5() {
//        final int wait_time = 2000; // milliseconds
//            
//            resultList[9] = a1.test4();
//            assertTrue(resultList[9], "Old Test 4");
//            if(resultList[9])
//                ++counter;
//            try {
//                Thread.sleep(wait_time);
//            } catch (InterruptedException e) {
//                
//                e.printStackTrace();
//            }
//    }
//
//    @Order(11)
//    @Test
//    public void test_old_6() {
//        final int wait_time = 2000; // milliseconds
//            
//            resultList[10] = a1.test5();
//            assertTrue(resultList[10], "Old Test 5");
//            if(resultList[10])
//                ++counter;
//            try {
//                Thread.sleep(wait_time);
//            } catch (InterruptedException e) {
//                
//                e.printStackTrace();
//            }
//    }
//
//    @Order(12)
//    @Test
//    public void test_old_7() {
//        final int wait_time = 2000; // milliseconds
//            
//            resultList[11] = a1.test6();
//            assertTrue(resultList[11], "Old Test 6");
//            if(resultList[11])
//                ++counter;
//            try {
//                Thread.sleep(wait_time);
//            } catch (InterruptedException e) {
//                
//                e.printStackTrace();
//            }
//    }
//
//    @Order(13)
//    @Test
//    public void test_old_8() {
//        final int wait_time = 2000; // milliseconds
//            
//            resultList[12] = a1.test7();
//            assertTrue(resultList[12], "Old Test 7");
//            if(resultList[12])
//                ++counter;
//            try {
//                Thread.sleep(wait_time);
//            } catch (InterruptedException e) {
//                
//                e.printStackTrace();
//            }
//    }
//
//    @Order(14)
//    @Test
//    public void test_old_9() {
//        final int wait_time = 2000; // milliseconds
//            
//            resultList[13] = a1.test8();
//            assertTrue(resultList[13], "Old Test 8");
//            if(resultList[13])
//                ++counter;
//            try {
//                Thread.sleep(wait_time);
//            } catch (InterruptedException e) {
//                
//                e.printStackTrace();
//            }
//    }
//    
//    @Order(15)
//    @Test
//    public void test_old_10() {
//        final int wait_time = 2000; // milliseconds
//            
//            resultList[14] = a1.test9();
//            assertTrue(resultList[14], "Old Test 9");
//            if(resultList[14])
//                ++counter;
//            try {
//                Thread.sleep(wait_time);
//            } catch (InterruptedException e) {
//                
//                e.printStackTrace();
//            }
//    }
//
//    @Order(16)
//    @Test
//    public void test_old_11() {
//        final int wait_time = 2000; // milliseconds
//            
//            resultList[15] = a1.test10();
//            assertTrue(resultList[15], "Old Test 10");
//            if(resultList[15])
//                ++counter;
//            try {
//                Thread.sleep(wait_time);
//            } catch (InterruptedException e) {
//                
//                e.printStackTrace();
//            }
//    }
//
//    @Order(17)
//    @Test
//    public void test_old_12() {
//        final int wait_time = 2000; // milliseconds
//            
//            resultList[16] = a1.test11();
//            assertTrue(resultList[16], "Old Test 11");
//            if(resultList[16])
//                ++counter;
//            try {
//                Thread.sleep(wait_time);
//            } catch (InterruptedException e) {
//                
//                e.printStackTrace();
//            }
//    }
//
//    @Order(18)
//    @Test
//    public void test_old_13() {
//        final int wait_time = 2000; // milliseconds
//            
//            resultList[17] = a1.test12();
//            assertTrue(resultList[17], "Old Test 12");
//            if(resultList[17])
//                ++counter;
//            try {
//                Thread.sleep(wait_time);
//            } catch (InterruptedException e) {
//                
//                e.printStackTrace();
//            }
//    }
//    
//    @Order(19)
//    @Test
//    public void test_old_13_14() {
//        final int wait_time = 2000; // milliseconds
//            
//            resultList[18] = a1.test13_14();
//            assertTrue(resultList[18], "Old Test 13 - 14");
//            if(resultList[18])
//                ++counter;
//            try {
//                Thread.sleep(wait_time);
//            } catch (InterruptedException e) {
//                
//                e.printStackTrace();
//            }
//    }

    @AfterAll
    public static void results() {
        System.out.println("Başaralı Test Oranı: " + counter + "/" + resultList.length);
    }
}