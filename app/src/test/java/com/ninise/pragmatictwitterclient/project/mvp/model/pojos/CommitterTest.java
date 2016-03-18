package com.ninise.pragmatictwitterclient.project.mvp.model.pojos;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class CommitterTest {

    String date = "2016-03-17 16:39:03";

    @Test
    public void testGetDate() throws Exception {
        Committer committer = new Committer();
        committer.setDate(date);
        Assertions.assertThat(committer.getDate()).isEqualTo(date);
    }

    @Test
    public void testSetDate() throws Exception {
        Committer committer = new Committer();
        committer.setDate(date);
        Assertions.assertThat(committer.getDate()).isEqualTo(date);
    }
}