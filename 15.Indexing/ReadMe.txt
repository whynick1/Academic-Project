[README]

I have successfully achieve all the functions including extra ones except display df for “NASA” which worth 10 points. So I should have additional 90 points.

How to compile and run the Program:

1) Login with ssh: ssh hxw150830@cs6360.utdallas.edu 

2) Copy file “Indexing2" to your directory

3) source /usr/local/corenlp350/classpath.sh

4) navigate to the “Indexing/src/information_retrival_hw2”: cd src/information_retrival_hw2

5) Compile using the command:
javac -classpath "../../libs/slf4j-api.jar:../../libs/slf4j-simple.jar:../../libs/commons-lang3-3.4.jar:/usr/local/corenlp341/stanford-corenlp-3.4.1-models.jar:/usr/local/corenlp341/stanford-corenlp-3.4.1.jar" *.java

6) navigate to “Indexing/src”: cd ..

7) Run using the command:
java -cp "../libs/*:/usr/local/corenlp341/stanford-corenlp-3.4.1-models.jar:/usr/local/corenlp341/stanford-corenlp-3.4.1.jar:" information_retrival_hw2/Main


8) Input 2 file path:
“Please input Cranfield directory path:”: [input Cranfield file path]
	
“Please input stopwords file path:”: [input stopwords file path]

9) The output file are created in “Indexing/src”, check using the command
	cat index_v1.uncompressed
	cat index_v1.compressed
	cat index_v1.compressed_dictionary	

	cat index_v2.uncompressed
	cat index_v2.compressed
	cat index_v2.compressed_dictionary

10) I am using stanford-corenlp-version3.4.1 for lemmatization. here are the related link to it: 
DOWNLOAD: http://stanfordnlp.github.io/CoreNLP/
Javadoc: http://nlp.stanford.edu/nlp/javadoc/javanlp/
Source code: https://github.com/stanfordnlp/CoreNLP

