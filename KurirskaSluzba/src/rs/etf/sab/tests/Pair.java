/*    */ package rs.etf.sab.tests;
/*    */ 
/*    */ public class Pair<K, V>
/*    */ {
/*    */   private final K element0;
/*    */   private final V element1;
/*    */   
/*    */   public static <K, V> Pair<K, V> createPair(K element0, V element1) {
/*  9 */     return new Pair<>(element0, element1);
/*    */   }
/*    */   
/*    */   public Pair(K element0, V element1) {
/* 13 */     this.element0 = element0;
/* 14 */     this.element1 = element1;
/*    */   }
/*    */   
/*    */   public K getKey() {
/* 18 */     return this.element0;
/*    */   }
/*    */   
/*    */   public V getValue() {
/* 22 */     return this.element1;
/*    */   }
/*    */ }


/* Location:              C:\Users\vulov\Downloads\SAB_projekat_2122\SAB_projekat_2122\SAB_projekat_2122.jar!\rs\etf\sab\tests\Pair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */