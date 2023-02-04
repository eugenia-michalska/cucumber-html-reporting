import java.io.File;
import java.util.*;

import org.junit.Test;

import tech.paranoidandroid.cucumber.Configuration;
import tech.paranoidandroid.cucumber.ReportBuilder;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class LiveDemoTest {

    @Test
    public void generateDemoReport() {
        File reportOutputDirectory = new File("target/demo");
        List<String> jsonFiles = new ArrayList<>();
        jsonFiles.add("src/test/resources/json/demo.json");

        String buildNumber = "1.0.0";
        String projectName = "Live Demo Project";
        Configuration configuration = new Configuration(reportOutputDirectory, projectName);
        configuration.setBuildNumber(buildNumber);

        // points to the demo trends which is not used for other tests
        configuration.setTrendsStatsFile(new File("target/test-classes/demo-trends.json"));

        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);
        reportBuilder.generateReports();
    }
}
