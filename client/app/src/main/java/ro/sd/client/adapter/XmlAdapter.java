package ro.sd.client.adapter;

import org.json.JSONObject;

public class XmlAdapter implements WebAdapter {

    @Override
    public String request(Object data) {

        String xml = null;

        try {

            JSONObject json = new JSONObject(data.toString());
            //xml = XML.toString(json);

        } catch (Exception e){}

        return xml;
    }
}
