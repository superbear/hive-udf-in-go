package go;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import com.sun.jna.Native;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import go.types.GoString;

public class Client {
    private static final Log LOG = LogFactory.getLog(Client.class.getName());
    private static String libName = "awesome.so";
    private static Awesome handle = null;

    static {
        LOG.info("load so");
        String path = extractResource(libName, libName);
        handle = (Awesome) Native.loadLibrary(path, Awesome.class);
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

    // extractResources extract resource from classpath then write it to disk
    private static String extractResource(String name, String path) {
        File temp = new File(name);
        if (temp.exists()) {
            return temp.getAbsolutePath();
        }

        FileOutputStream out = null;
        InputStream in = Client.class.getClassLoader().getResourceAsStream(path);
        try {
            out = new FileOutputStream(temp);
            IOUtils.copy(in, out);
        } catch (Exception e) {
            throw new RuntimeException("extract resource '" + name + "' from " + path + " failed", e);
        } finally {
            IOUtils.closeQuietly(in);
            IOUtils.closeQuietly(out);
        }

        return temp.getAbsolutePath();
    }
}
