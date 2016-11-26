package com.jm.service;

import com.jm.domain.WordRepository;
import opennlp.tools.cmdline.PerformanceMonitor;
import opennlp.tools.cmdline.postag.POSModelLoader;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSSample;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.tokenize.WhitespaceTokenizer;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.StringReader;

/**
 * com.jm.service.OpenNPL Class will allow individual words to be detected from a sentence.
 *
 * Its primary use will be to develop the wordnet within the application.
 *
 * Created by James Millner on 25/11/2016 at 23:45.
 */
@Service
public class OpenNPL {

    private Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private WordNetService wordNetService;

    @Autowired
    private WordRepository wordRepository;

    private static String noun = "NN";
    private static String verb = "VB";
    private static String adjective = "JJ";
    private static String adverb = "RB";

    public void go(String meaning) throws Exception{
        POSModel model = new POSModelLoader().load(new File("C:\\Users\\James\\Documents\\Workspace\\webnetworkservices\\src\\main\\resources\\en-pos-maxent.bin"));
        PerformanceMonitor perfMon = new PerformanceMonitor(System.err, "sent");
        POSTaggerME tagger = new POSTaggerME(model);

        ObjectStream<String> lineStream =
                new PlainTextByLineStream(new StringReader(meaning));

        perfMon.start();
        String line;
        while ((line = lineStream.read()) != null) {

            String whitespaceTokenizerLine[] = WhitespaceTokenizer.INSTANCE.tokenize(line);
            String[] tags = tagger.tag(whitespaceTokenizerLine);

            POSSample sample = new POSSample(whitespaceTokenizerLine, tags);
            logger.info(sample.toString());

            String[] splitSentence = sample.toString().split(" ");
            for(String word : splitSentence) {
                //The word is returned in the format word_TYPE.
                //Split the string into an array whereby the initial index will always be the word, and the second index - the type.
                String[] wordAndType = word.split("_");

                String type = wordAndType[1];
                String detectedWord = wordAndType[0];

                //Some of the meanings supplied from Wordnet include sub text contained in either "" | () along with semi colons.
                //replaceAll removes those occurrences
                detectedWord = detectedWord.replaceAll("\"|;|\\)|\\(|,|\\?", "");
                if(type.equals(noun)){
                    wordNetService.go("noun", detectedWord);
                    logger.info("Noun: " + detectedWord);
                }

                if(type.equals(verb)) {
                    wordNetService.go("verb", detectedWord);
                    logger.info("Verb: " + detectedWord);
                }

                if(type.equals(adjective)) {
                    wordNetService.go("adjective", detectedWord);
                    logger.info("Adjective: " + detectedWord);
                }

                if(type.equals(adverb)) {
                    wordNetService.go("adverb", detectedWord);
                    logger.info("Adverb: " + detectedWord);
                }
            }
            perfMon.incrementCounter();
        }
    }
}
