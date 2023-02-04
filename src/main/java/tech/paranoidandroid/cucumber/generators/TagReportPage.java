package tech.paranoidandroid.cucumber.generators;

import tech.paranoidandroid.cucumber.Configuration;
import tech.paranoidandroid.cucumber.ReportResult;
import tech.paranoidandroid.cucumber.json.support.TagObject;

public class TagReportPage extends AbstractPage {

    private final TagObject tagObject;

    public TagReportPage(ReportResult reportResult, Configuration configuration, TagObject tagObject) {
        super(reportResult, "reportTag.vm", configuration);
        this.tagObject = tagObject;
    }

    @Override
    public String getWebPage() {
        return tagObject.getReportFileName();
    }

    @Override
    public void prepareReport() {
        context.put("tag", tagObject);
    }

}
