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
    "interface_id",
    "job_id",
    "prop_name",
    "system_id"
})
public class AuditPutRequest {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("interface_id")
    private String interfaceId;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("job_id")
    private String jobId;
    /**
     * 
     */
    @JsonProperty("prop_name")
    private List<String> propName = new ArrayList<String>();
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("system_id")
    private String systemId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * (Required)
     * 
     * @return
     *     The interfaceId
     */
    @JsonProperty("interface_id")
    public String getInterfaceId() {
        return interfaceId;
    }

    /**
     * 
     * (Required)
     * 
     * @param interfaceId
     *     The interface_id
     */
    @JsonProperty("interface_id")
    public void setInterfaceId(String interfaceId) {
        this.interfaceId = interfaceId;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The jobId
     */
    @JsonProperty("job_id")
    public String getJobId() {
        return jobId;
    }

    /**
     * 
     * (Required)
     * 
     * @param jobId
     *     The job_id
     */
    @JsonProperty("job_id")
    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    /**
     * 
     * @return
     *     The propName
     */
    @JsonProperty("prop_name")
    public List<String> getPropName() {
        return propName;
    }

    /**
     * 
     * @param propName
     *     The prop_name
     */
    @JsonProperty("prop_name")
    public void setPropName(List<String> propName) {
        this.propName = propName;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The systemId
     */
    @JsonProperty("system_id")
    public String getSystemId() {
        return systemId;
    }

    /**
     * 
     * (Required)
     * 
     * @param systemId
     *     The system_id
     */
    @JsonProperty("system_id")
    public void setSystemId(String systemId) {
        this.systemId = systemId;
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
