package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.domain.Administrator;
import com.example.domain.LoginAdministrator;
import com.example.repository.AdministratorRepository;

/**
 * Spring Securityのログイン時に利用するUserDetailsServiceクラス
 *
 * @author T.Araki
 */
@Service
public class LoginAdministratorDetailsService implements UserDetailsService {

    @Autowired
    private AdministratorRepository administratorRepository;

    /**
     * 入力されたメールアドレスが存在する場合は情報を返す
     */
    @Override
    public UserDetails loadUserByUsername(String mailAddress) throws UsernameNotFoundException {
        Administrator administrator = administratorRepository.findByMailAddress(mailAddress);

        if (administrator == null) {
            throw new UsernameNotFoundException(mailAddress);
        }

        return new LoginAdministrator(administrator);

    }
}
