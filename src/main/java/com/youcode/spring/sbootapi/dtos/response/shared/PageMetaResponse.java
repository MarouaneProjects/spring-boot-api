package com.youcode.spring.sbootapi.dtos.response.shared;

import com.youcode.spring.sbootapi.dtos.response.base.SuccessResponse;
import com.youcode.spring.sbootapi.dtos.response.base.SuccessResponse;

public class PageMetaResponse extends SuccessResponse {
    private final PageMeta pageMeta;

    public PageMetaResponse(PageMeta pageMeta) {
        this.pageMeta = pageMeta;
    }

    public PageMeta getPageMeta() {
        return pageMeta;
    }
}
