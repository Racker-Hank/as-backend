package vnu.uet.AppointmentScheduler.config.security;

import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import vnu.uet.AppointmentScheduler.model.user.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SuperBuilder
public class UserDetailsImpl extends User implements UserDetails {
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(this.getUserRole().toString()));
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.getEmail();
    }
}
