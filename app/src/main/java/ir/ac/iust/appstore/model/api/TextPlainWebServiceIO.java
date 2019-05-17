package ir.ac.iust.appstore.model.api;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by IT-GIS on 2/2/2017.
 */
public class TextPlainWebServiceIO implements WebServiceIO<String>
{
    public TextPlainWebServiceIO()
    {
    }

    public TextPlainWebServiceIO(String data)
    {
        this.data = data;
    }

    String data = "";

    @Override
    public MimeType getMimeType()
    {
        return MimeType.TEXT_PLAIN;
    }

    @Override
    public void setData(String data)
    {
        this.data=data;
    }

    @Override
    public String getData()
    {
        return data;
    }

    @Override
    public void writeTo(OutputStream outputStream) throws Exception
    {
        DataOutputStream wr = new DataOutputStream(outputStream);
        wr.write(data.getBytes("UTF-8"));
    }

    @Override
    public void readFrom(InputStream inputStream) throws Exception
    {
        try(java.util.Scanner s = new java.util.Scanner(inputStream))
        {
            data= s.useDelimiter("\\A").hasNext() ? s.next() : "";
        }
    }
}
