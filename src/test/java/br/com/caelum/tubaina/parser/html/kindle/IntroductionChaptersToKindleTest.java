package br.com.caelum.tubaina.parser.html.kindle;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import br.com.caelum.tubaina.Chapter;
import br.com.caelum.tubaina.SectionsManager;
import br.com.caelum.tubaina.TubainaBuilder;
import br.com.caelum.tubaina.builder.ChapterBuilder;
import br.com.caelum.tubaina.io.KindleResourceManipulatorFactory;
import br.com.caelum.tubaina.io.ResourceManipulatorFactory;
import br.com.caelum.tubaina.io.TubainaHtmlDir;
import br.com.caelum.tubaina.io.TubainaKindleIO;
import br.com.caelum.tubaina.parser.Parser;
import br.com.caelum.tubaina.parser.RegexConfigurator;
import br.com.caelum.tubaina.parser.html.desktop.HtmlParser;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;

public class IntroductionChaptersToKindleTest {

    private IntroductionChaptersToKindle introductionChaptersToKindle;

    @Before
    public void setUp() throws IOException {
        Configuration cfg = new Configuration();
        File templateDir = new File(TubainaBuilder.DEFAULT_TEMPLATE_DIR, "kindle/");
        cfg.setDirectoryForTemplateLoading(templateDir);
        cfg.setObjectWrapper(new BeansWrapper());

        Parser parser = new HtmlParser(new RegexConfigurator().read("/regex.properties",
                "/html.properties"));
        
        ResourceManipulatorFactory kindleResourceManipulatorFactory = new KindleResourceManipulatorFactory();
        File temp = new File("tmp");
        TubainaHtmlDir bookRoot = new TubainaKindleIO(templateDir, kindleResourceManipulatorFactory)
                .createTubainaDir(temp);
        introductionChaptersToKindle = new IntroductionChaptersToKindle(parser, cfg, bookRoot);
    }

    @Test
    public void shouldGenerateIntroductionChapters() throws Exception {
        Chapter preface = new ChapterBuilder("preface", "label",  "preface text", "", 0, true, new SectionsManager()).build();
        new KindleModule().inject(preface);
        String introductionChapters = introductionChaptersToKindle.generateIntroductionChapters(
                Arrays.asList(preface)).toString();
        assertTrue(introductionChapters.contains("<h2>preface</h2>"));
        assertTrue(introductionChapters.contains("preface text"));
    }

}
