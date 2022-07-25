package PaqueteRequest;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {

    private static final String ruta = "http://pruebasnero.000webhostapp.com/iniciosesion.php";
    private Map<String, String> val;

    public LoginRequest(String usuario, String contra, Response.Listener<String> listener){
        super(Request.Method.POST, ruta, listener, null);
        val = new HashMap<>();
        val.put("user", usuario);
        val.put("pass", contra);
    }

    @Override
    protected Map<String, String> getParams(){
        return val;
    }
}