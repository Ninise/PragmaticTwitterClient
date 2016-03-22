package com.ninise.pragmatictwitterclient.project.mvp.model.pojos;

import com.ninise.pragmatictwitterclient.project.mvp.model.pojos.github.Commit;
import com.ninise.pragmatictwitterclient.project.mvp.model.pojos.github.Pojo;

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