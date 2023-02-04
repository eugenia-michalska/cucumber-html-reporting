package tech.paranoidandroid.cucumber.generators;

import java.util.List;

import tech.paranoidandroid.cucumber.Configuration;
import tech.paranoidandroid.cucumber.ReportResult;
import tech.paranoidandroid.cucumber.json.support.StepObject;
import tech.paranoidandroid.cucumber.util.Util;

/**
 * Presents details about how long steps are executed (adds the same steps and presents sum).
 * 
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class StepsOverviewPage extends AbstractPage {

    public static final String WEB_PAGE = "overview-steps.html";

    public StepsOverviewPage(ReportResult reportResult, Configuration configuration) {
        super(reportResult, "overviewSteps.vm", configuration);
    }

    @Override
    public String getWebPage() {
        return WEB_PAGE;
    }

    @Override
    public void prepareReport() {
        context.put("all_steps", reportResult.getAllSteps());

        int allOccurrences = 0;
        long allDurations = 0;
        for (StepObject stepObject : reportResult.getAllSteps()) {
            allOccurrences += stepObject.getTotalOccurrences();
            allDurations += stepObject.getDuration();
        }
        context.put("all_occurrences", allOccurrences);
        context.put("all_durations", Util.formatDuration(allDurations));
        // make sure it does not divide by 0 - may happens if there is no step at all or all results have 0 ms durations
        context.put("all_max_duration", Util.formatDuration(maxDurationOf(reportResult.getAllSteps())));
        long average = allDurations / (allOccurrences == 0 ? 1 : allOccurrences);
        context.put("all_average_duration", Util.formatDuration(average));
    }

    private long maxDurationOf(List<StepObject> steps) {
        long maxDuration = 0;
        for (StepObject step : steps) {
            if (step.getDuration() > maxDuration) {
                maxDuration = step.getDuration();
            }
        }
        return maxDuration;
    }
}
