package go.types;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Structure;

public class GoString extends Structure implements Structure.ByValue {
    public String p;
    public long n;

    protected List<String> getFieldOrder() {
        return Arrays.asList("p", "n");
    }

    public GoString(String content) {
        this.p = content;
        this.n = p.length();
    }
}
