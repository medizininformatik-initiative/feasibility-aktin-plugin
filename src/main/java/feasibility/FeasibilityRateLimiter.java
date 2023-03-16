package feasibility;

import org.joda.time.LocalDateTime;

public class FeasibilityRateLimiter {

  private int requestCount = 0;
  private int maxRequestCount;
  private LocalDateTime lastClearing = new org.joda.time.LocalDateTime();
  private int requestCountClearingTimeMinutes;
  private boolean rejectRequests = false;


  public FeasibilityRateLimiter(int maxRequestCount,
      int requestCountClearingTimeMinutes) {
    this.maxRequestCount = maxRequestCount;
    this.requestCountClearingTimeMinutes = requestCountClearingTimeMinutes;
  }

  public boolean limitRequests(){

    if (this.rejectRequests){
      return true;
    }

    LocalDateTime curDate = new LocalDateTime();
    if (curDate.minusMinutes(requestCountClearingTimeMinutes).isAfter(lastClearing)){
      this.requestCount = 0;
      this.lastClearing = curDate;
    }

    if (this.requestCount >= maxRequestCount){
      this.rejectRequests = true;
      return true;
    }

    this.requestCount++;

    return false;

  }


}
