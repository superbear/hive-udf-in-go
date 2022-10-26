package go;

import java.io.File;

import com.sun.jna.Native;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import go.types.GoString;

public class Client {
    private static final Log LOG = LogFactory.getLog(Client.class.getName());
    private static Awesome handle = null;

    static {
        LOG.info("load so");

        try {
            File file = Native.extractFromResourcePath("/awesome.so", Client.class.getClassLoader());
            Awesome lib = Native.load(file.getAbsolutePath(), Awesome.class);
            handle = (Awesome)Native.synchronizedLibrary(lib);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static int Add(int a, int b) {
        return handle.Add(a, b);
    }

    public static String ToUpper(String s) {
        return handle.ToUpper(new GoString(s));
    }

    public static Long Atoi(String s) {
        return handle.Atoi(new GoString(s)).getResult();
    }
}
