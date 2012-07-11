package info.simplecloud.scimproxy.compliance.test;

import info.simplecloud.scimproxy.compliance.CSP;
import info.simplecloud.scimproxy.compliance.ServiceProviderConfig;
import info.simplecloud.scimproxy.compliance.enteties.TestResult;

import java.util.ArrayList;
import java.util.List;

public class PatchTest extends Test{

    public PatchTest(CSP csp, ResourceCache<CachedUser> cache, ResourceCache<CachedGroup> groupCache) {
        super(csp, cache, groupCache);
    }

    @Override
    public List<TestResult> run() {
        List<TestResult> results = new ArrayList<TestResult>();
        ServiceProviderConfig spc = csp.getSpc();
        if(spc.hasPatch()){
            return results;
        }
        
        
        return results;
    }
}

/*
2012-05-03 15:08:52 3 INFO - - "AUTHORIZATION ACCESS_GRANTED: Request URI: mvpnas://mvpnas:0/wa/client.ewa [ Resource URI:mvpnas://mvpnas:0/ (cache 2250 sec. recursive) ] "
2012-05-03 15:08:58 0 192.176.242.136 "Access Client" 0adc707064451337 - - - login.moderat.se https - - GET /wa/tunnel - "Access Client/4,9,11,5088" - - 130 HTTP/1.0 - - 171 101
2012-05-03 15:08:58 WARNING 1032092 "Client disconnected unexpectedly, winerr=0" - - - -
2012-05-03 15:08:52 3 INFO - - "AUTHORIZATION ACCESS_DENIED: Request URI: mvpnas://mvpnas:0/wa/webclient/hhioqg8w4jk0 [ Resource URI:wa/webclient/VPN MODvStrExtPub ] #SG-VPN-MODvStrExtPub-Users evaluated false "

2012-05-03 15:09:04 3 INFO - - "AUTHORIZATION ACCESS_GRANTED: Request URI: mvpnas://mvpnas:0/wa/client.ewa [ Resource URI:mvpnas://mvpnas:0/ (cache 2250 sec. recursive) ] "
2012-05-03 15:09:04 3 INFO - - "AUTHORIZATION ACCESS_DENIED: Request URI: mvpnas://mvpnas:0/wa/webclient/hhioqg8w4jk0 [ Resource URI:wa/webclient/VPN MODvStrExtPub ] #SG-VPN-MODvStrExtPub-Users evaluated false "
2012-05-03 15:09:09 WARNING 1032092 "Client disconnected unexpectedly, winerr=0" - - - -
2012-05-03 15:09:09 0 192.176.242.136 "Access Client" fde4500d2142c301 - - - login.moderat.se https - - GET /wa/tunnel - "Access Client/4,9,11,5088" - - 130 HTTP/1.0 - - 171 101

2012-05-03 15:09:30 3 INFO - - "AUTHORIZATION ACCESS_GRANTED: Request URI: mvpnas://mvpnas:0/wa/client.ewa [ Resource URI:mvpnas://mvpnas:0/ (cache 2250 sec. recursive) ] "
2012-05-03 15:09:30 3 INFO - - "AUTHORIZATION ACCESS_DENIED: Request URI: mvpnas://mvpnas:0/wa/webclient/hhioqg8w4jk0 [ Resource URI:wa/webclient/VPN MODvStrExtPub ] #SG-VPN-MODvStrExtPub-Users evaluated false "
2012-05-03 15:09:35 0 192.176.242.136 "Access Client" 6795970757baa16d - - - login.moderat.se https - - GET /wa/tunnel - "Access Client/4,9,11,5088" - - 130 HTTP/1.0 - - 171 101
2012-05-03 15:09:35 WARNING 1032092 "Client disconnected unexpectedly, winerr=0" - - - -
2012-05-03 15:09:49 3 INFO - - "AUTHORIZATION ACCESS_GRANTED: Request URI: mvpnas://mvpnas:0/wa/client.ewa [ Resource URI:mvpnas://mvpnas:0/ (cache 2250 sec. recursive) ] "

2012-05-03 15:09:55 WARNING 1032092 "Client disconnected unexpectedly, winerr=0" - - - -
2012-05-03 15:09:55 0 192.176.242.136 "Access Client" f8f55b737a20d17f - - - login.moderat.se https - - GET /wa/tunnel - "Access Client/4,9,11,5088" - - 130 HTTP/1.0 - - 171 101
2012-05-03 15:09:58 3 INFO - - "AUTHORIZATION ACCESS_GRANTED: Request URI: mvpnas://mvpnas:0/wa/client.ewa [ Resource URI:mvpnas://mvpnas:0/ (cache 2250 sec. recursive) ] "
2012-05-03 15:10:03 0 192.176.242.136 "Access Client" 8578cd04e86ab8db - - - login.moderat.se https - - GET /wa/tunnel - "Access Client/4,9,11,5088" - - 130 HTTP/1.0 - - 171 101
2012-05-03 15:11:30 3 INFO - - "AUTHORIZATION ACCESS_DENIED: Request URI: mvpnas://mvpnas:0/wa/webclient/hhioqg8w4jk0 [ Resource URI:wa/webclient/VPN MODvStrExtPub ] #SG-VPN-MODvStrExtPub-Users evaluated false "
2012-05-03 15:11:30 3 INFO - - "AUTHORIZATION ACCESS_GRANTED: Request URI: mvpnas://mvpnas:0/wa/client.ewa [ Resource URI:mvpnas://mvpnas:0/ (cache 2250 sec. recursive) ] "
2012-05-03 15:11:32 WARNING 1032092 "Client disconnected unexpectedly, winerr=0" - - - -
2012-05-03 15:11:35 WARNING 1032092 "Client disconnected unexpectedly, winerr=0" - - - -
2012-05-03 15:11:35 3 INFO - - "AUTHORIZATION ACCESS_DENIED: Request URI: mvpnas://mvpnas:0/wa/webclient/hhioqg8w4jk0 [ Resource URI:wa/webclient/VPN MODvStrExtPub ] #SG-VPN-MODvStrExtPub-Users evaluated false "
2012-05-03 15:11:35 0 192.176.242.136 "Access Client" f2bf5df30514dc07 - - - login.moderat.se https - - GET /wa/tunnel - "Access Client/4,9,11,5088" - - 130 HTTP/1.0 - - 171 101

*/


