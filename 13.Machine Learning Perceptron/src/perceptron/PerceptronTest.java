
package perceptron;

/**
 *
 * @author Orhan Demirel
 */
public class PerceptronTest {

	static ReadFile train;
	static ReadFile test;
	static Perceptron p;
	
    public static void main(String[] args) {
    	train = new ReadFile();
    	train.loadSet("promoters_data/training.new");
    	test = new ReadFile();
    	test.loadSet("promoters_data/validation.new");
    	p = new Perceptron();
    	p.getAttributeFreq(train.table);

	    p.Train(train.table, train.labelTable, 0.0, 0.05, 10000);
	    System.out.println("Accuracy is: " + p.getAccuracy(test.table, test.labelTable));
    }
//    public static void main(String[] args) {
//        Perceptron p = new Perceptron();
//        double inputs[][] = {{0,0},{0,1},{1,0},{1,1}};
//        int outputs[] = {0,0,0,1};
//
//        p.Train(inputs, outputs,0.2, 0.1, 200);
//        System.out.println(p.Output(new double[]{0,0}));
//        System.out.println(p.Output(new double[]{1,0}));
//        System.out.println(p.Output(new double[]{0,1}));
//        System.out.println(p.Output(new double[]{1,1}));
//    }
    
}

