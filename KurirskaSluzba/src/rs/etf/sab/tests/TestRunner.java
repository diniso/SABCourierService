/*     */ package rs.etf.sab.tests;
/*     */ 
/*     */ import org.junit.runner.JUnitCore;
/*     */ import org.junit.runner.Request;
/*     */ import org.junit.runner.Result;
/*     */ 
/*     */ public final class TestRunner
/*     */ {
/*     */   private static final int MAX_POINTS_ON_PUBLIC_TEST = 10;
/*  10 */   private static final Class[] UNIT_TEST_CLASSES = new Class[] { CityOperationsTest.class, AddressOperationsTest.class, UserOperationsTest.class, CourierRequestOperationTest.class, StockroomOperationsTest.class, VehicleOperationsTest.class };
/*     */ 
/*     */ 
/*     */   
/*  14 */   private static final Class[] UNIT_TEST_CLASSES_PRIVATE = new Class[0];
/*     */   
/*  16 */   private static final Class[] MODULE_TEST_CLASSES = new Class[] { PublicModuleTest.class };
/*  17 */   private static final Class[] MODULE_TEST_CLASSES_PRIVATE = new Class[0];
/*     */ 
/*     */   
/*     */   private static double runUnitTestsPublic() {
/*  21 */     int numberOfSuccessfulCases = 0;
/*  22 */     int numberOfAllCases = 0;
/*  23 */     double points = 0.0D;
/*  24 */     JUnitCore jUnitCore = new JUnitCore();
/*     */     
/*  26 */     for (Class testClass : UNIT_TEST_CLASSES) {
/*  27 */       System.out.println("\n" + testClass.getName());
/*     */       
/*  29 */       Request request = Request.aClass(testClass);
/*  30 */       Result result = jUnitCore.run(request);
/*     */       
/*  32 */       numberOfAllCases = result.getRunCount();
/*  33 */       numberOfSuccessfulCases = result.getRunCount() - result.getFailureCount();
/*  34 */       if (numberOfSuccessfulCases < 0)
/*  35 */         numberOfSuccessfulCases = 0; 
/*  36 */       System.out.println("Successful: " + numberOfSuccessfulCases);
/*  37 */       System.out.println("All: " + numberOfAllCases);
/*  38 */       double points_curr = numberOfSuccessfulCases * 1.0D / numberOfAllCases;
/*  39 */       System.out.println("Points: " + points_curr);
/*  40 */       points += points_curr;
/*     */     } 
/*     */ 
/*     */     
/*  44 */     return points;
/*     */   }
/*     */   
/*     */   private static double runModuleTestsPublic() {
/*  48 */     int numberOfSuccessfulCases = 0;
/*  49 */     int numberOfAllCases = 0;
/*  50 */     double points = 0.0D;
/*  51 */     JUnitCore jUnitCore = new JUnitCore();
/*     */     
/*  53 */     for (Class testClass : MODULE_TEST_CLASSES) {
/*  54 */       System.out.println("\n" + testClass.getName());
/*     */       
/*  56 */       Request request = Request.aClass(testClass);
/*  57 */       Result result = jUnitCore.run(request);
/*     */       
/*  59 */       numberOfAllCases = result.getRunCount();
/*  60 */       numberOfSuccessfulCases = result.getRunCount() - result.getFailureCount();
/*  61 */       if (numberOfSuccessfulCases < 0)
/*  62 */         numberOfSuccessfulCases = 0; 
/*  63 */       System.out.println("Successful: " + numberOfSuccessfulCases);
/*  64 */       System.out.println("All: " + numberOfAllCases);
/*  65 */       double points_curr = numberOfSuccessfulCases * 2.0D;
/*  66 */       System.out.println("Points: " + points_curr);
/*  67 */       points += points_curr;
/*     */     } 
/*     */ 
/*     */     
/*  71 */     return points;
/*     */   }
/*     */   
/*     */   private static double runPublic() {
/*  75 */     double res = 0.0D;
/*  76 */     res += runUnitTestsPublic();
/*  77 */     res += runModuleTestsPublic();
/*  78 */     return res;
/*     */   }
/*     */   
/*     */   private static double runUnitTestsPrivate() {
/*  82 */     int numberOfSuccessfulCases = 0;
/*  83 */     int numberOfAllCases = 0;
/*  84 */     double points = 0.0D;
/*  85 */     JUnitCore jUnitCore = new JUnitCore();
/*     */     
/*  87 */     for (Class testClass : UNIT_TEST_CLASSES_PRIVATE) {
/*  88 */       System.out.println("\n" + testClass.getName());
/*     */       
/*  90 */       Request request = Request.aClass(testClass);
/*  91 */       Result result = jUnitCore.run(request);
/*     */       
/*  93 */       numberOfAllCases = result.getRunCount();
/*  94 */       numberOfSuccessfulCases = result.getRunCount() - result.getFailureCount();
/*  95 */       if (numberOfSuccessfulCases < 0)
/*  96 */         numberOfSuccessfulCases = 0; 
/*  97 */       System.out.println("Successful: " + numberOfSuccessfulCases);
/*  98 */       System.out.println("All: " + numberOfAllCases);
/*  99 */       double points_curr = numberOfSuccessfulCases * 1.0D / numberOfAllCases;
/* 100 */       System.out.println("Points: " + points_curr);
/* 101 */       points += points_curr;
/*     */     } 
/*     */     
/* 104 */     return points;
/*     */   }
/*     */   
/*     */   private static double runModuleTestsPrivate() {
/* 108 */     int numberOfSuccessfulCases = 0;
/* 109 */     int numberOfAllCases = 0;
/* 110 */     double points = 0.0D;
/* 111 */     JUnitCore jUnitCore = new JUnitCore();
/*     */     
/* 113 */     for (Class testClass : MODULE_TEST_CLASSES_PRIVATE) {
/* 114 */       System.out.println("\n" + testClass.getName());
/*     */       
/* 116 */       Request request = Request.aClass(testClass);
/* 117 */       Result result = jUnitCore.run(request);
/*     */       
/* 119 */       numberOfAllCases = result.getRunCount();
/* 120 */       numberOfSuccessfulCases = result.getRunCount() - result.getFailureCount();
/* 121 */       if (numberOfSuccessfulCases < 0)
/* 122 */         numberOfSuccessfulCases = 0; 
/* 123 */       System.out.println("Successful:" + numberOfSuccessfulCases);
/* 124 */       System.out.println("All:" + numberOfAllCases);
/* 125 */       double points_curr = numberOfSuccessfulCases * 2.0D;
/* 126 */       System.out.println("Points: " + points_curr);
/* 127 */       points += points_curr;
/*     */     } 
/*     */     
/* 130 */     return points;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static double runPrivate() {
/* 136 */     double res = 0.0D;
/* 137 */     res += runUnitTestsPrivate();
/* 138 */     res += runModuleTestsPrivate();
/* 139 */     return res;
/*     */   }
/*     */   
/*     */   public static void runTests() {
/* 143 */     double resultsPublic = runPublic();
/* 144 */     System.out.println("Points won on public tests is: " + resultsPublic + " out of 10.0");
/*     */   }
/*     */ }


/* Location:              C:\Users\vulov\Downloads\SAB_projekat_2122\SAB_projekat_2122\SAB_projekat_2122.jar!\rs\etf\sab\tests\TestRunner.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */