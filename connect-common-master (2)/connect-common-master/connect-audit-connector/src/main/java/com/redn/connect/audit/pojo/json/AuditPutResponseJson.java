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
    "auditPutResponse"
})
public class AuditPutResponseJson {

    /**
     * 
     */
    @JsonProperty("auditPutResponse")
    private List<AuditPutResponse> auditPutResponse = new ArrayList<AuditPutResponse>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The auditPutResponse
     */
    @JsonProperty("auditPutResponse")
    public List<AuditPutResponse> getAuditPutResponse() {
        return auditPutResponse;
    }

    /**
     * 
     * @param auditPutResponse
     *     The auditPutResponse
     */
    @JsonProperty("auditPutResponse")
    public void setAuditPutResponse(List<AuditPutResponse> auditPutResponse) {
        this.auditPutResponse = auditPutResponse;
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
