package com.vito.base.action;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Created by lenovo on 2016/8/11.
 */


public class FunActionDes {
    @JsonProperty("action_tittle")
    public String action_tittle;

    @JsonProperty("action_tag")
    public String action_tag;

    @JsonProperty("action")
    public Action action;

}
