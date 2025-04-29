package in.codifi.scrips.config;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@ApplicationScoped
public class ApplicationProperties {

	/* Chat server config */
	@ConfigProperty(name = "config.app.ssh.host")
	private String sshHost;

	@ConfigProperty(name = "config.app.ssh.username")
	private String sshUserName;

	@ConfigProperty(name = "config.app.ssh.password")
	private String sshPassword;

	@ConfigProperty(name = "config.app.ssh.port")
	private int sshPort;

	@ConfigProperty(name = "config.app.ssh.file.path")
	private String remoteContractDire;

	@ConfigProperty(name = "config.app.local.file.path")
	private String localcontractDir;

	/* db values config */

	@ConfigProperty(name = "quarkus.datasource.username")
	private String dbUserName;

	@ConfigProperty(name = "quarkus.datasource.password")
	private String dbpassword;

	@ConfigProperty(name = "config.app.db.schema")
	private String dbSchema;

	@ConfigProperty(name = "config.app.db.host")
	private String dbHost;

	@ConfigProperty(name = "config.app.local.asmgsm.file.path")
	private String localAsmGsmDir;

	@ConfigProperty(name = "config.app.ssh.asmgsm.file.path")
	private String remoteAsmGsmDir;

	@ConfigProperty(name = "config.app.contract.source.ssh.host")
	private String contractSourceHost;

	@ConfigProperty(name = "config.app.contract.source.ssh.user")
	private String contractSourceUserName;

	@ConfigProperty(name = "config.app.contract.source.ssh.password")
	private String contractSourcePassword;

	@ConfigProperty(name = "config.app.contract.source.ssh.port")
	private int contractSourcePort;

	@ConfigProperty(name = "config.app.contract.source.ssh.directory")
	private String contractSourcePath;

	@ConfigProperty(name = "config.app.contract.dest.ssh.directory")
	private String contractDestPath;
}
