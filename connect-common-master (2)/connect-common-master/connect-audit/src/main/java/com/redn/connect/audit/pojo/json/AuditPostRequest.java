
package com.redn.connect.audit.pojo.json;
/**
 * * *@author  Lakshmi Maram
 
 * This pojo class includes the Post Request of audit
 **/
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
    "batch_counter",
    "comment",
    "external_id",
    "interface_id",
    "job_id",
    "phase_id",
    "prop_name",
    "prop_value",
    "status_id",
    "system_id"
})
public class AuditPostRequest {

    /**
     * 
     */
    @JsonProperty("batch_counter")
    private String batchCounter;
    /**
     * 
     */
    @JsonProperty("comment")
    private String comment;
    /**
     * 
     */
    @JsonProperty("external_id")
    private String externalId;
    /**
     * 
     */
    @JsonProperty("interface_id")
    private String interfaceId;
    /**
     * 
     */
    @JsonProperty("job_id")
    private String jobId;
    /**
     * 
     */
    @JsonProperty("phase_id")
    private String phaseId;
    /**
     * 
     */
    @JsonProperty("prop_name")
    private String propName;
    /**
     * 
     */
    @JsonProperty("prop_value")
    private String propValue;
    /**
     * 
     */
    @JsonProperty("status_id")
    private String statusId;
    /**
     * 
     */
    @JsonProperty("system_id")
    private String systemId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The batchCounter
     */
    @JsonProperty("batch_counter")
    public String getBatchCounter() {
        return batchCounter;
    }

    /**
     * 
     * @param batchCounter
     *     The batch_counter
     */
    @JsonProperty("batch_counter")
    public void setBatchCounter(String batchCounter) {
        this.batchCounter = batchCounter;
    }

    /**
     * 
     * @return
     *     The comment
     */
    @JsonProperty("comment")
    public String getComment() {
        return comment;
    }

    /**
     * 
     * @param comment
     *     The comment
     */
    @JsonProperty("comment")
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * 
     * @return
     *     The externalId
     */
    @JsonProperty("external_id")
    public String getExternalId() {
        return externalId;
    }

    /**
     * 
     * @param externalId
     *     The external_id
     */
    @JsonProperty("external_id")
    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    /**
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
     * @param interfaceId
     *     The interface_id
     */
    @JsonProperty("interface_id")
    public void setInterfaceId(String interfaceId) {
        this.interfaceId = interfaceId;
    }

    /**
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
     *     The phaseId
     */
    @JsonProperty("phase_id")
    public String getPhaseId() {
        return phaseId;
    }

    /**
     * 
     * @param phaseId
     *     The phase_id
     */
    @JsonProperty("phase_id")
    public void setPhaseId(String phaseId) {
        this.phaseId = phaseId;
    }

    /**
     * 
     * @return
     *     The propName
     */
    @JsonProperty("prop_name")
    public String getPropName() {
        return propName;
    }

    /**
     * 
     * @param propName
     *     The prop_name
     */
    @JsonProperty("prop_name")
    public void setPropName(String propName) {
        this.propName = propName;
    }

    /**
     * 
     * @return
     *     The propValue
     */
    @JsonProperty("prop_value")
    public String getPropValue() {
        return propValue;
    }

    /**
     * 
     * @param propValue
     *     The prop_value
     */
    @JsonProperty("prop_value")
    public void setPropValue(String propValue) {
        this.propValue = propValue;
    }

    /**
     * 
     * @return
     *     The statusId
     */
    @JsonProperty("status_id")
    public String getStatusId() {
        return statusId;
    }

    /**
     * 
     * @param statusId
     *     The status_id
     */
    @JsonProperty("status_id")
    public void setStatusId(String statusId) {
        this.statusId = statusId;
    }

    /**
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
