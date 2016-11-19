How to compile and run:
1. To compile the program, import the project named “Tokenization” to IDE, and click ‘run’.

2. Or you can simply open a terminal and input:  
java -jar tokenization.jar to run; 

3. When the program start running, console will display “Please input Cranfield directory path:”

4. Input Cranfield directory to continue. For example: /Users/wanghongyi/Desktop/Cranfield


1. How long the program took to acquire the text characteristics.
Answer : On an average program takes 0.87 second.

2. How the program handles:
A. Upper and lower case words (e.g. "Peo ple", "people", "Apple", "apple");
Answer : convert upper case word to lowercase.

B. Words with dashes (e.g. "1996-97", "middle-class", "30-year", "tean-ager")
Answer : split token using dashes as delimiter.

C. Possessives (e.g. "sheriff's", "university's")
Answer : remove all"'s"

D. Acronyms (e.g., "U.S.", "U.N.")
Ans : removed all ".", like US , UN

3. Major algorithms and data structures.

	- Used DocumentBuilder to extract useful information(omit XML tags) from files

	- Use Scanner to extract each token, and futher process the word (including remove numbers, deal with special sign)

	- Count number of tokens, and put them into HashMap<Token, Frequency>
		
	- Iterator through all words, and count tokens that occur only once
		
	- Meanwhile, use PriorityQueen<Token> as heap to dynamically store top 30 frequent words (In this case, we don't have to sort, which saves lots of time)

	- For Stemming, we use the open source code from http://chianti.ucsd.edu/svn/csplugins/trunk/soc/layla/WordCloudPlugin/trunk/WordClo ud/src/cytoscape/csplugins/wordcloud/Stemmer.java

	- By call Stem s.add() and s.stem(), we get the stemming form

4. The following the program running print information

**********************************************************
1.Acquire text characters take: 871 milliseconds
2.Number of tokens in text collection is: 230847
3.Number of unique tokens is: 8776
4.Number of tokens that occur only once is: 3309
5.30 most frequent tokens are: 
shock: 712
mach: 824
theory: 789
it: 857
which: 975
layer: 1002
results: 885
this: 1081
be: 1272
is: 4114
boundary: 1156
pressure: 1207
as: 1113
from: 1116
number: 973
and: 6677
the: 19453
on: 1944
for: 3493
a: 5987
to: 4563
at: 1834
by: 1756
that: 1570
are: 2429
of: 12717
an: 1389
in: 4651
flow: 1849
with: 2265
6.The average number of tokens per document is: 164

**********************************************************
1.Number of unique stem is: 6070
2.Number of stem that occur only once is: 2246
3.30 most frequent stems are: 
theori: 882
method: 887
effect: 998
which: 975
boundari: 1185
it: 1044
from: 1116
thi: 1081
ar: 2458
that: 1570
be: 1369
pressur: 1382
result: 1087
with: 2265
number: 1347
flow: 2080
as: 1113
to: 4563
of: 12717
for: 3493
a: 5987
in: 4651
by: 1756
at: 1834
on: 2262
layer: 1134
an: 1389
is: 4114
the: 19453
and: 6677
4.The average number of stems per document is: 164