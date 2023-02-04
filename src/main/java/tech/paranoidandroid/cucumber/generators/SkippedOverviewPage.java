package tech.paranoidandroid.cucumber.generators;

import java.util.ArrayList;
import java.util.List;

import tech.paranoidandroid.cucumber.Configuration;
import tech.paranoidandroid.cucumber.ReportResult;
import tech.paranoidandroid.cucumber.json.Element;
import tech.paranoidandroid.cucumber.json.Feature;
import tech.paranoidandroid.cucumber.json.support.Status;

public class SkippedOverviewPage extends AbstractPage {

    public static final String WEB_PAGE = "overview-skipped.html";

    public SkippedOverviewPage(ReportResult reportResult, Configuration configuration) {
        super(reportResult, "overviewSkipped.vm", configuration);
    }

    @Override
    public String getWebPage() {
        return WEB_PAGE;
    }

    @Override
    public void prepareReport() {
        context.put("skipped", collectSkipped());
    }

    private List<Element> collectSkipped() {
        List<Element> skipped = new ArrayList<>();
        for (Feature feature : reportResult.getAllFeatures()) {

            for (Element element : feature.getElements()) {

                if (element.getStatus() == Status.SKIPPED) {
                    skipped.add(element);
                }
            }
        }
        return skipped;
    }
}
