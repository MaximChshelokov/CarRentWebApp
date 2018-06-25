package com.mv.schelokov.car_rent.controller.actions;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class JspRedirect {
    private String url;
    
    public JspRedirect(String url) {
        this.url = url;
    }

    /**
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }
}
