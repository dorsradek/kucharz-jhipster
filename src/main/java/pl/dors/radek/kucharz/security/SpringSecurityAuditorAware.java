package pl.dors.radek.kucharz.security;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;
import pl.dors.radek.kucharz.config.Constants;

/**
 * Implementation of AuditorAware based on Spring Security.
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {

    @Override
    public String getCurrentAuditor() {
        String userName = SecurityUtils.getCurrentLogin();
        return (userName != null ? userName : Constants.SYSTEM_ACCOUNT);
    }
}
