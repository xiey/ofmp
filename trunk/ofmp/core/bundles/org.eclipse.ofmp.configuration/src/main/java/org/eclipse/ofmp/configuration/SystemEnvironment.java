package org.eclipse.ofmp.configuration;

import java.io.File;

public class SystemEnvironment
{
    public static final File BASE_DIR = new File(System.getProperty("ofmp.base.dir", "./"));
    public static final File CONF_DIR = new File(BASE_DIR, "configuration");
}
