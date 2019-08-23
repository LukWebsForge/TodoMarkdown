package de.lukweb.tomd;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.log.Log4JLogChute;

public class VelocityFactory {

    private static VelocityEngine engine;

    public static VelocityEngine getEngine() {
        if (engine == null) {
            engine = newVelocityEngine();
        }
        return engine;
    }

    // See https://github.com/JetBrains/intellij-community/blob/master/plugins/generate-tostring/src/org/jetbrains/java/generate/velocity/VelocityFactory.java
    private static VelocityEngine newVelocityEngine() {
        ExtendedProperties prop = new ExtendedProperties();
        prop.addProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM_CLASS, Log4JLogChute.class.getName());
        prop.addProperty("runtime.log.logsystem.log4j.category", "TODOMarkdown");
        prop.addProperty(RuntimeConstants.VM_PERM_ALLOW_INLINE_REPLACE_GLOBAL, "true");

        VelocityEngine velocity = new VelocityEngine();
        velocity.setExtendedProperties(prop);
        return velocity;
    }

}
