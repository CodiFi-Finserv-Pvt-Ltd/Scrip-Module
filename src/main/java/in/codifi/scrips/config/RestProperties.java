package in.codifi.scrips.config;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import lombok.Getter;
import lombok.Setter;

@ApplicationScoped
@Getter
@Setter
public class RestProperties {
	@ConfigProperty(name = "config.tr.url.securityinfo")
	private String getSecurityInfo;

}
