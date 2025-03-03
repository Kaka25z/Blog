package com.blog.server.entity.vo;

import lombok.Data;

@Data
public class PairVO<K, V> {

    private K pairKey;

    private V pairValue;

    public PairVO() {
    }

    public PairVO(K pairKey, V pairValue) {
        this.pairKey = pairKey;

        this.pairValue = pairValue;
    }

}
