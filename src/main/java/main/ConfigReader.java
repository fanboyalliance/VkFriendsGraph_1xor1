package main;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class ConfigReader {
    private Logger logger;
    public ConfigReader() {
        logger = LogManager.getLogger(getClass());
    }

    public void read() throws IOException {
        var objectMapper = new ObjectMapper();
        logger.info("Start reading config.json");
        var node = new ObjectMapper().readTree(new File(getClass().getClassLoader().getResource("config.json").getFile()));
        objectMapper.readValue(node.get("vkSettings").toString(), Config.class);
        logger.info("config.json was successful read");
    }
}
