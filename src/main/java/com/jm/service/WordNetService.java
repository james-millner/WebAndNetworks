package com.jm.service;

import net.didion.jwnl.*;
import net.didion.jwnl.data.*;
import net.didion.jwnl.dictionary.Dictionary;
import org.springframework.stereotype.Service;
import java.io.InputStream;

/**
 * com.jm.service.WordNetService Class will...
 * Created by James Millner on 15/11/2016 at 15:26.
 */
@Service
public class WordNetService {


    public void go() {
        initWordNet();
        Dictionary dictionary = Dictionary.getInstance();

        try {
            IndexWord word = dictionary.lookupIndexWord(POS.NOUN, "wing");
            System.out.println("Senses of the word 'wing':");
            Synset[] senses = word.getSenses();

            for (int i=0; i<senses.length; i++) {
                Synset sense = senses[i];
                System.out.println((i+1) + ". " + sense.getGloss());
                Pointer[] holo = sense.getPointers(PointerType.PART_HOLONYM);
                for (int j=0; j<holo.length; j++) {
                    Synset synset = (Synset) (holo[j].getTarget());
                    Word synsetWord = synset.getWord(0);
                    System.out.print("  -part-of-> " + synsetWord.getLemma());
                    System.out.println(" = " + synset.getGloss());
                }
            }

        } catch (Exception a){
            a.printStackTrace();
        }
    }

    public void initWordNet() {
        try {
            InputStream in = getClass().getResourceAsStream("/static/file_properties.xml");
            JWNL.initialize(in);
            JWNL.getResourceBundle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
