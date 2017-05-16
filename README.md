# multi-wiki

This corpus contains parallel aligned sentences extracted from Wikipedia in languages: Indonesian, Malay, Filipino, Vietnamese, English.


# Building corpora

    1. Extracting parallel titles
          Follow the procedures
          https://github.com/clab/wikipedia-parallel-titles
          
    2. Crawl articles using the title pairs
    
    3. Preprocessing: split sentences, word tokenization
    
    4. Sentence alignment: using Microsoft Sentence Aligner [Moore, 2002]
    
        https://www.microsoft.com/en-us/download/details.aspx?id=52608&from=http%3A%2F%2Fresearch.microsoft.com%2Fen-us%2Fdownloads%2Faafd5dcf-4dcc-49b2-8a22-f7055113e656%2F
        
        
    5. Truecase, clean
        

# Bilingual Parallel Corpus
Language 1 | Language 2 |  Sentences
------------ | ------------- | -------------
Indonesian | English | 234,380
Indonesian | Filipino | 9,952
Indonesian | Malay | 83,557
Indonesian | Vietnamese | 76,863
Malay | English | 198,087
Malay | Filipino | 4,919
Malay | Vietnamese | 55,613
Filipino | English | 22,758
Filipino | Vietnamese | 10,418
Vietnamese | English | 408,552


# Monolingual Corpus

Language | Sentences
------------ | -------------
Indonesian | 1,478,986
Malay | 596,097
Filipino | 682,939
Vietnamese | 1,862,599

# Citation
The corpus is introduced in this paper:

"Multi-Wiki: A Multilingual Parallel Corpus for Southeast AsianLanguages"


# References

[1] Sentence alignment:
Robert C. Moore (2002): Fast and Accurate Sentence Alignment of Bilingual Corpora, Machine Translation: From Research to Real Users, 5th Conference of the Association for Machine Translation in the Americas, AMTA 2002 Tiburon, CA, USA, October 6-12, 2002, Proceedings

[2] Extracting parallel titles
https://github.com/clab/wikipedia-parallel-titles
