import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ReadData extends DriverSetUp {

    private static ReadData instance = null;
    private final static Map<String, String> configValues = new HashMap<>();

    public static ReadData getInstance() {
        if (instance == null) {
            instance = new ReadData();
            loadData();
        }
        return instance;
    }

    private static void loadData() {
        //Instantiating the properties file
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("src/main/resources/Elements.properties"));

            for (Object key : props.keySet()) {
                configValues.put(key.toString(), props.getProperty(key.toString()));
            }

        } catch (Exception ie) {
            log.info(ie.getMessage());
        }
    }

    public void setData(String property, String data) {
        try {
            configValues.putIfAbsent(property, data);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public String getData(String property) {
        try {
            return configValues.get(property);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    public void replaceData(String property, String data) {
        try {
            configValues.replace(property, data);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}
