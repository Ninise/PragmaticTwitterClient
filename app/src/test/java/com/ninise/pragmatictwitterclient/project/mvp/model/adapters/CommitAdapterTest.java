package com.ninise.pragmatictwitterclient.project.mvp.model.adapters;

import com.ninise.pragmatictwitterclient.project.mvp.model.pojos.github.Pojo;

import junit.framework.TestCase;

import org.assertj.core.api.Assertions;

import java.util.Arrays;
import java.util.List;


public class CommitAdapterTest extends TestCase {

    private CommitAdapter mCommitAdapter;

    @Override
    public void setUp() throws Exception {
        List<Pojo> dataSet = Arrays.asList(new Pojo(), new Pojo());
        mCommitAdapter = new CommitAdapter(dataSet);
    }

    public void testOnCreateCommitAdapter() throws Exception {
        Assertions.assertThat(mCommitAdapter).isNotNull();
    }

    public void testGetItemCount() throws Exception {
        Assertions.assertThat(mCommitAdapter.getItemCount()).isNotNull();
    }
}