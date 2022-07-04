package rs.etf.sab.operations;

import java.util.List;
import javax.validation.constraints.NotNull;

public interface DriveOperation {
  boolean planingDrive(String paramString);
  
  int nextStop(@NotNull String paramString);
  
  List<Integer> getPackagesInVehicle(String paramString);
}


/* Location:              C:\Users\vulov\Downloads\SAB_projekat_2122\SAB_projekat_2122\SAB_projekat_2122.jar!\rs\etf\sab\operations\DriveOperation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */