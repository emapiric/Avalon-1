package domain.decoder;

import com.google.gson.Gson;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class PlayerDecoder implements Decoder.Text<domain.Player>{

    private static Gson gson = new Gson();

    @Override
    public domain.Player decode(String s) throws DecodeException {
        return gson.fromJson(s, domain.Player.class);
    }

    @Override
    public boolean willDecode(String s) {
        return (s != null);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
        // Custom initialization logic
    }

    @Override
    public void destroy() {
        // Close resources
    }
}
