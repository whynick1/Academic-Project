package information_retrival_hw3;


import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

//Use code from here
//http://stanfordnlp.github.io/CoreNLP/
public class StanfordLemmatizer {
	protected StanfordCoreNLP sfcn;
	public StanfordLemmatizer() {
		Properties props;
		props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma");
		this.sfcn = new StanfordCoreNLP(props);
	}

	public List<String> lemmatize(final String documentText) {
		final List<String> lemmas = new LinkedList<String>();
		final Annotation document = new Annotation(documentText);
		this.sfcn.annotate(document);
		final List<CoreMap> sentences = document.get(SentencesAnnotation.class);
		for (final CoreMap sentence : sentences) {
			for (final CoreLabel token : sentence.get(TokensAnnotation.class)) {
				lemmas.add(token.get(LemmaAnnotation.class));
			}
		}

		return lemmas;
	}
}