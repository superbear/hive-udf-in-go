package go.types;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

// typedef struct { void *t; void *v; } GoInterface;
public class GoInterface extends Structure implements Structure.ByValue {
    public Pointer t;
    public Pointer v;

    protected List<String> getFieldOrder() {
        return Arrays.asList("t", "v");
    }
}
