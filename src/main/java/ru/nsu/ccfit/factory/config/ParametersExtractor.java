package ru.nsu.ccfit.factory.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ParametersExtractor {
    private final Properties props = new Properties();
    private final String configFileName = "app.properties";

    public ParametersExtractor() throws IOException {
        props.load(new FileInputStream(getClass().getClassLoader().getResource(configFileName).getPath()));
    }

    public String getParameter(Parameter parameter) {
        return props.getProperty(parameter.name().toUpperCase());
    }
}
