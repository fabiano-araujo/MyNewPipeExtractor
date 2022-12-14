package org.schabi.newpipe.extractor.services.bandcamp.extractors;

import org.jsoup.nodes.Element;
import org.schabi.newpipe.extractor.comments.CommentsInfoItemExtractor;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.stream.Description;

import java.util.Objects;

public class BandcampCommentsInfoItemExtractor implements CommentsInfoItemExtractor {

    private final Element writing;
    private final String url;

    public BandcampCommentsInfoItemExtractor(final Element writing, final String url) {
        this.writing = writing;
        this.url = url;
    }

    @Override
    public String getName() throws ParsingException {
        return getCommentText().getContent();
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public String getThumbnailUrl() throws ParsingException {
        return writing.getElementsByClass("thumb").attr("src");
    }

    @Override
    public Description getCommentText() throws ParsingException {
        final var text = writing.getElementsByClass("text").stream()
                .filter(Objects::nonNull)
                .map(Element::ownText)
                .findFirst()
                .orElseThrow(() -> new ParsingException("Could not get comment text"));

        return new Description(text, Description.PLAIN_TEXT);
    }

    @Override
    public String getUploaderName() throws ParsingException {
        return writing.getElementsByClass("name").stream()
                .filter(Objects::nonNull)
                .map(Element::text)
                .findFirst()
                .orElseThrow(() -> new ParsingException("Could not get uploader name"));
    }

    @Override
    public String getUploaderAvatarUrl() {
        return writing.getElementsByClass("thumb").attr("src");
    }
}
