package com.example.domain;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * ログイン情報に利用するドメイン
 *
 * @author T.Araki
 */
public class LoginAdministrator implements UserDetails {

    private final Administrator administrator;

    /** コンストラクタ */
    public LoginAdministrator(Administrator administrator) {
        this.administrator = administrator;
    }

    /**
     * 今回は未使用
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    /**
     * ハッシュ化済みのパスワードを返却
     */
    @Override
    public String getPassword() {
        return this.administrator.getPassword();
    }

    /**
     * ユーザー名を返却
     */
    @Override
    public String getUsername() {
        return this.administrator.getName();
    }

    /**
     * 今回は未使用
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 今回は未使用
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 今回は未使用
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 今回は未使用
     */
    @Override
    public boolean isEnabled() {
        return true;
    }

}
