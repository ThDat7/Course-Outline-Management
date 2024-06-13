package com.dat.configs;
import com.dat.pojo.User;
import com.dat.pojo.UserStatus;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class UserStatusAccessDecisionVoter implements AccessDecisionVoter<FilterInvocation> {
    @Override
    public int vote(Authentication authentication, FilterInvocation fi, Collection<ConfigAttribute> attributes) {
        if ( authentication.getPrincipal().equals("anonymousUser"))
            return ACCESS_ABSTAIN;

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserStatus status = userDetails.getUser().getStatus();

        if (status == UserStatus.DISABLED || status == UserStatus.PENDING) {
            return ACCESS_DENIED;
        }

        String requestUrl = fi.getRequestUrl();

        if (status == UserStatus.NEED_INFO && !requestUrl.startsWith("/additional-info")) {
            return ACCESS_DENIED;
        }

        return ACCESS_GRANTED;
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
