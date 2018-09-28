package com.picolab.application.Controller;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.picolab.application.DTO.CanvasImageDTO;
import com.picolab.domain.CanvasImage;
import com.picolab.utils.JSonNullException;

import org.json.JSONException;
import org.json.JSONObject;

import static com.picolab.view.showImage.canvasImage;

public class RequestsController {

    private String url;

    public RequestsController(){
        this.url="https://colaborativepicture.herokuapp.com/canvas/user";
    }

    public RequestsController(String url){
        this.url = url;
    }

    private CanvasImageDTO obtenerDatosGet(){
        final int[] id = new int[1];
        final String[] url = new String[1];

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, this.url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                JSONObject jsonObject =response;

                try {
                    id[0] = (int) jsonObject.get("id");
                    url[0] = jsonObject.getString("url");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        return new CanvasImageDTO(id[0], url[0]);
    }
}
