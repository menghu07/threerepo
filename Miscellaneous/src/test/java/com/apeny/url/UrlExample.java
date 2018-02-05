package com.apeny.url;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by apeny on 2018/1/14.
 */
public class UrlExample {
    public static void main(String[] args) {
        testUrl();
    }

    private static void testUrl() {
        String filePath = "http:///etc/host.txt";
        String urlPath = "http://www.example.com:9029/docs/resource1.html#fragment";
        try {
            //URL一定会有协议
            URL url = new URL(urlPath);
            System.out.println(url + " authority: " + url.getAuthority() + ", path: " + url.getPath());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            //URI就更宽泛些
            URI uri = new URI(filePath);
            System.out.println("uri : " + uri + " scheme specific part: " + uri.getSchemeSpecificPart()
                    + "scheme : " + uri.getScheme() + ", authority: " + uri.getAuthority() + ", path: " + uri.getPath());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }
}
