[README]

How to compile and run the Program:

1) Login with ssh: ssh hxw150830@cs6360.utdallas.edu 

2) Copy file "RankedRetrieval2" to your directory

3) source /usr/local/corenlp350/classpath.sh

4) navigate to the RankedRetrieval2/src/information_retrival_hw3”: cd src/information_retrival_hw3

5) Compile using the command:
javac -classpath "../../libs/slf4j-api.jar:../../libs/slf4j-simple.jar:../../libs/commons-lang3-3.4.jar:/usr/local/corenlp341/stanford-corenlp-3.4.1-models.jar:/usr/local/corenlp341/stanford-corenlp-3.4.1.jar" *.java

6) navigate to RankedRetrieval2/src”: cd ..

7) Run using the command:
java -cp "../libs/*:/usr/local/corenlp341/stanford-corenlp-3.4.1-models.jar:/usr/local/corenlp341/stanford-corenlp-3.4.1.jar:" information_retrival_hw3/Main

8) Input 3 file path:
“Please input Cranfield directory path:”: [input Cranfield file path]
	
“Please input stopwords file path:”: [input stopwords file path]
	
“Please input hw3.queries file path:”: [input hw3.queries file path]

9) The output will be printed in terminal as attachecd file 'output.txt'.

10) I am using stanford-corenlp-version3.4.1 for lemmatization. here are the related link to it: 
DOWNLOAD: http://stanfordnlp.github.io/CoreNLP/
Javadoc: http://nlp.stanford.edu/nlp/javadoc/javanlp/
Source code: https://github.com/stanfordnlp/CoreNLP









