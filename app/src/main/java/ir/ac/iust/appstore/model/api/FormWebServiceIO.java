package ir.ac.iust.appstore.model.api;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FormWebServiceIO implements WebServiceIO<Map<String,String>>
{
    private Map<String,String> data;
    private String rawData;

    public FormWebServiceIO() {}

    public FormWebServiceIO(Map<String,String> data)
    {
        this.data=data;
    }

    @Override
    public MimeType getMimeType()
    {
        return MimeType.FORM;
    }

    @Override
    public void setData(Map<String, String> data)
    {
        this.data=data;
    }

    @Override
    public Map<String, String> getData()
    {
        return data;
    }

    @Override
    public void writeTo(OutputStream outputStream) throws Exception
    {
        DataOutputStream wr = new DataOutputStream(outputStream);
        Iterator<Map.Entry<String,String>> itr = data.entrySet().iterator();
        while (itr.hasNext())
        {
            Map.Entry<String,String> entry = itr.next();
            rawData=(rawData == null) ? (entry.getKey()+"="+entry.getValue()) : rawData+"&"+(entry.getKey()+"="+entry.getValue());
        }
        rawData=rawData.replace("+", "%2B");
        wr.write(rawData.getBytes("UTF-8"));
    }

    @Override
    public void readFrom(InputStream inputStream) throws Exception
    {
        data = new HashMap<>();
        java.util.Scanner scanner = new java.util.Scanner(inputStream);
        try
        {
            rawData= scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            scanner.close();
        }

        String[] items = rawData.split("&");
        for(String item : items)
        {
            String[] content = item.split("=");
            data.put(content[0],content[1]);
        }
    }

    /*public void addFormParam(String key,String value)
    {
        String currentData = getData();
        String item = key+"="+value;
        setData((currentData == null) ? item : currentData+"&"+item);
    }

    public String setData()
    {
        return super.getData();
    }*/
}
