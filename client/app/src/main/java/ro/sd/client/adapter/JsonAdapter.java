package ro.sd.client.adapter;

import fr.arnaudguyon.xmltojsonlib.XmlToJson;

public class JsonAdapter implements WebAdapter {

    @Override
    public String request(Object data) {

        String json = null;

        try {
            //JSONObject xmlJSONObj = XML.toJSONObject(data.toString());
            //json = xmlJSONObj.toString();


            String xml = data.toString();
            XmlToJson xmlToJson = new XmlToJson.Builder(xml).build();

            // convert to a JSONObject
            //JSONObject jsonObject = xmlToJson.toJson();

            // OR convert to a Json String
            json = xmlToJson.toString();


        } catch (Exception e){}

        return json;
    }
}
