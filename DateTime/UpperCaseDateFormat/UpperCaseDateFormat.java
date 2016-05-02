import java.util.*;
import java.text.*;

/** DateFormat that is upper case particularly great for spring bean factory that takes only DateFormat */
public class UpperCaseDateFormat extends DateFormat {

  public static void main(String[] args) {
    Date now = new Date();
    DateFormat mmMmm = new SimpleDateFormat("MM-MMM");
    DateFormat mmMMM = new UpperCaseDateFormat(mmMmm);
    System.out.println(mmMmm.format(now) + "=>" + mmMMM.format(now));
    DateFormat fullDf = DateFormat.getDateInstance(DateFormat.FULL);
    DateFormat fullDF = new UpperCaseDateFormat(fullDf);
    System.out.println(fullDf.format(now) + "=>" + fullDF.format(now));
  }

  private final DateFormat delegate;

  public UpperCaseDateFormat(DateFormat delegate) {
    this.delegate = delegate;
  }

  public StringBuffer format(Date date, StringBuffer toAppendTo, java.text.FieldPosition fieldPosition) {
    return new StringBuffer(delegate.format(date, toAppendTo, fieldPosition).toString().toUpperCase());
  };

  @Override
  public Date parse(String source, ParsePosition pos) {
    return delegate.parse(source, pos);
  }
};
