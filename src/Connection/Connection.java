package Connection;
      
/**
 * @author DarKPhuRioN
 */
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public final class Connection {

    private static java.sql.Connection con;//instancia
    private static String drive = "jdbc:mysql";
    private String server = "localhost";
    private String port = "3306";
    private String db = "Jardin";
    private String user = "root";
    private String password = "phurion123";

    public Connection() {
        openConnection();
    }

    public Connection(String server, String port, String db, String user, String password) {
        this.server = server;
        this.port = port;
        this.db = db;
        this.user = user;
        this.password = password;
        openConnection();
    }

    private void openConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(drive + "://" + server + ":" + port + "/" + db, user, password);
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.toString());
        }
    }

    public java.sql.Connection getcon() {
        return this.con;
    }

    public void closeConnection() throws SQLException {
        con.close();
    }

    public boolean Operation(String req) throws SQLException {
        Statement st = getcon().createStatement();
        try {
            st.execute(req);
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }

    public ArrayList Query(String req, String par[]) throws SQLException {
        Statement st = getcon().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        String arraytemp[];
        ArrayList<String[]> res = new ArrayList<>();
        ResultSet consulta = st.executeQuery(req);
        while (consulta.next()) {
            arraytemp = new String[par.length];
            for (int i = 0; i < arraytemp.length; i++) {
                arraytemp[i] = consulta.getString(par[i]);
            }
            res.add(arraytemp);
        }
        return res;
    }
}
/*
    public static void main(String[] args) throws SQLException {
        Connection tt = new Connection();
        String array[] = {"id", "name"};
        List cccc = tt.Query("select * from app", array);
        System.out.println(cccc.size());
        String x[] = (String[]) cccc.get(1);
        System.out.println(x[0]);
    }
*/