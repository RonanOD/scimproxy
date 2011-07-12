package info.simplecloud.core.coding.decode;

import info.simplecloud.core.ScimUser;
import info.simplecloud.core.execeptions.InvalidUser;

import java.util.List;
import java.util.Map;

public class SamlDecoder implements IUserDecoder {

    @Override
    public void addMe(Map<String, IUserDecoder> decoders) {
        // TODO Auto-generated method stub

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
