package go;

import com.sun.jna.Library;

import go.types.AtoiResult;
import go.types.GoString;

public interface Awesome extends Library {
    public int Add(int a, int b);

    public String ToUpper(GoString s);

    public AtoiResult Atoi(GoString s);
}
