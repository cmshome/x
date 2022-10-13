package com.lxk.jdk.file.scanner;

import lombok.Data;

import java.util.List;

/**
 * @author LiXuekai on 2022/1/13
 */
@Data
public class Message {
    private String pid;
    private String spid;
    private List<String> result;
    private String time;
    private long sec;


}
