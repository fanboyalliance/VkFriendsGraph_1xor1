package infrastructure.config;

import com.fasterxml.jackson.annotation.JsonSetter;

public class Config {
    public static String OAUTH_BASE_POINT;
    public static String API_BASE_POINT;
    public static String VERSION;
    public static String CLIENT_ID;
    public static String REDIRECT_URI;
    public static String GET_FRIENDS = "method/friends.get";
    public static String GET_MUTUAL_FRIENDS = "method/friends.getMutual";
    public static String API_TOKEN; // TODO: think about it
    public static long USER_ID; // TODO: think about it

    // TODO: while not found a way to deserialize into static fields
    private String oauthBasePoint;
    private String apiBasePoint;
    private String version;
    private String clientId;
    private String redirectUri;

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

    @JsonSetter("redirectUri")
    public void setRedirectUri(String redirectUri) {
        REDIRECT_URI = redirectUri;
    }
}
