
package com.redn.connect.audit.pojo.json;
/**
 * * *@author  Lakshmi Maram
 
 * This pojo class is used in PUT Response Validation
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
    "comment",
    "prop_name",
    "status_id"
})
public class ValidationPutResponse {

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
     */
    @JsonProperty("status_id")
    private String statusId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
