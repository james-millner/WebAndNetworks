package com.jm.service;

import com.jm.domain.Meaning;
import com.jm.domain.MeaningRepository;
import com.jm.domain.Word;
import com.jm.domain.WordRepository;
import net.didion.jwnl.JWNL;
import net.didion.jwnl.JWNLException;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.dictionary.Dictionary;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private MeaningRepository meaningRepository;

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

            logger.info("***" + wordRepository.doesWordExist(word, type) + "***");

            if(!wordRepository.doesWordExist(word,type)) {
                Word newWord = new Word();
                newWord.setSearch(word);
                newWord.setType(type);
                wordRepository.save(newWord);

                getMeaning(response, pos, word);
            }
        } catch (Exception e){
            logger.info("WordNetService - '" + word + "'" + " Can't get that word from WordNet.");
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
        return word.getSenses();
    }

    /**
     * Method to take an array of descriptions supplied with from searching the WordNet dictionary.
     * @param wordMeanings
     * @param type
     */
    public void getMeaning(Synset[] wordMeanings, POS type, String word) {
        Word wordMeaning = wordRepository.findBySearch(word);
        for (Synset sense : wordMeanings)
        {
            Meaning meaning = new Meaning();
            meaning.setWord(wordMeaning);
            meaning.setMeaning(sense.getGloss());
            meaningRepository.save(meaning);
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
