package hive.udf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF.DeferredJavaObject;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF.DeferredObject;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.JavaLongObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.JavaTimestampObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.io.Text;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GenericUDFUpperTest {

    protected void runAndVerify(Text value, Text exp) throws HiveException {
        GenericUDFUpper udf = new GenericUDFUpper();
        ObjectInspector input = PrimitiveObjectInspectorFactory.javaStringObjectInspector;
        udf.initialize(new ObjectInspector[] {input});

        Text result = (Text) udf.evaluate(new DeferredObject[] { new DeferredJavaObject(value) });
        if (exp == null) {
            assertNull(result);
        } else {
            assertNotNull(result);
            assertEquals(exp, result, "UPPER() test");
        }
    }

    @Test
    @DisplayName("throws UDFArgumentLengthException when argument length is incorrect")
    public void testArgumentLength() {
        GenericUDFUpper udf = new GenericUDFUpper();
        Exception exception = assertThrows(UDFArgumentException.class, () -> udf.initialize(new ObjectInspector[]{}));
        assertEquals("UPPER requires 1 argument, got 0", exception.getMessage());

        GenericUDFUpper udf2 = new GenericUDFUpper();
        Exception exception2 = assertThrows(UDFArgumentException.class, () -> udf.initialize(new ObjectInspector[]{null, null}));
        assertEquals("UPPER requires 1 argument, got 2", exception2.getMessage());
    }

    @Test
    @DisplayName("throws UDFArgumentTypeException when argument type is incorrect")
    public void testArgumentType() {
        GenericUDFUpper udf = new GenericUDFUpper();
        JavaLongObjectInspector longOI = PrimitiveObjectInspectorFactory.javaLongObjectInspector;
        Exception exception = assertThrows(UDFArgumentException.class, () -> udf.initialize(new ObjectInspector[]{longOI}));
        assertEquals("UPPER only takes STRING types, got bigint", exception.getMessage());

        GenericUDFUpper udf2 = new GenericUDFUpper();
        JavaTimestampObjectInspector timestampOI = PrimitiveObjectInspectorFactory.javaTimestampObjectInspector;
        Exception exception2 = assertThrows(UDFArgumentException.class, () -> udf.initialize(new ObjectInspector[]{timestampOI}));
        assertEquals("UPPER only takes STRING types, got timestamp", exception2.getMessage());
    }

    @Test
    @DisplayName("test")
    public void testUpper() throws Exception {
        runAndVerify(null, null);
        runAndVerify(new Text(""), new Text(""));
        runAndVerify(new Text(" "), new Text(" "));
        runAndVerify(new Text("1"), new Text("1"));
        runAndVerify(new Text("hello world!"), new Text("HELLO WORLD!"));
        runAndVerify(new Text("HELLO world!"), new Text("HELLO WORLD!"));
        runAndVerify(new Text("HELLO WORLD!"), new Text("HELLO WORLD!"));
    }
}
