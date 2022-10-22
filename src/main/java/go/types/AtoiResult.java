package go.types;

import java.util.Arrays;
import java.util.List;

import com.sun.jna.Structure;

// struct Atoi_return {
// 	GoInt r0; /* result */
// 	char* r1; /* error */
// };
public class AtoiResult extends Structure implements Structure.ByValue {
    public Long r0;
    public String r1;

    protected List<String> getFieldOrder() {
        return Arrays.asList("r0", "r1");
    }

    public Long getResult() {
        if (r1 == null) {
            return r0;
        }
        return null;
    }
}
