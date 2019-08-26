package com.redn.connect.audit.pojo.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    "validationPutResponse"
})
public class ValidationPutResponseJson {

    /**
     * 
     */
    @JsonProperty("validationPutResponse")
    private List<ValidationPutResponse> validationPutResponse = new ArrayList<ValidationPutResponse>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The validationPutResponse
     */
    @JsonProperty("validationPutResponse")
    public List<ValidationPutResponse> getValidationPutResponse() {
        return validationPutResponse;
    }

    /**
     * 
     * @param validationPutResponse
     *     The validationPutResponse
     */
    @JsonProperty("validationPutResponse")
    public void setValidationPutResponse(List<ValidationPutResponse> validationPutResponse) {
        this.validationPutResponse = validationPutResponse;
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
