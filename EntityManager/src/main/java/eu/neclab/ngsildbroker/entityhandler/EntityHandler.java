package eu.neclab.ngsildbroker.entityhandler;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
//@Import(WebSecurityConfiguration.class)
public class EntityHandler implements QuarkusApplication {
	public static void main(String[] args) {
		//System.setProperty("org.apache.tomcat.util.buf.UDecoder.ALLOW_ENCODED_SLASH", "true");
		Quarkus.run(EntityHandler.class, args);
	}
	@Override
	public int run(String... args) throws Exception {
		// TODO Auto-generated method stub
		return 0;
	}
}