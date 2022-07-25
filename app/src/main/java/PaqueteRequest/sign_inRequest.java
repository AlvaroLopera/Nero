package PaqueteRequest;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class sign_inRequest extends StringRequest {
    private static final String ruta = "http://pruebasnero.000webhostapp.com/registro.php";
    private Map<String, String> val;

    public sign_inRequest(String usuario, String contra, String email, String nombre, Response.Listener<String> respuesta) {
        super(Request.Method.POST, ruta, respuesta, null);
        val = new HashMap<>();
        val.put("user", usuario);
        val.put("pass", contra);
        val.put("correo", email);
        val.put("name", nombre);
    }

    @Override
    protected Map<String, String> getParams(){
        return val;
    }
}
