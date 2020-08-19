package com.slaask.sdk;

import org.json.JSONObject;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class SlaaskIdentity {
    private HashMap<String, String> attributes;

    public SlaaskIdentity() {
        this.attributes = new HashMap<>();
        setName("Unknown");
        setCustomAttribute("kind", "lead");
    }

    public SlaaskIdentity setId(String id) {
        return setCustomAttribute("id", id);
    }

    public SlaaskIdentity setIdentityHash(String hash) {
        return setCustomAttribute("user_hash", hash);
    }

    public SlaaskIdentity setName(String name) {
        return setCustomAttribute("name", name);
    }

    public SlaaskIdentity setEmail(String email) {
        return setCustomAttribute("email", email);
    }

    public SlaaskIdentity setAvatar(String avatar) {
        return setCustomAttribute("avatar", avatar);
    }

    public SlaaskIdentity setRegisteredAt(String registeredAt) {
        return setCustomAttribute("registered_at", registeredAt);
    }

    public SlaaskIdentity setCustomAttribute(String key, String value) {
        this.attributes.put(key, value);
        return this;
    }

    public String build() {
        return new JSONObject(attributes).toString();
    }
}
