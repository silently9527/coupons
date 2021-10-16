package cn.silently9527.coupons.rest.model;

import lombok.Data;

import java.util.List;

@Data
public class CodeFile {
    private String name;
    private boolean isDir;
    private String path;
    private List<CodeFile> children;
}
