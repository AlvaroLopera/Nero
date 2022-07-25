package PaqueteRequest;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AnyadirComentarioRequest extends StringRequest {
    private static final String ruta = "https://pruebasnero.000webhostapp.com/anyadircomentario.php";
    private Map<String, String> val;

    public AnyadirComentarioRequest(String nick, String pass, String texto, String idfuente, Response.Listener<String> listener){
        super(Request.Method.POST, ruta, listener, null);
        val = new HashMap<>();
        val.put("user", nick);
        val.put("pass", pass);
        val.put("text", texto);
        val.put("idfuente", idfuente);
    }

    @Override
    protected Map<String, String> getParams(){
        return val;
    }
}
