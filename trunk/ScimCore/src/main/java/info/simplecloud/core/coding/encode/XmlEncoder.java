package info.simplecloud.core.coding.encode;

import info.simplecloud.core.Resource;
import info.simplecloud.core.User;
import info.simplecloud.core.exceptions.EncodingFailed;
import info.simplecloud.core.handlers.ComplexHandler;

import java.util.List;

import org.apache.xmlbeans.XmlBeans;

public class XmlEncoder implements IUserEncoder {

    @Override
    public String encode(Resource scimUser) {
        //new ComplexHandler().encodeXml(me, null, null);
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String encode(List<User> scimUser) throws EncodingFailed {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String encode(Resource scimUser, List<String> attributesList) throws EncodingFailed {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String encode(List<User> arrayList, List<String> attributesList) throws EncodingFailed {
        // TODO Auto-generated method stub
        return null;
    }

}
