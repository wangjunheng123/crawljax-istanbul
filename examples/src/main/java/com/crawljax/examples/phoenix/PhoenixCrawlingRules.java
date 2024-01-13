package com.crawljax.examples.phoenix;

import java.util.concurrent.TimeUnit;

import com.crawljax.browser.EmbeddedBrowser;
import com.crawljax.browser.EmbeddedBrowser.BrowserType;
import com.crawljax.core.configuration.BrowserConfiguration;
import com.crawljax.core.configuration.CrawlRules;
import com.crawljax.core.configuration.CrawlRules.FormFillMode;
import com.crawljax.core.configuration.CrawljaxConfiguration.CrawljaxConfigurationBuilder;
import com.crawljax.core.configuration.InputSpecification;
import com.crawljax.plugins.crawloverview.CrawlOverview;
import com.crawljax.stateabstractions.visual.PDiffStateVertexFactory;

public class PhoenixCrawlingRules {

	/**
	 * List of crawling rules for the Angular Phonecat application.
	 */
	private static final long WAIT_TIME_AFTER_EVENT = 500;
	private static final long WAIT_TIME_AFTER_RELOAD = 500;
	public static void setCrawlingRules(CrawljaxConfigurationBuilder builder) {

		//      1. set crawl rules
		builder.crawlRules().setFormFillMode(CrawlRules.FormFillMode.RANDOM);
		builder.crawlRules().clickDefaultElements();
		builder.crawlRules().crawlHiddenAnchors(true);
		builder.crawlRules().crawlFrames(false);
		builder.crawlRules().clickElementsInRandomOrder(false);

		builder.setUnlimitedStates();
//      3. set max run time
		builder.setMaximumRunTime(30, TimeUnit.MINUTES);
//        builder.setUnlimitedRuntime();
//      4. set crawl depth
		builder.setUnlimitedCrawlDepth();
//      5. setup abstract function to be used
		builder.setStateVertexFactory(new PDiffStateVertexFactory(0.038071336346029695));
		builder.crawlRules().waitAfterReloadUrl(WAIT_TIME_AFTER_RELOAD, TimeUnit.MILLISECONDS);
		builder.crawlRules().waitAfterEvent(WAIT_TIME_AFTER_EVENT, TimeUnit.MILLISECONDS);
//      7. choose browser
		builder.setBrowserConfig(new BrowserConfiguration(EmbeddedBrowser.BrowserType.CHROME, 1));

		builder.addPlugin(new CrawlOverview());
		/* input data. */
		builder.crawlRules().setInputSpec(PhoenixCrawlingRules.phoenixSpecification());

		/* CrawlOverview. */
		builder.addPlugin(new CrawlOverview());
	}

	/**
	 * List of inputs to crawl the Phonecat application.
	 */
	static InputSpecification phoenixSpecification() {

		InputSpecification inputPhoenix = new InputSpecification();

//		PageKitForms.register(inputDimeshift);
		
		PhoenixForms.login(inputPhoenix);

		return inputPhoenix;
	}

}
