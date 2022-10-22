package go.types;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Pointer;
import com.sun.jna.Structure;

// typedef struct { void *data; GoInt len; GoInt cap; } GoSlice;
public class GoSlice extends Structure implements Structure.ByValue {
    public Pointer data;
    public long len;
    public long cap;

    protected List<String> getFieldOrder() {
        return Arrays.asList("data", "len", "cap");
    }

    public GoSlice(Pointer data, long len) {
        this.data = data;
        this.len = len;
        this.cap = len;
    }

    public GoSlice(Pointer data, long len, long cap) {
        this.data = data;
        this.len = len;
        this.cap = cap;
    }
}
