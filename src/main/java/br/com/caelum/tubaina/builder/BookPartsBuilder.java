package br.com.caelum.tubaina.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import br.com.caelum.tubaina.BookPart;
import br.com.caelum.tubaina.Chapter;

public class BookPartsBuilder {

    private final Logger LOG = Logger.getLogger(BookPartsBuilder.class);
    
    List<BookPart> bookParts;

    public BookPartsBuilder() {
        bookParts = new ArrayList<BookPart>();
    }

    public BookPartsBuilder addPartsFrom(String text) {
        if (containsPartTag(text)) {
            String bookPartTitle = extractPartBookTitle(text);
            bookParts.add(new BookPart(bookPartTitle, true));
            LOG.info("Parsing part: " + bookPartTitle);
        }
        return this;
    }

    public List<BookPart> build() {
        return bookParts;
    }

    public BookPartsBuilder addChaptersToLastAddedPart(List<Chapter> parsedChapters) {
        if (bookParts.isEmpty()) {
            bookParts.add(new BookPart("", false));
        }
        int lastPartIndex = bookParts.size() - 1;
        bookParts.get(lastPartIndex).addAllChapters(parsedChapters);
        return this;
    }

    private String extractPartBookTitle(String text) {
        Pattern bookPartPattern = Pattern.compile("(?i)(?s)(?m)^\\[part(.*?)\\].*?");
        Matcher chapterMatcher = bookPartPattern.matcher(text);
        chapterMatcher.find();
        return chapterMatcher.group(1).trim();
    }

    private boolean containsPartTag(String text) {
        Pattern bookPartPattern = Pattern.compile("(?i)(?s)(?m)^\\[part(.*?)\\].*?");
        Matcher chapterMatcher = bookPartPattern.matcher(text);
        return chapterMatcher.matches();
    }
}
