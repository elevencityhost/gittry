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
    "comment",
    "prop_name",
    "status_id"
})
public class AuditPutResponse {
	
	/**
     * 
     */
    @JsonProperty("system_id")
    private String system_id;

    /**
     * 
     */
    @JsonProperty("comment")
    private String comment;
    /**
     * 
     */
    @JsonProperty("prop_name")
    private String propName;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("status_id")
    private String statusId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
    
    
    

    /**
	 * @return the system_id
	 */
	public String getSystem_id() {
		return system_id;
	}

	/**
	 * @param system_id the system_id to set
	 */
	public void setSystem_id(String system_id) {
		this.system_id = system_id;
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
     * (Required)
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
     * (Required)
     * 
     * @param statusId
     *     The status_id
     */
    @JsonProperty("status_id")
    public void setStatusId(String statusId) {
        this.statusId = statusId;
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
