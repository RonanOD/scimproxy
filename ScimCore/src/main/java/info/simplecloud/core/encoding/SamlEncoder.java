package info.simplecloud.core.encoding;

import java.util.List;
import java.util.Map;

import info.simplecloud.core.ScimUser;
import info.simplecloud.core.decoding.IUserDecoder;
import info.simplecloud.core.execeptions.EncodingFailed;
import info.simplecloud.core.execeptions.InvalidUser;

public class SamlEncoder implements IUserEncoder {

	@Override
	public String encode(ScimUser scimUser) throws EncodingFailed {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String encode(List<ScimUser> scimUser) throws EncodingFailed {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addMe(Map<String, IUserEncoder> encoders) {
		// TODO Auto-generated method stub
		
	}


}
