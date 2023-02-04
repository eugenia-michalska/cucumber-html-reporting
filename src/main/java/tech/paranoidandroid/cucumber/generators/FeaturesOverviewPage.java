package tech.paranoidandroid.cucumber.generators;

import tech.paranoidandroid.cucumber.Configuration;
import tech.paranoidandroid.cucumber.ReportBuilder;
import tech.paranoidandroid.cucumber.ReportResult;
import tech.paranoidandroid.cucumber.presentation.PresentationMode;

public class FeaturesOverviewPage extends AbstractPage {

    public static final String WEB_PAGE = ReportBuilder.HOME_PAGE;

    public FeaturesOverviewPage(ReportResult reportResult, Configuration configuration) {
        super(reportResult, "overviewFeatures.vm", configuration);
    }

    @Override
    public String getWebPage() {
        return WEB_PAGE;
    }

    @Override
    public void prepareReport() {
        context.put("all_features", reportResult.getAllFeatures());
        context.put("report_summary", reportResult.getFeatureReport());

        context.put("parallel_testing", configuration.containsPresentationMode(PresentationMode.PARALLEL_TESTING));
        context.put("classifications", configuration.getClassifications());
    }
}
