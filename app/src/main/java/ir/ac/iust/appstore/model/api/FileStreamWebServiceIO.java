package ir.ac.iust.appstore.model.api;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mohammad on 2016-10-29.
 */
public class FileStreamWebServiceIO implements WebServiceIO<File>
{
    //required parameters
    private File file;
    private String originalFileName;

    //helper parameters
    private String contentType;
    private String name;
    private String twoHyphens = "--";
    private String boundary = "*****";
    private String crlf = "\r\n";
    private HashMap<String, String> info;

    private static final String INFO_ITEM_SPLITTER = "@";
    private static final String INFO_KEY_VALUE_SPLITTER = "_";
    public static final String OWNER_ENTITY_ID_INFO_ARG = "OwnerEntityID";

    public enum FileType
    {
        IMAGE;
    }

    public FileStreamWebServiceIO(File file, FileType fileType)
    {
        this.file = file;

        if (fileType.equals(FileType.IMAGE))
        {
            name = "image";
            contentType = "image/jpeg";
        }
    }

    public void addInfo(String key, String value)
    {
        if (info == null)
            info = new HashMap<>();

        info.put(key, value);
    }

    private String buildInfoString()
    {
        String infoString = "";
        if (info != null && info.size() > 0)
        {
            for (Map.Entry<String, String> pair : info.entrySet())
            {
                if (!infoString.equals(""))
                    infoString += INFO_ITEM_SPLITTER;

                infoString += pair.getKey() + INFO_KEY_VALUE_SPLITTER + pair.getValue();
            }
        }
        return infoString;
    }

    @Override
    public MimeType getMimeType()
    {
        return MimeType.FILE;
    }

    @Override
    public void setData(File data)
    {
        this.file = data;
    }

    @Override
    public File getData()
    {
        return file;
    }

    @Override
    public void writeTo(OutputStream outputStream) throws Exception
    {
        String info = buildInfoString();
        originalFileName = (info == null) ? "null" : info;

        //set inputStream description to output
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeBytes(twoHyphens + boundary + crlf);
        dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"" + name + "\"; filename=\"" + originalFileName + "\"" + crlf);
        dataOutputStream.writeBytes("Content-Type: " + contentType + crlf);
        dataOutputStream.writeBytes("Content-Transfer-Encoding: binary" + crlf);
        dataOutputStream.writeBytes(crlf);

        //write file bytes to output
        FileInputStream inputStream = new FileInputStream(file);

        byte[] buffer = new byte[1024];
        int len;
        while ((len = inputStream.read(buffer)) != -1)
            dataOutputStream.write(buffer, 0, len);

        dataOutputStream.writeBytes(crlf);
        dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + crlf);
        dataOutputStream.flush();
        dataOutputStream.close();
    }

    @Override
    public void readFrom(InputStream inputStream) throws Exception
    {

    }
}
