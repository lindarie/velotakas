package lv.velotakas.app.models.enums;

public enum Role {
    ADMIN("admin"),
    AUTHENTICATED("authenticated");

    private String value;
    Role(String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
