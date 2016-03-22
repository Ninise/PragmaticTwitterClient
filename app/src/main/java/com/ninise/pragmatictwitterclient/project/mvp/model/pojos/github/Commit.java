
package com.ninise.pragmatictwitterclient.project.mvp.model.pojos.github;

public class Commit {

    private String message;
    private Committer committer;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDateOfCommit() {
        return committer.getDate();
    }

    public void setDateOfCommit(Committer committer) {
        this.committer = committer;
    }
}
