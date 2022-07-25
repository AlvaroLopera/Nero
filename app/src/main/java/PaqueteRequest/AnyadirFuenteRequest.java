package PaqueteRequest;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AnyadirFuenteRequest extends StringRequest {

    private static final String ruta = "http://pruebasnero.000webhostapp.com/anyadirFuente.php";
    private Map<String, String> val;

    public AnyadirFuenteRequest(String nomUsuario, String latitud, String longitud, String dispo, String nomFuente, Response.Listener<String> listener){
        super(Request.Method.POST, ruta, listener, null);

        val = new HashMap<>();
        val.put("nomUsuario", nomUsuario);
        val.put("latitud", latitud);
        val.put("longitud", longitud);
        val.put("dispo", dispo);
        val.put("nomfuente", nomFuente);
    }

    @Override
    protected Map<String, String> getParams(){
        return val;
    }
}
