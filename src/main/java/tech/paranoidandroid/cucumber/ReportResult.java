package tech.paranoidandroid.cucumber;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import tech.paranoidandroid.cucumber.generators.OverviewReport;
import tech.paranoidandroid.cucumber.json.Element;
import tech.paranoidandroid.cucumber.json.Feature;
import tech.paranoidandroid.cucumber.json.Match;
import tech.paranoidandroid.cucumber.json.Result;
import tech.paranoidandroid.cucumber.json.Step;
import tech.paranoidandroid.cucumber.json.Tag;
import tech.paranoidandroid.cucumber.json.support.Resultsable;
import tech.paranoidandroid.cucumber.json.support.Status;
import tech.paranoidandroid.cucumber.json.support.StepObject;
import tech.paranoidandroid.cucumber.json.support.TagObject;
import tech.paranoidandroid.cucumber.reducers.ReportFeatureMergerFactory;
import tech.paranoidandroid.cucumber.sorting.SortingFactory;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;

public class ReportResult {

    private final List<Feature> allFeatures = new ArrayList<>();
    private final Map<String, TagObject> allTags = new TreeMap<>();
    private final Map<String, StepObject> allSteps = new TreeMap<>();

    /**
     * Time when this report was created.
     */
    private final String buildTime;
    private final SortingFactory sortingFactory;
    private final ReportFeatureMergerFactory mergerFactory = new ReportFeatureMergerFactory();

    private final OverviewReport featuresReport = new OverviewReport();

    private final OverviewReport tagsReport = new OverviewReport();

    public ReportResult(List<Feature> features, Configuration configuration) {
        buildTime = getCurrentTime();
        sortingFactory = new SortingFactory(configuration.getSortingMethod());

        List<Feature> mergedFeatures = mergerFactory.get(configuration.getReducingMethods()).merge(features);

        for (int i = 0; i < mergedFeatures.size(); i++) {
            mergedFeatures.get(i).setMetaData(i);
            processFeature(mergedFeatures.get(i));
        }
    }

    public List<Feature> getAllFeatures() {
        return sortingFactory.sortFeatures(allFeatures);
    }

    public List<TagObject> getAllTags() {
        return sortingFactory.sortTags(allTags.values());
    }

    public List<StepObject> getAllSteps() {
        return sortingFactory.sortSteps(allSteps.values());
    }

    public Reportable getFeatureReport() {
        return featuresReport;
    }

    public Reportable getTagReport() {
        return tagsReport;
    }

    public String getBuildTime() {
        return buildTime;
    }

    private void processFeature(Feature feature) {
        allFeatures.add(feature);


        for (Element element : feature.getElements()) {
            if (element.isScenario()) {
                featuresReport.incScenarioFor(element.getStatus());

                // all feature tags should be linked with scenario
                for (Tag tag : feature.getTags()) {
                    processTag(tag, element, feature.getStatus());
                }
            }

            // all element tags should be linked with element
            for (Tag tag : element.getTags()) {
                // don't count tag for feature if was already counted for element
                if (!ArrayUtils.contains(feature.getTags(), tag)) {
                    processTag(tag, element, element.getStatus());
                }
            }

            Step[] steps = element.getSteps();
            for (Step step : steps) {
                featuresReport.incStepsFor(step.getResult().getStatus());
                featuresReport.incDurationBy(step.getDuration());
            }
            countSteps(steps);

            countSteps(element.getBefore());
            countSteps(element.getAfter());
        }

        featuresReport.incFeaturesFor(feature.getStatus());
        tagsReport.incFeaturesFor(feature.getStatus());
    }

    private void processTag(Tag tag, Element element, Status status) {

        TagObject tagObject = addTagObject(tag.getName());

        // if this element was not added by feature tag, add it as element tag
        if (tagObject.addElement(element)) {
            tagsReport.incScenarioFor(status);

            Step[] steps = element.getSteps();
            for (Step step : steps) {
                tagsReport.incStepsFor(step.getResult().getStatus());
                tagsReport.incDurationBy(step.getDuration());
            }
        }
    }

    private void countSteps(Resultsable[] steps) {
        for (Resultsable step : steps) {

            Match match = step.getMatch();
            // no match = could not find method that was matched to this step -> status is missing
            if (match != null) {
                String methodName = match.getLocation();
                // location is missing so there is no way to identify step
                if (StringUtils.isNotEmpty(methodName)) {
                    addNewStep(step.getResult(), methodName);
                }
            }
        }
    }

    private void addNewStep(Result result, String methodName) {
        StepObject stepObject = allSteps.get(methodName);
        // if first occurrence of this location add element to the map
        if (stepObject == null) {
            stepObject = new StepObject(methodName);
        }

        // happens that report is not valid - does not contain information about result
        stepObject.addDuration(result.getDuration(), result.getStatus());
        allSteps.put(methodName, stepObject);
    }

    private TagObject addTagObject(String name) {
        TagObject tagObject = allTags.get(name);
        if (tagObject == null) {
            tagObject = new TagObject(name);
            allTags.put(tagObject.getName(), tagObject);
        }
        return tagObject;
    }

    public static String getCurrentTime() {
        return new SimpleDateFormat("dd MMM yyyy, HH:mm").format(new Date());
    }
}
