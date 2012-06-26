
package info.simplecloud.scimproxy.test;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/{host}/{id}")
public class ScimTestSuite
{
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public String getList(@PathParam("host") String host)
    {
        System.out.println("Called GET with only host specified.");
        return "Super result";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getStatus(@PathParam("host") String host,
                             @PathParam("id") String id)
    {
        System.out.println("Called GET with host and id specified.");
        return "DONE";
    }
}
