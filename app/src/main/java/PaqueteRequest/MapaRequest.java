package PaqueteRequest;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MapaRequest extends StringRequest {
    private static final String ruta = "https://pruebasnero.000webhostapp.com/consultasfuente.php";
    private Map<String, String> val;

    public MapaRequest(Response.Listener<String> listener){
        super(Request.Method.POST, ruta, listener, null);
        val = new HashMap<>();
        //val.put("user", usuario);
        //val.put("pass", contra);
    }

    @Override
    protected Map<String, String> getParams(){
        return val;
    }
}