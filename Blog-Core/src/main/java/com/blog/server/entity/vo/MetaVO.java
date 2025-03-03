package com.blog.server.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MetaVO {

    private String title;

    private String icon;

    private Integer rank;

    private List<String> roles;

}
