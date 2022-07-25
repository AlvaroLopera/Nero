package PaqueteRequest;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AnyadirReporteComentarioRequest extends StringRequest {
    private static final String ruta = "https://pruebasnero.000webhostapp.com/anyadirreportecomentario.php";
    private Map<String, String> val;

    public AnyadirReporteComentarioRequest(String nick, String pass, String idcomentario, String descripcion, Response.Listener<String> listener){
        super(Request.Method.POST, ruta, listener, null);
        val = new HashMap<>();
        val.put("user", nick);
        val.put("pass", pass);
        val.put("idcomentario", idcomentario);
        val.put("descripcion", descripcion);
    }

    @Override
    protected Map<String, String> getParams(){
        return val;
    }
}
