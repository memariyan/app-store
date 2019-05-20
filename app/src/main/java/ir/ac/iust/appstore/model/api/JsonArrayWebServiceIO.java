package ir.ac.iust.appstore.model.api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class JsonArrayWebServiceIO implements WebServiceIO<JSONArray>
{
    private JSONArray jsonData;

    public JsonArrayWebServiceIO(JSONArray jsonData)
    {
        this.jsonData = jsonData;
    }

    public JsonArrayWebServiceIO()
    {
    }

    @Override
    public MimeType getMimeType()
    {
        return MimeType.JSON;
    }

    @Override
    public void setData(JSONArray data)
    {
        this.jsonData=data;
    }

    @Override
    public JSONArray getData()
    {
        return jsonData;
    }

    @Override
    public void writeTo(OutputStream outputStream) throws Exception
    {
        OutputStreamWriter osw = new OutputStreamWriter(outputStream, "UTF-8");
        osw.write(jsonData.toString());
        osw.flush();
        osw.close();
    }

    @Override
    public void readFrom(InputStream inputStream) throws Exception
    {
        jsonData=new JSONArray(getStringFromInputStream(inputStream));
    }

    public String getStringFromInputStream(InputStream is) throws Exception
    {

        String line;
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferReader = new BufferedReader(new InputStreamReader(is,"UTF-8"));

        while ((line = bufferReader.readLine()) != null)
            stringBuilder.append(line);

        if (bufferReader != null)
            bufferReader.close();

        return stringBuilder.toString();
    }
}
