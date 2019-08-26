package com.redn.connect.audit.pojo.json;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "validationPutRequest"
})
public class ValidationPutRequestJson {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("validationPutRequest")
    private ValidationPutRequest validationPutRequest;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * (Required)
     * 
     * @return
     *     The validationPutRequest
     */
    @JsonProperty("validationPutRequest")
    public ValidationPutRequest getValidationPutRequest() {
        return validationPutRequest;
    }

    /**
     * 
     * (Required)
     * 
     * @param validationPutRequest
     *     The validationPutRequest
     */
    @JsonProperty("validationPutRequest")
    public void setValidationPutRequest(ValidationPutRequest validationPutRequest) {
        this.validationPutRequest = validationPutRequest;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
