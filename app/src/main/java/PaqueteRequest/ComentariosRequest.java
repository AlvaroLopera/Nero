package PaqueteRequest;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ComentariosRequest extends StringRequest {
    private static final String ruta = "https://pruebasnero.000webhostapp.com/consultascomentarios.php";
    private Map<String, String> val;

    public ComentariosRequest(Response.Listener<String> listener, String fuente_id){
        super(Request.Method.POST, ruta, listener, null);
        val = new HashMap<>();
        val.put("fuenteid", fuente_id);
    }

    @Override
    protected Map<String, String> getParams(){
        return val;
    }
}
