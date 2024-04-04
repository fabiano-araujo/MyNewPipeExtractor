package org.schabi.newpipe.extractor.services.media_ccc;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.schabi.newpipe.downloader.DownloaderTestImpl;
import org.schabi.newpipe.extractor.NewPipe;
import org.schabi.newpipe.extractor.channel.tabs.ChannelTabExtractor;
import org.schabi.newpipe.extractor.services.media_ccc.extractors.MediaCCCConferenceExtractor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.schabi.newpipe.extractor.ExtractorAsserts.assertContainsImageUrlInImageCollection;
import static org.schabi.newpipe.extractor.ServiceList.MediaCCC;

/**
 * Test {@link MediaCCCConferenceExtractor} and {@link
 * org.schabi.newpipe.extractor.services.media_ccc.extractors.MediaCCCChannelTabExtractor}
 */
public class MediaCCCConferenceExtractorTest {
    public static class FrOSCon2017 {
        private static MediaCCCConferenceExtractor extractor;
        private static ChannelTabExtractor tabExtractor;

        @BeforeAll
        public static void setUpClass() throws Exception {
            NewPipe.init(DownloaderTestImpl.getInstance());
            extractor = (MediaCCCConferenceExtractor) MediaCCC.getChannelExtractor("https://media.ccc.de/c/froscon2017");
            extractor.fetchPage();

            tabExtractor = MediaCCC.getChannelTabExtractor(extractor.getTabs().get(0));
            tabExtractor.fetchPage();
        }

        @Test
        void testName() throws Exception {
            assertEquals("FrOSCon 2017", extractor.getName());
        }

        @Test
        void testGetUrl() throws Exception {
            assertEquals("https://media.ccc.de/c/froscon2017", extractor.getUrl());
        }

        @Test
        void testGetOriginalUrl() throws Exception {
            assertEquals("https://media.ccc.de/c/froscon2017", extractor.getOriginalUrl());
        }

        @Test
        void testGetThumbnails() {
            assertContainsImageUrlInImageCollection(
                    "https://static.media.ccc.de/media/events/froscon/2017/logo.png",
                    extractor.getAvatars());
        }

        @Test
        void testGetInitalPage() throws Exception {
            assertEquals(97, tabExtractor.getInitialPage().getItems().size());
        }
    }

    public static class Oscal2019 {
        private static MediaCCCConferenceExtractor extractor;
        private static ChannelTabExtractor tabExtractor;

        @BeforeAll
        public static void setUpClass() throws Exception {
            NewPipe.init(DownloaderTestImpl.getInstance());
            extractor = (MediaCCCConferenceExtractor) MediaCCC.getChannelExtractor("https://media.ccc.de/c/oscal19");
            extractor.fetchPage();

            tabExtractor = MediaCCC.getChannelTabExtractor(extractor.getTabs().get(0));
            tabExtractor.fetchPage();
        }

        @Test
        void testName() throws Exception {
            assertEquals("Open Source Conference Albania 2019", extractor.getName());
        }

        @Test
        void testGetUrl() throws Exception {
            assertEquals("https://media.ccc.de/c/oscal19", extractor.getUrl());
        }

        @Test
        void testGetOriginalUrl() throws Exception {
            assertEquals("https://media.ccc.de/c/oscal19", extractor.getOriginalUrl());
        }

        @Test
        void testGetThumbnailUrl() {
            assertContainsImageUrlInImageCollection(
                    "https://static.media.ccc.de/media/events/oscal/2019/oscal-19.png",
                    extractor.getAvatars());
        }

        @Test
        void testGetInitalPage() throws Exception {
            assertTrue(tabExtractor.getInitialPage().getItems().size() >= 21);
        }
    }
}
