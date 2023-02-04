package tech.paranoidandroid.cucumber.json.support;

import tech.paranoidandroid.cucumber.json.Match;
import tech.paranoidandroid.cucumber.json.Output;
import tech.paranoidandroid.cucumber.json.Result;

/**
 * Ensures that class delivers method for counting results and matches.
 * 
 * @author Damian Szczepanik (damianszczepanik@github)
 *
 */
public interface Resultsable {

    Result getResult();

    Match getMatch();

    Output[] getOutputs();
}
