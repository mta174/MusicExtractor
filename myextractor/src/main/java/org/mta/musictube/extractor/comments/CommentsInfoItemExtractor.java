package org.mta.musictube.extractor.comments;

import org.mta.musictube.extractor.InfoItemExtractor;
import org.mta.musictube.extractor.exceptions.ParsingException;

public interface CommentsInfoItemExtractor extends InfoItemExtractor {
    String getCommentId() throws ParsingException;
    String getCommentText() throws ParsingException;
    String getAuthorName() throws ParsingException;
    String getAuthorThumbnail() throws ParsingException;
    String getAuthorEndpoint() throws ParsingException;
    String getPublishedTime() throws ParsingException;
    Integer getLikeCount() throws ParsingException;
}
