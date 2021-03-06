{
  "operationId": "getProcessInstanceVariables",
  "description": "Retrieves all variables of a given process instance by id.",
  "tags": [
    "Process instance"
  ],
  "parameters": [

    <@lib.parameter
        name = "id"
        location = "path"
        type = "string"
        required = true
        description = "The id of the process instance to retrieve the variables from."/>


    <@lib.parameter
        name = "deserializeValue"
        location = "query"
        type = "boolean"
        hasDefault = true
        defaultValue = true
        last = true
        description = "Determines whether serializable variable values (typically variables that store custom Java objects) should be deserialized on server side (default true).\nIf set to true, a serializable variable will be deserialized on server side and transformed to JSON using Jackson's POJO/bean property introspection feature. Note that this requires the Java classes of the variable value to be on the REST API's classpath.\n\nIf set to false, a serializable variable will be returned in its serialized format. For example, a variable that is serialized as XML will be returned as a JSON string containing XML.\n\nNote: While true is the default value for reasons of backward compatibility, we recommend setting this parameter to false when developing web applications that are independent of the Java process applications deployed to the engine."/>

  ],
  "responses": {

    <@lib.response
        code = "200"
        dto = "VariableValueDto"
        additionalProperties = true
        desc = "Request successful."/>


    <@lib.response
        code = "500"
        dto = "ExceptionDto"
        last = true
        desc = "Process instance with given id does not exist."/>
  }
}