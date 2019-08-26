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
    "auditPostRequest"
})
public class AuditPostRequestJson {

    /**
     * 
     */
    @JsonProperty("auditPostRequest")
    private AuditPostRequest auditPostRequest;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The auditPostRequest
     */
    @JsonProperty("auditPostRequest")
    public AuditPostRequest getAuditPostRequest() {
        return auditPostRequest;
    }

    /**
     * 
     * @param auditPostRequest
     *     The auditPostRequest
     */
    @JsonProperty("auditPostRequest")
    public void setAuditPostRequest(AuditPostRequest auditPostRequest) {
        this.auditPostRequest = auditPostRequest;
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
