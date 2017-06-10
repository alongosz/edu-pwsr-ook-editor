package net.longosz.OokEditor;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.StyleSpans;
import org.fxmisc.richtext.StyleSpansBuilder;

import java.util.Collection;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Controller {

    private static final String[] MOVE_PTR = new String[]{
            Pattern.quote("Ook. Ook?"),
            Pattern.quote("Ook? Ook."),
    };

    private static final String[] INCR_DECR = new String[]{
            Pattern.quote("Ook. Ook."),
            Pattern.quote("Ook! Ook!"),
    };

    private static final String[] IO = new String[]{
            Pattern.quote("Ook! Ook."),
            Pattern.quote("Ook. Ook!"),
    };

    private static final String[] JUMP = new String[]{
            Pattern.quote("Ook! Ook?"),
            Pattern.quote("Ook? Ook!"),
    };

    private static final Pattern PATTERN = Pattern.compile(
            "(?<MOVEPTR>" + String.join("|", MOVE_PTR) + ")"
                    + "|(?<INCRDECR>" + String.join("|", INCR_DECR) + ")"
                    + "|(?<IO>" + String.join("|", IO) + ")"
                    + "|(?<JUMP>" + String.join("|", JUMP) + ")"
    );

    Controller(CodeArea codeArea) {
        this.codeArea = codeArea;
    }

    void watchForChanges() {
        codeArea.richChanges().subscribe(change -> {
            codeArea.setStyleSpans(0, computeHighlighting(codeArea.getText()));
        });
        codeArea.replaceText(0, 0, sampleCode);
    }

    private static StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        while (matcher.find()) {
            String styleClass =
                    matcher.group("MOVEPTR") != null ? "moveptr" :
                            matcher.group("INCRDECR") != null ? "incrdecr" :
                                    matcher.group("IO") != null ? "io" :
                                            matcher.group("JUMP") != null ? "jump" : "comment";
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }

    private CodeArea codeArea;

    private static final String sampleCode = String.join("\n", new String[]{
            "Ook. Ook? Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook.",
            "Ook. Ook. Ook. Ook. Ook! Ook? Ook? Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook.",
            "Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook? Ook! Ook! Ook? Ook! Ook? Ook.",
            "Ook! Ook. Ook. Ook? Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook.",
            "Ook. Ook. Ook! Ook? Ook? Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook?",
            "Ook! Ook! Ook? Ook! Ook? Ook. Ook. Ook. Ook! Ook. Ook. Ook. Ook. Ook. Ook. Ook.",
            "Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook! Ook. Ook! Ook. Ook. Ook. Ook. Ook.",
            "Ook. Ook. Ook! Ook. Ook. Ook? Ook. Ook? Ook. Ook? Ook. Ook. Ook. Ook. Ook. Ook.",
            "Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook! Ook? Ook? Ook. Ook. Ook.",
            "Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook? Ook! Ook! Ook? Ook! Ook? Ook. Ook! Ook.",
            "Ook. Ook? Ook. Ook? Ook. Ook? Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook.",
            "Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook! Ook? Ook? Ook. Ook. Ook.",
            "Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook. Ook.",
            "Ook. Ook? Ook! Ook! Ook? Ook! Ook? Ook. Ook! Ook! Ook! Ook! Ook! Ook! Ook! Ook.",
            "Ook? Ook. Ook? Ook. Ook? Ook. Ook? Ook. Ook! Ook. Ook. Ook. Ook. Ook. Ook. Ook.",
            "Ook! Ook. Ook! Ook! Ook! Ook! Ook! Ook! Ook! Ook! Ook! Ook! Ook! Ook! Ook! Ook.",
            "Ook! Ook! Ook! Ook! Ook! Ook! Ook! Ook! Ook! Ook! Ook! Ook! Ook! Ook! Ook! Ook!",
            "Ook! Ook. Ook. Ook? Ook. Ook? Ook. Ook. Ook! Ook."
    });

}
