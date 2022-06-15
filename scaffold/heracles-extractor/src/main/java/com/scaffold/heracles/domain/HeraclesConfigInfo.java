package com.scaffold.heracles.domain;

import lombok.Data;
import lombok.ToString;

/**
 * Created by Karl on 2022/3/4
 *
 * @author Karl
 */
@Data
@ToString
public class HeraclesConfigInfo {
    private String configId;
    private String version;
    private String name;
    private String format;
    private String aId;
    private String value;
    private String envId;
}
