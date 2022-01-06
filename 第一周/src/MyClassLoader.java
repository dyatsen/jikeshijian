import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyClassLoader extends ClassLoader {

    public static void main(String[] args) {
        try {
            Class<?> aClass = new MyClassLoader().findClass("Hello");
            Method hello = aClass.getDeclaredMethod("hello");
            hello.invoke(aClass.newInstance());
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        File file = new File("F:\\Hello.xlass");
        InputStream in;
        byte[] result = null;
        int available = 0;
        try {
            in = new FileInputStream(file);
            available = in.available();
            result = new byte[available];
            for (int i = 0; i < available; i++) {
                result[i] = (byte) (255 - in.read());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defineClass("Hello", result, 0, available);
    }
}
