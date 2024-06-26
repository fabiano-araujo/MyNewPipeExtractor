package org.schabi.newpipe.extractor.stream;

import org.schabi.newpipe.extractor.MediaFormat;
import org.schabi.newpipe.extractor.exceptions.ParsingException;
import org.schabi.newpipe.extractor.services.youtube.ItagItem;
import org.schabi.newpipe.extractor.utils.LocaleCompat;

import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class SubtitlesStream extends Stream {
    private final MediaFormat format;
    private final Locale locale;
    private final boolean autoGenerated;
    private final String code;

    /**
     * Class to build {@link SubtitlesStream} objects.
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;
        private String content;
        private boolean isUrl;
        private DeliveryMethod deliveryMethod = DeliveryMethod.PROGRESSIVE_HTTP;
        @Nullable
        private MediaFormat mediaFormat;
        @Nullable
        private String manifestUrl;
        private String languageCode;
        // Use of the Boolean class instead of the primitive type needed for setter call check
        private Boolean autoGenerated;

        /**
         * Create a new {@link Builder} instance with default values.
         */
        public Builder() {
        }

        /**
         * Set the identifier of the {@link SubtitlesStream}.
         *
         * @param id the identifier of the {@link SubtitlesStream}, which should not be null
         *           (otherwise the fallback to create the identifier will be used when building
         *           the builder)
         * @return this {@link Builder} instance
         */
        public Builder setId(@Nonnull final String id) {
            this.id = id;
            return this;
        }

        /**
         * Set the content of the {@link SubtitlesStream}.
         *
         * <p>
         * It must not be null, and should be non empty.
         * </p>
         *
         * @param content the content of the {@link SubtitlesStream}, which must not be null
         * @param isUrl   whether the content is a URL
         * @return this {@link Builder} instance
         */
        public Builder setContent(@Nonnull final String content,
                                  final boolean isUrl) {
            this.content = content;
            this.isUrl = isUrl;
            return this;
        }

        /**
         * Set the {@link MediaFormat} used by the {@link SubtitlesStream}.
         *
         * <p>
         * It should be one of the subtitles {@link MediaFormat}s ({@link MediaFormat#SRT SRT},
         * {@link MediaFormat#TRANSCRIPT1 TRANSCRIPT1}, {@link MediaFormat#TRANSCRIPT2
         * TRANSCRIPT2}, {@link MediaFormat#TRANSCRIPT3 TRANSCRIPT3}, {@link MediaFormat#TTML
         * TTML}, or {@link MediaFormat#VTT VTT}) but can be {@code null} if the media format could
         * not be determined.
         * </p>
         *
         * <p>
         * The default value is {@code null}.
         * </p>
         *
         * @param mediaFormat the {@link MediaFormat} of the {@link SubtitlesStream}, which can be
         *                    null
         * @return this {@link Builder} instance
         */
        public Builder setMediaFormat(@Nullable final MediaFormat mediaFormat) {
            this.mediaFormat = mediaFormat;
            return this;
        }

        /**
         * Set the {@link DeliveryMethod} of the {@link SubtitlesStream}.
         *
         * <p>
         * It must not be null.
         * </p>
         *
         * <p>
         * The default delivery method is {@link DeliveryMethod#PROGRESSIVE_HTTP}.
         * </p>
         *
         * @param deliveryMethod the {@link DeliveryMethod} of the {@link SubtitlesStream}, which
         *                       must not be null
         * @return this {@link Builder} instance
         */
        public Builder setDeliveryMethod(@Nonnull final DeliveryMethod deliveryMethod) {
            this.deliveryMethod = deliveryMethod;
            return this;
        }

        /**
         * Sets the URL of the manifest this stream comes from (if applicable, otherwise null).
         *
         * @param manifestUrl the URL of the manifest this stream comes from or {@code null}
         * @return this {@link Builder} instance
         */
        public Builder setManifestUrl(@Nullable final String manifestUrl) {
            this.manifestUrl = manifestUrl;
            return this;
        }

        /**
         * Set the language code of the {@link SubtitlesStream}.
         *
         * <p>
         * It <b>must not be null</b> and should not be an empty string.
         * </p>
         *
         * @param languageCode the language code of the {@link SubtitlesStream}
         * @return this {@link Builder} instance
         */
        public Builder setLanguageCode(@Nonnull final String languageCode) {
            this.languageCode = languageCode;
            return this;
        }

        /**
         * Set whether the subtitles have been auto-generated by the streaming service.
         *
         * @param autoGenerated whether the subtitles have been generated by the streaming
         *                      service
         * @return this {@link Builder} instance
         */
        public Builder setAutoGenerated(final boolean autoGenerated) {
            this.autoGenerated = autoGenerated;
            return this;
        }

        /**
         * Build a {@link SubtitlesStream} using the builder's current values.
         *
         * <p>
         * The content (and so the {@code isUrl} boolean), the language code and the {@code
         * isAutoGenerated} properties must have been set.
         * </p>
         *
         * <p>
         * If no identifier has been set, an identifier will be generated using the language code
         * and the media format suffix, if the media format is known.
         * </p>
         *
         * @return a new {@link SubtitlesStream} using the builder's current values
         * @throws IllegalStateException if {@code id}, {@code content} (and so {@code isUrl}),
         * {@code deliveryMethod}, {@code languageCode} or the {@code isAutogenerated} have been
         * not set, or have been set as {@code null}
         */
        @Nonnull
        public SubtitlesStream build() throws ParsingException {
            if (content == null) {
                throw new IllegalStateException("No valid content was specified. Please specify a "
                        + "valid one with setContent.");
            }

            if (deliveryMethod == null) {
                throw new IllegalStateException(
                        "The delivery method of the subtitles stream has been set as null, which "
                                + "is not allowed. Pass a valid one instead with"
                                + "setDeliveryMethod.");
            }

            if (languageCode == null) {
                throw new IllegalStateException("The language code of the subtitles stream has "
                        + "been not set or is null. Make sure you specified an non null language "
                        + "code with setLanguageCode.");
            }

            if (autoGenerated == null) {
                throw new IllegalStateException("The subtitles stream has been not set as an "
                        + "autogenerated subtitles stream or not. Please specify this information "
                        + "with setIsAutoGenerated.");
            }

            if (id == null) {
                id = languageCode + (mediaFormat != null ? "." + mediaFormat.suffix
                        : "");
            }

            return new SubtitlesStream(id, content, isUrl, mediaFormat, deliveryMethod,
                    languageCode, autoGenerated, manifestUrl);
        }
    }

    /**
     * Create a new subtitles stream.
     *
     * @param id             the identifier which uniquely identifies the stream, e.g. for YouTube
     *                       this would be the itag
     * @param content        the content or the URL of the stream, depending on whether isUrl is
     *                       true
     * @param isUrl          whether content is the URL or the actual content of e.g. a DASH
     *                       manifest
     * @param mediaFormat    the {@link MediaFormat} used by the stream
     * @param deliveryMethod the {@link DeliveryMethod} of the stream
     * @param languageCode   the language code of the stream
     * @param autoGenerated  whether the subtitles are auto-generated by the streaming service
     * @param manifestUrl    the URL of the manifest this stream comes from (if applicable,
     *                       otherwise null)
     */
    @SuppressWarnings("checkstyle:ParameterNumber")
    private SubtitlesStream(@Nonnull final String id,
                            @Nonnull final String content,
                            final boolean isUrl,
                            @Nullable final MediaFormat mediaFormat,
                            @Nonnull final DeliveryMethod deliveryMethod,
                            @Nonnull final String languageCode,
                            final boolean autoGenerated,
                            @Nullable final String manifestUrl) throws ParsingException {
        super(id, content, isUrl, mediaFormat, deliveryMethod, manifestUrl);
        this.locale = LocaleCompat.forLanguageTag(languageCode).orElseThrow(
                () -> new ParsingException(
                        "not a valid locale language code: " + languageCode));
        this.code = languageCode;
        this.format = mediaFormat;
        this.autoGenerated = autoGenerated;
    }

    /**
     * Get the extension of the subtitles.
     *
     * @return the extension of the subtitles
     */
    public String getExtension() {
        return format.suffix;
    }

    /**
     * Return whether if the subtitles are auto-generated.
     * <p>
     * Some streaming services can generate subtitles for their contents, like YouTube.
     * </p>
     *
     * @return {@code true} if the subtitles are auto-generated, {@code false} otherwise
     */
    public boolean isAutoGenerated() {
        return autoGenerated;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equalStats(final Stream cmp) {
        return super.equalStats(cmp)
                && cmp instanceof SubtitlesStream
                && code.equals(((SubtitlesStream) cmp).code)
                && autoGenerated == ((SubtitlesStream) cmp).autoGenerated;
    }

    /**
     * Get the display language name of the subtitles.
     *
     * @return the display language name of the subtitles
     */
    public String getDisplayLanguageName() {
        return locale.getDisplayName(locale);
    }

    /**
     * Get the language tag of the subtitles.
     *
     * @return the language tag of the subtitles
     */
    public String getLanguageTag() {
        return code;
    }

    /**
     * Get the {@link Locale locale} of the subtitles.
     *
     * @return the {@link Locale locale} of the subtitles
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * No subtitles which are currently extracted use an {@link ItagItem}, so {@code null} is
     * returned by this method.
     *
     * @return {@code null}
     */
    @Nullable
    @Override
    public ItagItem getItagItem() {
        return null;
    }
}
