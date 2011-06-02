<html>
<body>
<h2>scimproxy api calls</h2>

<h3>User</h3>
<pre>
GET:
curl -i -H "Accept: application/json" http://localhost:8080/User/3f62ce30-dcd6-4dd7-abfe-2352a76f9978

POST:
curl -i -H "Accept: application/json" -X POST -d "{%22schemas%22: [%22urn:scim:schemas:core:1.0%22],%22userName%22: %22bjensen@example.com%22,%22name%22: {%22familyName%22: %22Jensen%22,%22givenName%22: %22Barbara%22},%22displayName%22: %22Babs Jensen%22, %22emails%22: [{%22value%22: %22bjensen@example.com%22,%22type%22: %22work%22,%22primary%22: true}]}" http://localhost:8080/User

PUT:
curl -i -H "Accept: application/json" -X PUT -d "phone=1-800-999-9999" http://localhost:8080/User

DELETE:
curl -i -H "Accept: application/json" -X DELETE http://localhost:8080/User

PUT OVER POST:
curl -i -H "Accept: application/json" -H "X-HTTP-Method-Override: PUT" -X POST -d "phone=1-800-999-9999" http://localhost:8080/User

DELETE OVER POST:
curl -i -H "Accept: application/json" -H "X-HTTP-Method-Override: DELETE" -X POST  http://localhost:8080/User
</pre>

<h3>Users</h3>
<pre>
GET:
curl -i -H "Accept: application/json" http://localhost:8080/Users  
</pre>

<h3>Group</h3>
<pre>
GET:
curl -i -H "Accept: application/json" http://localhost:8080/Group

POST:
curl -i -H "Accept: application/json" -X POST -d "firstName=james" http://localhost:8080/Group

PUT:
curl -i -H "Accept: application/json" -X PUT -d "phone=1-800-999-9999" http://localhost:8080/Group

DELETE:
curl -i -H "Accept: application/json" -X DELETE http://localhost:8080/Group

PUT OVER POST:
curl -i -H "Accept: application/json" -H "X-HTTP-Method-Override: PUT" -X POST -d "phone=1-800-999-9999" http://localhost:8080/Group

DELETE OVER POST:
curl -i -H "Accept: application/json" -H "X-HTTP-Method-Override: DELETE" -X GET  http://localhost:8080/Group
</pre>

<h3>Groups</h3>
<pre>
GET:
curl -i -H "Accept: application/json" http://localhost:8080/Groups
</pre>

<h3>Schema</h3>
<pre>
GET:
curl -i -H "Accept: application/json" http://localhost:8080/Schema
</pre>

<h3>Schemas</h3>
<pre>
GET:
curl -i -H "Accept: application/json" http://localhost:8080/Schemas
</pre>

<br/>
<br/>
<br/>

</body>
</html>
