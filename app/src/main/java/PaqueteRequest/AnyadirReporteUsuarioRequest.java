package PaqueteRequest;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AnyadirReporteUsuarioRequest extends StringRequest {
    private static final String ruta = "https://pruebasnero.000webhostapp.com/anyadirreporteusuario.php";
    private Map<String, String> val;

    public AnyadirReporteUsuarioRequest(String thisUserNick, String thisUserPass, String reportado, String texto, Response.Listener<String> listener){
        super(Request.Method.POST, ruta, listener, null);
        val = new HashMap<>();
        val.put("thisuser", thisUserNick);
        val.put("thispass", thisUserPass);
        val.put("descripcion", texto);
        val.put("reportado", reportado);
    }

    @Override
    protected Map<String, String> getParams(){
        return val;
    }
}
