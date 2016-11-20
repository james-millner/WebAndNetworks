package com.jm.service;

import net.didion.jwnl.*;
import net.didion.jwnl.data.*;
import net.didion.jwnl.dictionary.Dictionary;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import java.io.InputStream;

/**
 * com.jm.service.WordNetService Class will manage all service queries to JWNL.
 *
 * Created by James Millner on 15/11/2016 at 15:26.
 */
@Service
public class WordNetService {

    private Log logger = LogFactory.getLog(this.getClass());

    private static POS noun = POS.NOUN;
    private static POS verb = POS.VERB;
    private static POS adjective = POS.ADJECTIVE;
    private static POS adverb = POS.ADVERB;

    public void go(String type, String word) {
        initWordNet();

        POS pos;
        if(type.equals("noun"))
            pos = POS.NOUN;
        else if(type.equals("verb"))
            pos = POS.VERB;
        else if(type.equals("adjective"))
            pos = POS.ADJECTIVE;
        else if(type.equals("adverb"))
            pos = POS.ADVERB;
        else
            pos = null;

        try {
            Synset[] response = searchDictionary(pos, word);
            getMeaning(response, pos);
        } catch (Exception e){
            logger.fatal(e);
        }
    }

    /**
     * Method to take a type of word, and a word to then search the JWNL dictionary.
     *
     * If it finds a match return the array of results.
     * @param type
     * @param stringWord
     * @return
     * @throws JWNLException
     */
    public Synset[] searchDictionary(POS type, String stringWord) throws JWNLException {
        Dictionary dictionary = Dictionary.getInstance();

        IndexWord word = dictionary.lookupIndexWord(type, stringWord);
        logger.info("Senses of the word 'door':");
        return word.getSenses();
    }

    /**
     * Method to take an array of descriptions supplied with from searching the WordNet dictionary.
     * @param wordMeanings
     * @param type
     */
    public void getMeaning(Synset[] wordMeanings, POS type) {
        for (Synset sense : wordMeanings)
        {
            System.out.println(type + ": " + sense.getGloss());
        }
    }

    /**
     * Method to initial JWNL from the static file_properties file.
     *
     * Points JWNL to the correct dictionary.
     */
    private void initWordNet() {
        try {
            InputStream in = getClass().getResourceAsStream("/static/file_properties.xml");
            JWNL.initialize(in);
            JWNL.getResourceBundle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
