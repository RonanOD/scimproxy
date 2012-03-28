/**
 * 
 */

var userConfig = {
  "id":"urn:scim:schemas:core:1.0:User",
  "name":"User",
  "description":"Core User",
  "schema":"urn:scim:schemas:core:1.0",
  "endpoint":"/Users",
  "attributes":[
    {
      "name":"id",
      "type":"string",
      "multiValued":false,
      "description":"Unique identifier for the SCIM resource as defined by the Service Provider. Each representation of the resource MUST include a non-empty id value. This identifier MUST be unique across the Service Provider's entire set of resources. It MUST be a stable, non-reassignable identifier that does not change when the same resource is returned in subsequent requests. The value of the id attribute is always issued by the Service Provider and MUST never be specified by the Service Consumer. REQUIRED.",
      "schema":"urn:scim:schemas:core:1.0",
      "readOnly":true,
      "required":true,
      "caseExact":false
    },
    {
      "name":"externalId",
      "type":"string",
      "multiValued":false,
      "description":"Unique identifier for the Resource as defined by the Service Consumer. The externalId may simplify identification of the Resource between Service Consumer and Service provider by allowing the Consumer to refer to the Resource with its own identifier, obviating the need to store a local mapping between the local identifier of the Resource and the identifier used by the Service Provider. Each Resource MAY include a non-empty externalId value. The value of the externalId attribute is always issued be the Service Consumer and can never be specified by the Service Provider. This identifier MUST be unique across the Service Consumer's entire set of Resources. It MUST be a stable, non-reassignable identifier that does not change when the same Resource is returned in subsequent requests. The Service Provider MUST always interpret the externalId as scoped to the Service Consumer's tenant.",
      "schema":"urn:scim:schemas:core:1.0",
      "readOnly":false,
      "required":false,
      "caseExact":false
    },
    {
      "name":"displayName",
      "type":"string",
      "multiValued":false,
      "description":"The name of the User, suitable for display to end-users. Each User returned MAY include a non-empty displayName value. The name SHOULD be the full name of the User being described if known (e.g. Babs Jensen or Ms. Barbara J Jensen, III), but MAY be a username or handle, if that is all that is available (e.g. bjensen). The value provided SHOULD be the primary textual label by which this User is normally displayed by the Service Provider when presenting it to end-users.",
      "schema":"urn:scim:schemas:core:1.0",
      "readOnly":false,
      "required":false,
      "caseExact":false
    },
    {
      "name":"nickName",
      "type":"string",
      "multiValued":false,
      "description":"The casual way to address the user in real life, e.g. \"Bob\" or \"Bobby\" instead of \"Robert\". This attribute SHOULD NOT be used to represent a User's username (e.g. bjensen or mpepperidge).",
      "schema":"urn:scim:schemas:core:1.0",
      "readOnly":false,
      "required":false,
      "caseExact":false
    },
    {
      "name":"profileUrl",
      "type":"string",
      "multiValued":false,
      "description":"A fully qualified URL to a page representing the User's online profile.",
      "schema":"urn:scim:schemas:core:1.0",
      "readOnly":false,
      "required":false,
      "caseExact":false
    },
    {
      "name":"title",
      "type":"string",
      "multiValued":false,
      "description":"The user’s title, such as \"Vice President.\"",
      "schema":"urn:scim:schemas:core:1.0",
      "readOnly":false,
      "required":false,
      "caseExact":false
    },
    {
      "name":"userType",
      "type":"string",
      "multiValued":false,
      "description":"Used to identify the organization to user relationship. Typical values used might be \"Contractor\", \"Employee\", \"Intern\", \"Temp\", \"External\", and \"Unknown\" but any value may be used.",
      "schema":"urn:scim:schemas:core:1.0",
      "readOnly":false,
      "required":false,
      "caseExact":false
    },
    {
      "name":"preferredLanguage",
      "type":"string",
      "multiValued":false,
      "description":"Indicates the User's preferred written or spoken language. Generally used for selecting a localized User interface. Valid values are concatenation of the ISO 639-1 two letter language code, an underscore, and the ISO 3166-1 2 letter country code; e.g., 'en_US' specifies the language English and country US.",
      "schema":"urn:scim:schemas:core:1.0",
      "readOnly":false,
      "required":false,
      "caseExact":false
    },
    {
      "name":"locale",
      "type":"string",
      "multiValued":false,
      "description":"Used to indicate the User's default location for purposes of localizing items such as currency, date time format, numerical representations, etc. A locale value is a concatenation of the ISO 639-1 two letter language code, an underscore, and the ISO 3166-1 2 letter country code; e.g., 'en_US' specifies the language English and country US.",
      "schema":"urn:scim:schemas:core:1.0",
      "readOnly":false,
      "required":false,
      "caseExact":false
    },
    {
      "name":"timezone",
      "type":"string",
      "multiValued":false,
      "description":"The User's time zone in the \"Olson\" timezone database format; e.g.,'America/Los_Angeles'.",
      "schema":"urn:scim:schemas:core:1.0",
      "readOnly":false,
      "required":false,
      "caseExact":false
    },
    {
      "name":"active",
      "type":"boolean",
      "multiValued":false,
      "description":"A Boolean value indicating the User's administrative status. The definitive meaning of this attribute is determined by the Service Provider though a value of true infers the User is, for example, able to login while a value of false implies the User's account has been suspended.",
      "schema":"urn:scim:schemas:core:1.0",
      "readOnly":false,
      "required":false,
      "caseExact":false
    },
    {
      "name":"password",
      "type":"string",
      "multiValued":false,
      "description":"The User's clear text password. This attribute is intended to be used as a means to specify an initial password when creating a new User or to reset an existing User's password. No accepted standards exist to convey password policies, hence Consumers should expect Service Providers to reject password values. This value MUST never be returned by a Service Provider in any form.",
      "schema":"urn:scim:schemas:core:1.0",
      "readOnly":false,
      "required":false,
      "caseExact":false
    },
    {
        "name":"userName",
        "type":"string",
        "multiValued":false,
        "description":"Unique identifier for the User, typically used by the user to directly authenticate to the service provider. Often displayed to the user as their unique identifier within the system (as opposed to id or externalId, which are generally opaque and not user-friendly identifiers). Each User MUST include a non-empty userName value. This identifier MUST be unique across the Service Consumer's entire set of Users. It MUST be a stable ID that does not change when the same User is returned in subsequent requests. REQUIRED.",
        "schema":"urn:scim:schemas:core:1.0",
        "readOnly":false,
        "required":true,
        "caseExact":false
    },
    {
      "name":"name",
      "type":"complex",
      "multiValued":false,
      "description":"The components of the user's real name. Providers MAY return just the full name as a single string in the formatted sub-attribute, or they MAY return just the individual component attributes using the other sub-attributes, or they MAY return both. If both variants are returned, they SHOULD be describing the same name, with the formatted name indicating how the component attributes should be combined.",
      "schema":"urn:scim:schemas:core:1.0",
      "readOnly":false,
      "required":false,
      "caseExact":false,
      "subAttributes":[
        {
          "name":"formatted",
          "type":"string",
          "multiValued":false,
          "description":"The full name, including all middle names, titles, and suffixes as appropriate, formatted for display (e.g. Ms. Barbara J Jensen, III.)." ,
          "readOnly":false,
          "required":false,
          "caseExact":false
        },
        {
          "name":"familyName",
          "type":"string",
          "multiValued":false,
          "description":"The family name of the User, or Last Name in most Western languages (e.g. Jensen given the full name Ms. Barbara J Jensen, III.).",
          "readOnly":false,
          "required":false,
          "caseExact":false
        },
        {
          "name":"givenName",
          "type":"string",
          "multiValued":false,
          "description":"The given name of the User, or First Name in most Western languages (e.g. Barbara given the full name Ms. Barbara J Jensen, III.).",
          "readOnly":false,
          "required":false,
          "caseExact":false
        },
        {
          "name":"middleName",
          "type":"string",
          "multiValued":false,
          "description":"The middle name(s) of the User (e.g. Robert given the full name Ms. Barbara J Jensen, III.).",
          "readOnly":false,
          "required":false,
          "caseExact":false
        },
        {
          "name":"honorificPrefix",
          "type":"string",
          "multiValued":false,
          "description":"The honorific prefix(es) of the User, or Title in most Western languages (e.g. Ms. given the full name Ms. Barbara J Jensen, III.).",
          "readOnly":false,
          "required":false,
          "caseExact":false
        },
        {
          "name":"honorificSuffix",
          "type":"string",
          "multiValued":false,
          "description":"The honorific suffix(es) of the User, or Suffix in most Western languages (e.g. III. given the full name Ms. Barbara J Jensen, III.).",
          "readOnly":false,
          "required":false,
          "caseExact":false
        }
      ]
     },
     {
       "name":"emails",
       "type":"complex",
       "multiValued":true,
       "multiValuedAttributeChildName":"email",
       "description":"E-mail addresses for the user. The value SHOULD be canonicalized by the Service Provider, e.g. bjensen@example.com instead of bjensen@EXAMPLE.COM. Canonical Type values of work, home, and other.",
       "schema":"urn:scim:schemas:core:1.0",
       "readOnly":false,
       "required":false,
       "caseExact":false,
       "subAttributes":[
         {
           "name":"value",
           "type":"string",
           "multiValued":false,
           "description":"E-mail addresses for the user. The value SHOULD be canonicalized by the Service Provider, e.g. bjensen@example.com instead of bjensen@EXAMPLE.COM. Canonical Type values of work, home, and other.",
           "readOnly":false,
           "required":true,
           "caseExact":false
         },
         {
           "name":"display",
           "type":"string",
           "multiValued":false,
           "description":"A human readable name, primarily used for display purposes. READ-ONLY.",
           "readOnly":true,
           "required":false,
           "caseExact":false
         },
         {
           "name":"type",
           "type":"string",
           "multiValued":false,
           "description":"A label indicating the attribute's function; e.g., 'work' or 'home'.",
           "readOnly":false,
           "required":false,
           "caseExact":false,
           "canonicalValues":["work","home","other"]
         },
         {
           "name":"primary",
           "type":"boolean",
           "multiValued":false,
           "description":"A Boolean value indicating the 'primary' or preferred attribute value for this attribute, e.g. the preferred mailing address or primary e-mail address. The primary attribute value 'true' MUST appear no more than once.",
           "readOnly":false,
           "required":false,
           "caseExact":false
         }
       ]
     },
     {
       "name":"phoneNumbers",
       "type":"complex",
       "multiValued":true,
       "multiValuedAttributeChildName":"phoneNumber",
       "description":"Phone numbers for the User. No canonical value is assumed here. Canonical Type values of work, home, mobile, fax, pager and other.",
       "schema":"urn:scim:schemas:core:1.0",
       "readOnly":false,
       "required":false,
       "caseExact":false,
       "subAttributes":[
         {
           "name":"value",
           "type":"string",
           "multiValued":false,
           "description":"Phone numbers for the User. No canonical value is assumed here. Canonical Type values of work, home, mobile, fax, pager and other.",
           "readOnly":false,
           "required":true,
           "caseExact":false
         },
         {
           "name":"display",
           "type":"string",
           "multiValued":false,
           "description":"A human readable name, primarily used for display purposes. READ-ONLY.",
           "readOnly":true,
           "required":false,
           "caseExact":false
         },
         {
           "name":"type",
           "type":"string",
           "multiValued":false,
           "description":"A label indicating the attribute's function; e.g., 'work', 'home', 'mobile', 'fax' or 'pager'.",
           "readOnly":false,
           "required":false,
           "caseExact":false,
           "canonicalValues":['work', 'home', 'mobile', 'fax', 'pager']
         },
         {
           "name":"primary",
           "type":"boolean",
           "multiValued":false,
           "description":"A Boolean value indicating the 'primary' or preferred attribute value for this attribute",
           "readOnly":false,
           "required":false,
           "caseExact":false
         }
       ]
     },
     {
       "name":"ims",
       "type":"complex",
       "multiValued":true,
       "multiValuedAttributeChildName":"im",
       "description":"Instant messaging address for the User. No official canonicalization rules exist for all instant messaging addresses, but Service Providers SHOULD, when appropriate, remove all whitespace and convert the address to lowercase. Instead of the standard Canonical Values for type, this attribute defines the following Canonical Values to represent currently popular IM services: aim, gtalk, icq, xmpp, msn, skype, qq, and yahoo.",
       "schema":"urn:scim:schemas:core:1.0",
       "readOnly":false,
       "required":false,
       "caseExact":false,
       "subAttributes":[
         {
           "name":"value",
           "type":"string",
           "multiValued":false,
           "description":"Instant messaging address for the User. No official canonicalization rules exist for all instant messaging addresses, but Service Providers SHOULD, when appropriate, remove all whitespace and convert the address to lowercase. Instead of the standard Canonical Values for type, this attribute defines the following Canonical Values to represent currently popular IM services: aim, gtalk, icq, xmpp, msn, skype, qq, and yahoo.",
           "readOnly":false,
           "required":true,
           "caseExact":false
         },
         {
           "name":"display",
           "type":"string",
           "multiValued":false,
           "description":"A human readable name, primarily used for display purposes. READ-ONLY.",
           "readOnly":true,
           "required":false,
           "caseExact":false
         },
         {
           "name":"type",
           "type":"string",
           "multiValued":false,
           "description":"A label indicating the attribute's function; e.g., 'aim', 'gtalk', 'icq', 'xmpp', 'msn', 'skype', 'qq', or 'yahoo'.",
           "readOnly":false,
           "required":false,
           "caseExact":false,
           "canonicalValues":['aim', 'gtalk', 'icq', 'xmpp', 'msn', 'skype', 'qq', 'yahoo']
         },
         {
           "name":"primary",
           "type":"boolean",
           "multiValued":false,
           "description":"A Boolean value indicating the 'primary' or preferred attribute value for this attribute",
           "readOnly":false,
           "required":false,
           "caseExact":false
         }
       ]
     },
     {
       "name":"photos",
       "type":"complex",
       "multiValued":true,
       "multiValuedAttributeChildName":"photo",
       "description":"URL of a photo of the User. The value SHOULD be a canonicalized URL, and MUST point to an image file (e.g. a GIF, JPEG, or PNG image file) rather than to a web page containing an image. Service Providers MAY return the same image at different sizes, though it is recognized that no standard for describing images of various sizes currently exists. Note that this attribute SHOULD NOT be used to send down arbitrary photos taken by this User, but specifically profile photos of the User suitable for display when describing the User. Instead of the standard Canonical Values for type, this attribute defines the following Canonical Values to represent popular photo sizes: photo, thumbnail.",
       "schema":"urn:scim:schemas:core:1.0",
       "readOnly":false,
       "required":false,
       "caseExact":false,
       "subAttributes":[
         {
           "name":"value",
           "type":"string",
           "multiValued":false,
           "description":"URL of a photo of the User. The value SHOULD be a canonicalized URL, and MUST point to an image file (e.g. a GIF, JPEG, or PNG image file) rather than to a web page containing an image. Service Providers MAY return the same image at different sizes, though it is recognized that no standard for describing images of various sizes currently exists. Note that this attribute SHOULD NOT be used to send down arbitrary photos taken by this User, but specifically profile photos of the User suitable for display when describing the User. Instead of the standard Canonical Values for type, this attribute defines the following Canonical Values to represent popular photo sizes: photo, thumbnail.",
           "readOnly":false,
           "required":true,
           "caseExact":false
         },
         {
           "name":"display",
           "type":"string",
           "multiValued":false,
           "description":"A human readable name, primarily used for display purposes. READ-ONLY.",
           "readOnly":true,
           "required":false,
           "caseExact":false
         },
         {
           "name":"type",
           "type":"string",
           "multiValued":false,
           "description":"A label indicating the attribute's function; e.g., 'photo' or 'thumbnail'.",
           "readOnly":false,
           "required":false,
           "caseExact":false,
           "canonicalValues":["photo","thumbnail"]
         },
         {
           "name":"primary",
           "type":"boolean",
           "multiValued":false,
           "description":"A Boolean value indicating the 'primary' or preferred attribute value for this attribute",
           "readOnly":false,
           "required":false,
           "caseExact":false
         }
       ]
     },
     {
       "name":"groups",
       "type":"complex",
       "multiValued":true,
       "multiValuedAttributeChildName":"group",
       "description":"A list of groups that the user belongs to, either thorough direct membership, nested groups, or dynamically calculated. The values are meant to enable expression of common group or role based access control models, although no explicit authorization model is defined. It is intended that the semantics of group membership and any behavior or authorization granted as a result of membership are defined by the Service Provider. The Canonical types \"direct\" and \"indirect\" are defined to describe how the group membership was derived. Â Direct group membership indicates the User is directly associated with the group and SHOULD indicate that Consumers may modify membership through the Group Resource. Â Indirect membership indicates User membership is transitive or dynamic and implies that Consumers cannot modify indirect group membership through the Group resource but MAY modify direct group membership through the Group resource which MAY influence indirect memberships. Â If the SCIM Service Provider exposes a Group resource, the value MUST be the \"id\" attribute of the corresponding Group resources to which the user belongs. Since this attribute is read-only, group membership changes MUST be applied via the Group Resource. READ-ONLY.",
       "schema":"urn:scim:schemas:core:1.0",
       "readOnly":false,
       "required":false,
       "caseExact":false,
       "subAttributes":[
         {
           "name":"value",
           "type":"string",
           "multiValued":false,
           "description":"A list of groups that the user belongs to, either thorough direct membership, nested groups, or dynamically calculated. The values are meant to enable expression of common group or role based access control models, although no explicit authorization model is defined. It is intended that the semantics of group membership and any behavior or authorization granted as a result of membership are defined by the Service Provider. The Canonical types \"direct\" and \"indirect\" are defined to describe how the group membership was derived. Â Direct group membership indicates the User is directly associated with the group and SHOULD indicate that Consumers may modify membership through the Group Resource. Â Indirect membership indicates User membership is transitive or dynamic and implies that Consumers cannot modify indirect group membership through the Group resource but MAY modify direct group membership through the Group resource which MAY influence indirect memberships. Â If the SCIM Service Provider exposes a Group resource, the value MUST be the \"id\" attribute of the corresponding Group resources to which the user belongs. Since this attribute is read-only, group membership changes MUST be applied via the Group Resource. READ-ONLY.",
           "readOnly":false,
           "required":true,
           "caseExact":false
         },
         {
           "name":"display",
           "type":"string",
           "multiValued":false,
           "description":"A human readable name, primarily used for display purposes. READ-ONLY.",
           "readOnly":true,
           "required":false,
           "caseExact":false
         },
         {
           "name":"type",
           "type":"string",
           "multiValued":false,
           "description":"A label indicating the attribute's function.",
           "readOnly":false,
           "required":false,
           "caseExact":false,
           "canonicalValues":[]
         },
         {
           "name":"primary",
           "type":"boolean",
           "multiValued":false,
           "description":"A Boolean value indicating the 'primary' or preferred attribute value for this attribute",
           "readOnly":false,
           "required":false,
           "caseExact":false
         }
       ]
     },
     {
       "name":"entitlements",
       "type":"complex",
       "multiValued":true,
       "multiValuedAttributeChildName":"entitlement",
       "description":"A list of entitlements for the User that represent a thing the User has. That is, an entitlement is an additional right to a thing, object or service. No vocabulary or syntax is specified and Service Providers/Consumers are expected to encode sufficient information in the value so as to accurately and without ambiguity determine what the User has access to. This value has NO canonical types though type may be useful as a means to scope entitlements.",
       "schema":"urn:scim:schemas:core:1.0",
       "readOnly":false,
       "required":false,
       "caseExact":false,
       "subAttributes":[
         {
           "name":"value",
           "type":"string",
           "multiValued":false,
           "description":"A list of entitlements for the User that represent a thing the User has. That is, an entitlement is an additional right to a thing, object or service. No vocabulary or syntax is specified and Service Providers/Consumers are expected to encode sufficient information in the value so as to accurately and without ambiguity determine what the User has access to. This value has NO canonical types though type may be useful as a means to scope entitlements.",
           "readOnly":false,
           "required":true,
           "caseExact":false
         },
         {
           "name":"display",
           "type":"string",
           "multiValued":false,
           "description":"A human readable name, primarily used for display purposes. READ-ONLY.",
           "readOnly":true,
           "required":false,
           "caseExact":false
         },
         {
           "name":"type",
           "type":"string",
           "multiValued":false,
           "description":"A label indicating the attribute's function.",
           "readOnly":false,
           "required":false,
           "caseExact":false,
           "canonicalValues":[]
         },
         {
           "name":"primary",
           "type":"boolean",
           "multiValued":false,
           "description":"A Boolean value indicating the 'primary' or preferred attribute value for this attribute",
           "readOnly":false,
           "required":false,
           "caseExact":false
         }
       ]
     },
     {
       "name":"roles",
       "type":"complex",
       "multiValued":true,
       "multiValuedAttributeChildName":"role",
       "description":"A list of roles for the User that collectively represent who the User is; e.g., 'Student', 'Faculty'. No vocabulary or syntax is specified though it is expected that a role value is a String or label representing a collection of entitlements. This value has NO canonical types.",
       "schema":"urn:scim:schemas:core:1.0",
       "readOnly":false,
       "required":false,
       "caseExact":false,
       "subAttributes":[
         {
           "name":"value",
           "type":"string",
           "multiValued":false,
           "description":"A list of roles for the User that collectively represent who the User is; e.g., 'Student', 'Faculty'. No vocabulary or syntax is specified though it is expected that a role value is a String or label representing a collection of entitlements. This value has NO canonical types.",
           "readOnly":false,
           "required":true,
           "caseExact":false
         },
         {
           "name":"display",
           "type":"string",
           "multiValued":false,
           "description":"A human readable name, primarily used for display purposes. READ-ONLY.",
           "readOnly":true,
           "required":false,
           "caseExact":false
         },
         {
           "name":"type",
           "type":"string",
           "multiValued":false,
           "description":"A label indicating the attribute's function.",
           "readOnly":false,
           "required":false,
           "caseExact":false,
           "canonicalValues":[]
         },
         {
           "name":"primary",
           "type":"boolean",
           "multiValued":false,
           "description":"A Boolean value indicating the 'primary' or preferred attribute value for this attribute",
           "readOnly":false,
           "required":false,
           "caseExact":false
         }
       ]
     },
     {
       "name":"x509Certificates",
       "type":"complex",
       "multiValued":true,
       "multiValuedAttributeChildName":"x509Certificate",
       "description":"A list of certificates issued to the User. Values are Binary and DER encoded x509. This value has NO canonical types.",
       "schema":"urn:scim:schemas:core:1.0",
       "readOnly":false,
       "required":false,
       "caseExact":false,
       "subAttributes":[
         {
           "name":"value",
           "type":"string",
           "multiValued":false,
           "description":"A list of certificates issued to the User. Values are Binary and DER encoded x509. This value has NO canonical types.",
           "readOnly":false,
           "required":true,
           "caseExact":false
         },
         {
           "name":"display",
           "type":"string",
           "multiValued":false,
           "description":"A human readable name, primarily used for display purposes. READ-ONLY.",
           "readOnly":true,
           "required":false,
           "caseExact":false
         },
         {
           "name":"type",
           "type":"string",
           "multiValued":false,
           "description":"A label indicating the attribute's function.",
           "readOnly":false,
           "required":false,
           "caseExact":false,
           "canonicalValues":[]
         },
         {
           "name":"primary",
           "type":"boolean",
           "multiValued":false,
           "description":"A Boolean value indicating the 'primary' or preferred attribute value for this attribute",
           "readOnly":false,
           "required":false,
           "caseExact":false
         }
       ]
     },
     {
       "name":"addresses",
       "type":"complex",
       "multiValued":true,
       "multiValuedAttributeChildName":"address",
       "description":"A physical mailing address for this User, as described in (address Element). Canonical Type Values of work, home, and other. The value attribute is a complex type with the following sub-attributes.",
       "schema":"urn:scim:schemas:core:1.0",
       "readOnly":false,
       "required":false,
       "caseExact":false,
       "subAttributes":[
         {
           "name":"formatted",
           "type":"string",
           "multiValued":false,
           "description":"The full mailing address, formatted for display or use with a mailing label. This attribute MAY contain newlines.",
           "readOnly":false,
           "required":false,
           "caseExact":false
         },
         {
           "name":"streetAddress",
           "type":"string",
           "multiValued":false,
           "description":"The full street address component, which may include house number, street name, PO BOX, and multi-line extended street address information. This attribute MAY contain newlines.",
           "readOnly":false,
           "required":false,
           "caseExact":false
         },
         {
           "name":"locality",
           "type":"string",
           "multiValued":false,
           "description":"The city or locality component.",
           "readOnly":false,
           "required":false,
           "caseExact":false
         },
         {
           "name":"region",
           "type":"string",
           "multiValued":false,
           "description":"The state or region component.",
           "readOnly":false,
           "required":false,
           "caseExact":false
         },
         {
           "name":"postalCode",
           "type":"string",
           "multiValued":false,
           "description":"The zipcode or postal code component.",
           "readOnly":false,
           "required":false,
           "caseExact":false
         },
         {
           "name":"country",
           "type":"string",
           "multiValued":false,
           "description":"The country name component.",
           "readOnly":false,
           "required":false,
           "caseExact":false
         },
         {
           "name":"type",
           "type":"string",
           "multiValued":false,
           "description":"A label indicating the attribute's function; e.g., 'work' or 'home'.",
           "readOnly":false,
           "required":false,
           "caseExact":false,
           "canonicalValues":["work","home","other"]
         },
       ]
     }
   ]
};