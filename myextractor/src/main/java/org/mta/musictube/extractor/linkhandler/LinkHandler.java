package org.mta.musictube.extractor.linkhandler;

import java.io.Serializable;

public class LinkHandler implements Serializable {
    protected final String originalUrl;
    protected final String url;
    protected final String id;

    public LinkHandler(String originalUrl, String url, String id) {
        this.originalUrl = originalUrl;
        this.url = url;
        this.id = id;
    }

    public LinkHandler(LinkHandler handler) {
        this(handler.originalUrl, handler.url, handler.id);
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getUrl() {
        return url;
    }

    public String getId() {
        return id;
    }

}
