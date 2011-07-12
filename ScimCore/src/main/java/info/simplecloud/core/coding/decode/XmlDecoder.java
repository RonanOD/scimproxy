package info.simplecloud.core.coding.decode;

import info.simplecloud.core.ScimUser;
import info.simplecloud.core.execeptions.InvalidUser;

import java.util.List;
import java.util.Map;

public class XmlDecoder implements IUserDecoder {
    private static String[] names = { "xml", "XML" };

    @Override
    public void addMe(Map<String, IUserDecoder> decoders) {
        for (String name : names) {
            decoders.put(name, this);
        }
    }

    @Override
    public void decode(String user, ScimUser data) throws InvalidUser {
        // TODO Auto-generated method stub

    }

	@Override
	public void decode(String userList, List<ScimUser> users) throws InvalidUser {
		// TODO Auto-generated method stub
		
	}

}
