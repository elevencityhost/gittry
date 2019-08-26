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
    "auditGetResponse"
})
public class AuditGetResponseJson {

    /**
     * 
     */
    @JsonProperty("auditGetResponse")
    private List<AuditGetResponse> auditGetResponse = new ArrayList<AuditGetResponse>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The auditGetResponse
     */
    @JsonProperty("auditGetResponse")
    public List<AuditGetResponse> getAuditGetResponse() {
        return auditGetResponse;
    }

    /**
     * 
     * @param auditGetResponse
     *     The auditGetResponse
     */
    @JsonProperty("auditGetResponse")
    public void setAuditGetResponse(List<AuditGetResponse> auditGetResponse) {
        this.auditGetResponse = auditGetResponse;
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
