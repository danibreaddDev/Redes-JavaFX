package oovv;

public class DatosConexion {
    private final String DRIVER = "org.postgresql.Driver";
    private final String URL = "jdbc:postgresql://localhost:5432/PruebaDI";
    private final String USERNAME = "postgres";
    private final String PASSWORD = "postgres";
    private final String BD = "PruebaDI";

    public String getDRIVER() {
        return DRIVER;
    }

    public String getURL() {
        return URL;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public String getBD() {
        return BD;
    }
}
