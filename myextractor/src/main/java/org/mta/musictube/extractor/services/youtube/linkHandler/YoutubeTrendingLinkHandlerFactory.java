package org.mta.musictube.extractor.services.youtube.linkHandler;

/*
 * Created by Christian Schabesberger on 12.08.17.
 *
 * Copyright (C) Christian Schabesberger 2018 <chris.schabesberger@mailbox.org>
 * YoutubeTrendingLinkHandlerFactory.java is part of NewPipe.
 *
 * NewPipe is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * NewPipe is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with NewPipe.  If not, see <http://www.gnu.org/licenses/>.
 */

import org.mta.musictube.extractor.utils.ExtractorConstant;
import org.mta.musictube.extractor.utils.Utils;
import org.mta.musictube.extractor.linkhandler.ListLinkHandlerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class YoutubeTrendingLinkHandlerFactory extends ListLinkHandlerFactory {

    public String getUrl(String id, List<String> contentFilters, String sortFilter) {
        return "https://www.youtube.com/feed/trending";
    }

    @Override
    public String getId(String url) {
        return ExtractorConstant.KIOS_TRENDING;
    }

    @Override
    public boolean onAcceptUrl(final String url) {
        URL urlObj;
        try {
            urlObj = Utils.stringToURL(url);
        } catch (MalformedURLException e) {
            return false;
        }

        String urlPath = urlObj.getPath();
        return Utils.isHTTP(urlObj) && (YoutubeParsingHelper.isYoutubeURL(urlObj) || YoutubeParsingHelper.isInvidioURL(urlObj)) && urlPath.equals("/feed/trending");
    }
}
