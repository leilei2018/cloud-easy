package com.fd.cloud.serviceapi.ums.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AdminDto implements Serializable {
    private String name;
    private Date startTime;
}
