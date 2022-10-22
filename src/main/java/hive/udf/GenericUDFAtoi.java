package hive.udf;

import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.StringObjectInspector;
import org.apache.hadoop.io.LongWritable;

import go.Client;

// DESC FUNCTION xxx
@Description(name = "atoi", value = "_FUNC_(str) - string to int")
public class GenericUDFAtoi extends GenericUDF {
    private final LongWritable result = new LongWritable();
    private StringObjectInspector input;

    @Override
    public ObjectInspector initialize(ObjectInspector[] arg0) throws UDFArgumentException {
        if (arg0.length != 1) {
            throw new UDFArgumentLengthException("Atoi requires 1 argument, got " + arg0.length);
        }

        ObjectInspector input = arg0[0];
        if (!(input instanceof StringObjectInspector)) {
            throw new UDFArgumentTypeException(0, "Atoi only takes STRING types, got " + input.getTypeName());
        }
        this.input = (StringObjectInspector) input;

        return PrimitiveObjectInspectorFactory.writableLongObjectInspector;
    }

    @Override
    public Object evaluate(DeferredObject[] arg0) throws HiveException {
        Object input0 = arg0[0].get();
        if (input0 == null) {
            return null;
        }

        String str = this.input.getPrimitiveJavaObject(input0).toString();
        Long i = Client.Atoi(str);
        if (i == null) {
            return null;
        }
        result.set(i);
        return result;
    }

    @Override
    public String getDisplayString(String[] children) {
        assert (children.length == 1);
        return getStandardDisplayString("atoi", children);
    }
}
