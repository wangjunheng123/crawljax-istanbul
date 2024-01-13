package com.crawljax.examples;

import com.crawljax.browser.EmbeddedBrowser;
import com.crawljax.core.CrawljaxRunner;
import com.crawljax.core.configuration.BrowserConfiguration;
import com.crawljax.core.configuration.CrawlRules;
import com.crawljax.core.configuration.CrawljaxConfiguration;
import com.crawljax.plugins.crawloverview.CrawlOverview;
import com.crawljax.stateabstractions.dom.RTEDStateVertexFactory;
import com.crawljax.stateabstractions.visual.PDiffStateVertexFactory;

import java.util.concurrent.TimeUnit;

import java.util.concurrent.TimeUnit;

public class RunCrawler {
    private static final long WAIT_TIME_AFTER_EVENT = 500;
    private static final long WAIT_TIME_AFTER_RELOAD = 500;
    private static final String URL = "http://localhost:4000";
    public static void main(String[] args) throws Exception {


        CrawljaxConfiguration.CrawljaxConfigurationBuilder builder = CrawljaxConfiguration.builderFor(URL);
//      1. set crawl rules
        builder.crawlRules().setFormFillMode(CrawlRules.FormFillMode.RANDOM);
        builder.crawlRules().clickDefaultElements();
        builder.crawlRules().crawlHiddenAnchors(true);
        builder.crawlRules().crawlFrames(false);
        builder.crawlRules().clickElementsInRandomOrder(false);

        builder.setUnlimitedStates();
//      3. set max run time
        builder.setMaximumRunTime(5, TimeUnit.MINUTES);
//        builder.setUnlimitedRuntime();
//      4. set crawl depth
        builder.setUnlimitedCrawlDepth();
//      5. setup abstract function to be used
//        builder.setStateVertexFactory(new PDiffStateVertexFactory(0.038071336346029695));
        builder.setStateVertexFactory(new RTEDStateVertexFactory());
        builder.crawlRules().waitAfterReloadUrl(WAIT_TIME_AFTER_RELOAD, TimeUnit.MILLISECONDS);
        builder.crawlRules().waitAfterEvent(WAIT_TIME_AFTER_EVENT, TimeUnit.MILLISECONDS);
//      7. choose browser
        builder.setBrowserConfig(new BrowserConfiguration(EmbeddedBrowser.BrowserType.CHROME, 1));

        builder.addPlugin(new CrawlOverview());
//      9. build crawler
        CrawljaxRunner crawljax = new CrawljaxRunner(builder.build());
//      10. run crawler
        crawljax.call();


    }
}
