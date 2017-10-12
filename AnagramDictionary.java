/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class AnagramDictionary {

    private int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private ArrayList<String> wordList = new ArrayList<>();
    private HashSet<String> wordSet = new HashSet<>();
    private HashMap<String, ArrayList> lettersToWord = new HashMap<>();


    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordSet.add(word);
            wordList.add(word);


            if(lettersToWord.containsKey((word))) {
                ArrayList<String> temp = new ArrayList<>();
                temp = lettersToWord.get(sortLetters(word));
                temp.add(word);
                lettersToWord.put(sortLetters(word),temp);
            }else{
                ArrayList<String> temp = new ArrayList<>();
                temp.add(word);
                lettersToWord.put(sortLetters(word),temp);
            }
        }
        Log.d("ltw", lettersToWord.toString());
    }

    public boolean isGoodWord(String word, String base) {

        if(word.contains(base)){
            return false;
        }
        if(wordSet.contains(word)){
            return true;
        }
        return false;
    }

    public String sortLetters(String word){
        char[] c = word.toCharArray();
        Arrays.sort(c);
        String ans = new String(c);
        return ans;
    }

    public List<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        for (String i: wordSet){
            if(i.length()==targetWord.length()){
                if(sortLetters(targetWord).equals(sortLetters(i)) && !(targetWord.equals(i))){
                    result.add(i);
                }
            }
        }
        return result;
        //return lettersToWord.get(sortLetters(targetWord));
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        //char letter = 'a';
        //char[] alphabet = alphabets.toCharArray();
        for(char i='a'; i<='z'; i++){
            if(lettersToWord.containsKey(sortLetters(word+i))){
                ArrayList<String> temp = lettersToWord.get(sortLetters(word+i));
                Log.d("test",temp.toString());
                for(String j: temp){
                    if(isGoodWord(j,word)){
                        result.add(j);
                    }
                }
            }
        }
        return result;
    }

    public String pickGoodStarterWord() {
        int index;
        String go;
        while(true){
            index = random.nextInt(wordList.size());
            go = wordList.get(index);
            List<String> anagram = getAnagramsWithOneMoreLetter(go);
            if(anagram.size() >=MIN_NUM_ANAGRAMS){
                break;
            }
        };
        MIN_NUM_ANAGRAMS++;
        return go;
    }
}
