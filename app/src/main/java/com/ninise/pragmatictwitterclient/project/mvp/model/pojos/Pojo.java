
package com.ninise.pragmatictwitterclient.project.mvp.model.pojos;

public class Pojo {
    private Commit commit;

    /**
     * 
     * @return
     *     The commit
     */
    public String getCommit() {
        return commit.getMessage();
    }

    /**
     * 
     * @param commit
     *     The commit
     */
    public void setCommit(Commit commit) {
        this.commit = commit;
    }

}
