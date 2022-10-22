package hive.udf;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF.DeferredJavaObject;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF.DeferredObject;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.JavaLongObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GenericUDFAtoiTest {

    protected void runAndVerify(Text value, LongWritable exp) throws HiveException {
        GenericUDFAtoi udf = new GenericUDFAtoi();
        ObjectInspector input = PrimitiveObjectInspectorFactory.javaStringObjectInspector;
        udf.initialize(new ObjectInspector[] {input});

        LongWritable result = (LongWritable) udf.evaluate(new DeferredObject[] { new DeferredJavaObject(value) });
        if (exp == null) {
            assertNull(result);
        } else {
            assertNotNull(result);
            assertEquals(exp, result, "Atoi() test");
        }
    }

    @Test
    @DisplayName("throws UDFArgumentLengthException when argument length is incorrect")
    public void testArgumentLength() {
        GenericUDFAtoi udf = new GenericUDFAtoi();
        Exception exception = assertThrows(UDFArgumentLengthException.class, () -> udf.initialize(new ObjectInspector[]{}));

        assertEquals("Atoi requires 1 argument, got 0", exception.getMessage());
    }

    @Test
    @DisplayName("throws UDFArgumentTypeException when argument type is incorrect")
    public void testArgumentType() {
        GenericUDFAtoi udf = new GenericUDFAtoi();
        JavaLongObjectInspector strOI = PrimitiveObjectInspectorFactory.javaLongObjectInspector;
        Exception exception = assertThrows(UDFArgumentTypeException.class, () -> udf.initialize(new ObjectInspector[]{strOI}));

        assertEquals("Atoi only takes STRING types, got bigint", exception.getMessage());
    }

    @Test
    @DisplayName("test")
    public void testAtoi() throws Exception {
        runAndVerify(null, null);
        runAndVerify(new Text(""), null);
        runAndVerify(new Text("3.14"), null);
        runAndVerify(new Text("2hello world!"), null);
        runAndVerify(new Text("1_2_3_4_5"), null);
        runAndVerify(new Text("_12345"), null);
        runAndVerify(new Text("0"), new LongWritable(0L));
        runAndVerify(new Text("1"), new LongWritable(1L));
        runAndVerify(new Text("-2"), new LongWritable(-2L));
    }
}
