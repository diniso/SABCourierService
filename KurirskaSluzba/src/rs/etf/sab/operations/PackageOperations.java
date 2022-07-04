package rs.etf.sab.operations;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import javax.validation.constraints.NotNull;

public interface PackageOperations {
  int insertPackage(int paramInt1, int paramInt2, @NotNull String paramString, int paramInt3, BigDecimal paramBigDecimal);
  
  boolean acceptAnOffer(int paramInt);
  
  boolean rejectAnOffer(int paramInt);
  
  List<Integer> getAllPackages();
  
  List<Integer> getAllPackagesWithSpecificType(int paramInt);
  
  List<Integer> getAllUndeliveredPackages();
  
  List<Integer> getAllUndeliveredPackagesFromCity(int paramInt);
  
  List<Integer> getAllPackagesCurrentlyAtCity(int paramInt);
  
  boolean deletePackage(int paramInt);
  
  boolean changeWeight(int paramInt, @NotNull BigDecimal paramBigDecimal);
  
  boolean changeType(int paramInt1, int paramInt2);
  
  int getDeliveryStatus(int paramInt);
  
  BigDecimal getPriceOfDelivery(int paramInt);
  
  int getCurrentLocationOfPackage(int paramInt);
  
  Date getAcceptanceTime(int paramInt);
}


/* Location:              C:\Users\vulov\Downloads\SAB_projekat_2122\SAB_projekat_2122\SAB_projekat_2122.jar!\rs\etf\sab\operations\PackageOperations.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */