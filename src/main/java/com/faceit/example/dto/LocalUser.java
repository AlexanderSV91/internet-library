package com.faceit.example.dto;

import com.faceit.example.tables.records.RolesRecord;
import com.faceit.example.tables.records.UsersRecord;
import com.faceit.example.util.Utils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class LocalUser extends User implements OAuth2User, OidcUser {

    //private static final long serialVersionUID = -2845160792248762779L;
    private final OidcIdToken idToken;
    private final OidcUserInfo userInfo;
    private final UsersRecord user;
    private final List<RolesRecord> roles;
    private Map<String, Object> attributes;

    public LocalUser(String userID, String password, boolean enabled, boolean accountNonExpired,
                     boolean credentialsNonExpired, boolean accountNonLocked,
                     Collection<? extends GrantedAuthority> authorities, UsersRecord user,
                     List<RolesRecord> roles) {
        this(userID, password, enabled, accountNonExpired, credentialsNonExpired,
                accountNonLocked, authorities, user, roles, null, null);
    }

    public LocalUser(String userID, String password, boolean enabled, boolean accountNonExpired,
                     boolean credentialsNonExpired, boolean accountNonLocked,
                     Collection<? extends GrantedAuthority> authorities, UsersRecord user,
                     List<RolesRecord> roles, OidcIdToken idToken, OidcUserInfo userInfo) {
        super(userID, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.user = user;
        this.roles = roles;
        this.idToken = idToken;
        this.userInfo = userInfo;
    }

    public static LocalUser create(UsersRecord user, List<RolesRecord> roles, Map<String, Object> attributes,
                                   OidcIdToken idToken, OidcUserInfo userInfo) {
        LocalUser localUser = new LocalUser(
                user.getEmail(),
                user.getPassword(),
                user.getEnabled(),
                true,
                true,
                true,
                Utils.buildSimpleGrantedAuthorities(roles),
                user,
                roles,
                idToken,
                userInfo);
        localUser.setAttributes(attributes);
        return localUser;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return this.user.getUsername();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public Map<String, Object> getClaims() {
        return this.attributes;
    }

    @Override
    public OidcUserInfo getUserInfo() {
        return this.userInfo;
    }

    @Override
    public OidcIdToken getIdToken() {
        return this.idToken;
    }

    public UsersRecord getUser() {
        return user;
    }

    public List<RolesRecord> getRoles() {
        return roles;
    }
}
