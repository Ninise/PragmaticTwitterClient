
package com.ninise.pragmatictwitterclient.project.mvp.model.pojos.github;

public class Pojo {
    private Commit commit;

    public String getCommit() {
        return commit.getMessage();
    }

    public void setCommit(Commit commit) {
        this.commit = commit;
    }

    public String getDateOfCommit() {
        return commit.getDateOfCommit();
    }

}
