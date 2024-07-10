package org.schabi.newpipe.extractor.services;

import org.junit.jupiter.api.Test;

public interface BaseChannelExtractorTest extends BaseExtractorTest {
    @Test
    void testDescription() throws Exception;
    @Test
    void testAvatars() throws Exception;
    @Test
    void testBanners() throws Exception;
    @Test
    void testFeedUrl() throws Exception;
    @Test
    void testSubscriberCount() throws Exception;
    @Test
    void testVerified() throws Exception;
    @Test
    void testTabs() throws Exception;
    @Test
    void testTags() throws Exception;
}
