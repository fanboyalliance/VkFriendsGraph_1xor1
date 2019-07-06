package main;

import com.fasterxml.jackson.annotation.JsonSetter;

public class Config {
    public static String OAUTH_BASE_POINT;
    public static String API_BASE_POINT;
    public static String VERSION;
    public static String CLIENT_ID;

    // TODO: while not found a way to deserialize into static fields
    private String oauthBasePoint;
    private String apiBasePoint;
    private String version;
    private String clientId;

    @JsonSetter("oauthBasePoint")
    public void setOauthBasePoint(String oauthBasePoint) {
        OAUTH_BASE_POINT = oauthBasePoint;
    }

    @JsonSetter("apiBasePoint")
    public void setApiBasePoint(String apiBasePoint) {
        API_BASE_POINT = apiBasePoint;
    }

    @JsonSetter("version")
    public void setVersion(String version) {
        VERSION = version;
    }

    @JsonSetter("clientId")
    public void setClientId(String clientId) {
        CLIENT_ID = clientId;
    }

}
