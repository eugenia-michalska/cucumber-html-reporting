package tech.paranoidandroid.cucumber.generators;

import tech.paranoidandroid.cucumber.Configuration;
import tech.paranoidandroid.cucumber.ReportResult;
import tech.paranoidandroid.cucumber.json.Feature;

public class FeatureReportPage extends AbstractPage {

    private final Feature feature;

    public FeatureReportPage(ReportResult reportResult, Configuration configuration, Feature feature) {
        super(reportResult, "reportFeature.vm", configuration);
        this.feature = feature;
    }

    @Override
    public String getWebPage() {
        return feature.getReportFileName();
    }

    @Override
    public void prepareReport() {
        context.put("feature", feature);
    }

}
