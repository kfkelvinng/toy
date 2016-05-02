import java.util.Arrays;
import java.util.Iterator;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


public class ComboOps {
  public enum Arithmetic {
    A("+") {  double compute(double x, double y) { return x + y; } },
    S("-") {  double compute(double x, double y) { return x - y; } },
    M("*") {  double compute(double x, double y) { return x * y; } },
    D("/") {  double compute(double x, double y) { return x / y; } };
    
    abstract double compute(double x, double y);
    
    String s;
    
    Arithmetic (String s) {
      this.s = s;
    }
    
    @Override
    public String toString() {
      return s;
    }
    
  }
  
  private static class CombinationIterator implements Iterator<Arithmetic[]> {
    
    int length;
    
    Arithmetic[] current = null; 
    
    public static boolean isLastOne(Arithmetic a) {
      return a.ordinal() == Arithmetic.values().length-1;
    }
    
    public CombinationIterator(int length) {
      this.length = length;
    }
    
    /**
     * return true if for all i in current isLastOne(i)
     */
    @Override
    public boolean hasNext() {
      if (current == null) return true;
      for (Arithmetic i : current) {
        if (!isLastOne(i)) {
          return true;
        }
      }
      return false;
    }

    @Override
    public Arithmetic[] next() {
      if (current == null) {
        current = new Arithmetic[length-1];
        Arrays.fill(current, Arithmetic.values()[0]);
        return current;
      }
      
      for (int i = current.length-1 ; i >= 0; i--) {
        current[i] = Arithmetic.values()[isLastOne(current[i]) ? 0: current[i].ordinal()+1];
        if (current[i].ordinal() != 0) {
          break;
        }
      }
      return current;
    }
    
    @Override public void remove() { throw new UnsupportedOperationException();}
    
  };
  
  public static void main(String[] args) {
    
    final ScriptEngine jsEngine = new ScriptEngineManager().getEngineByName("JavaScript");
    
    final int length = 10;
    
    CombinationIterator ci = new CombinationIterator(length);
    
    while (ci.hasNext()) {
      Arithmetic[] c = ci.next();
      StringBuilder equation = new StringBuilder(); 
      for (int i = 0; i < length-1; i++) {
        equation.append(i+1).append(c[i]);
      }
      equation.append(length);
      
      
      try {
        Double d = (Double) jsEngine.eval(equation.toString());
        if (d == 100) {
          System.out.println(equation);
        }
      } catch (ScriptException ex) {
        ex.printStackTrace();
      }
      
    }

  }
  
}

