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
import org.apache.hadoop.io.Text;

import go.Client;

// reference https://github.com/apache/hive/blob/master/ql/src/java/org/apache/hadoop/hive/ql/udf/generic/GenericUDFUpper.java
// DESC FUNCTION xxx
@Description(name = "str_to_upper", value = "_FUNC_(str) - string to upper")
public class GenericUDFUpper extends GenericUDF {
    private Text result = new Text();
    private StringObjectInspector input;

    @Override
    public ObjectInspector initialize(ObjectInspector[] arg0) throws UDFArgumentException {
        if (arg0.length != 1) {
            throw new UDFArgumentLengthException("UPPER requires 1 argument, got " + arg0.length);
        }

        ObjectInspector input = arg0[0];
        if (!(input instanceof StringObjectInspector)) {
            throw new UDFArgumentTypeException(0, "UPPER only takes STRING types, got " + input.getTypeName());
        }
        this.input = (StringObjectInspector) input;

        return PrimitiveObjectInspectorFactory.writableStringObjectInspector;
    }

    @Override
    public Object evaluate(DeferredObject[] arg0) throws HiveException {
        Object input0 = arg0[0].get();
        if (input0 == null) {
            return null;
        }

        String str = this.input.getPrimitiveJavaObject(input0).toString();
        result.set(Client.ToUpper(str));
        return result;
    }

    @Override
    public String getDisplayString(String[] children) {
        assert (children.length == 1);
        return getStandardDisplayString("upper", children);
    }
}
