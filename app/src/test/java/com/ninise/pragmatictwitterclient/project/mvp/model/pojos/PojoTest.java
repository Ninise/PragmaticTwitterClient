package com.ninise.pragmatictwitterclient.project.mvp.model.pojos;

import org.assertj.core.api.Assertions;
import org.junit.Test;


public class PojoTest {

    Pojo mPojo = new Pojo();

    @Test
    public void testGetCommit() throws Exception {
        mPojo.setCommit(new Commit());
        Assertions.assertThat(mPojo.getCommit()).isNull();
    }

    @Test
    public void testSetCommit() throws Exception {
        mPojo.setCommit(new Commit());
        Assertions.assertThat(mPojo.getCommit()).isNull();
    }
}