import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
public class Genericide{

 /** // The challenge is to duplicate all the values from oldRecord to newRecord */
 public static void copy(Record oldRecord, Record newRecord) {

 for(Field<? extends Comparable<?>> field : oldRecord.getFields()){
// newRecord.setValue(field, oldRecord.getValue(field));
 copyValue(newRecord, field, oldRecord);
 }
 }

 private static <T extends Comparable<T>> void copyValue(Record s, Field<T> field, Record old) {
 T t = old.getValue(field);
 s.setValue(field, t);
 }

}

interface Record
{
 <T extends Comparable<T>> T getValue(Field<T> field);
<T extends Comparable<T>> T setValue(Field<T> field, T value);
Collection<Field<?>> getFields();
Collection<Object> getValues();
}
final class Field<T extends Comparable<T>> {

 String name;

 Class<T> clazz;

 Field(String name, Class<T> clazz) {
 this.name = name;
 this.clazz = clazz;
 }
}

