package tech.paranoidandroid.cucumber.json.support;

import mockit.Deencapsulation;

import tech.paranoidandroid.cucumber.json.Match;
import tech.paranoidandroid.cucumber.json.Output;
import tech.paranoidandroid.cucumber.json.Result;

/**
 * @author Damian Szczepanik (damianszczepanik@github)
 */
public class ResultsableBuilder {

    public static Resultsable[] Resultsable(Status... statuses) {
        Resultsable[] resultsables = new Resultsable[statuses.length];
        for (int i = 0; i < statuses.length; i++) {
            resultsables[i] = buildWith(statuses[i]);
        }
        return resultsables;
    }

    public static Resultsable buildWith(Status status) {
        return new ResultsableMock(status);
    }

    private static class ResultsableMock implements Resultsable {

        private Result result;

        ResultsableMock(Status status) {
            this.result = new Result();
            Deencapsulation.setField(this.result, "status", status);
        }

        @Override
        public Result getResult() {
            return result;
        }

        @Override
        public Match getMatch() {
            return null;
        }

        @Override
        public Output[] getOutputs() {
            return new Output[0];
        }
    }
}
