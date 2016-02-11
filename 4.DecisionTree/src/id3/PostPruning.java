package id3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import id3.ID3;

public class PostPruning {
	Node bestDTree;
	Node copyDTree = null;
	List<ArrayList<Boolean>> valiLookUpTable;
	double accuracy;
	int L, K, N;
	public PostPruning(Node root, int attributeNumber, int L, int K, List<ArrayList<Boolean>> valiLookUpTable, double accuracy)
	{
		this.L = L;
		this.K = K;
		this.N = attributeNumber;
		this.accuracy = accuracy;
		this.bestDTree = root;
		this.valiLookUpTable = valiLookUpTable;
	}
	
	public void pruning(Node node)
	{
		//pruning process
		for (int i = 1; i < L; i++)
		{
			//successfully copy the DTree
			copyDTree = copy(node, copyDTree, null);
			Random rd = new Random();
			int M = 0;
			while (M == 0)
				M = rd.nextInt(K);
			for (int j = 1; j < M; j++)
			{
				int P = 0;
				while (P == 0)
					P = rd.nextInt(N);
				replace(copyDTree, P);
			}
			if (betterAccuracy(copyDTree))
				bestDTree = copyDTree;
		}
		System.out.println("The best accuracy after Post Pruning is: " + accuracy);
	}
	
	private boolean betterAccuracy(Node nodeCopy) {
		ID3 id3 = new ID3();
		ID3.root = nodeCopy;
		//produce predicted result of vali_set.csv
		List<Boolean> predictions = id3.classify(valiLookUpTable);
//		ID3.PrintTree(ID3.root, 0);
		double newAccuracy = id3.computeAccuracy(predictions, valiLookUpTable);
//		System.out.println("Acurracy with Post Pruning algorithm is: " + newAccuracy);
		if (newAccuracy > this.accuracy)
		{
			System.out.println("find better DTree with accuracy = " + newAccuracy);
			this.accuracy = newAccuracy;
			return true;	
		}
		return false;
	}

	//p means pth node will be replaced
	private void replace(Node nodeCopy, int p) {
		//found pth node
		Queue<Node> q = new LinkedList<Node>();
		q.add(nodeCopy);
		int count = 1;
		while (q.size() > 0)
		{
			Node tempNode = q.poll();
			if (count == p)
			{
				tempNode.predictedLabel = tempNode.calculateLable();
				tempNode.children[0] = null;
				tempNode.children[1] = null;
				break;
			}
			else
			{
				count++;
				//node has left child && left child-node is non-leaf node
				if (tempNode.children[0] != null && tempNode.children[0].predictedLabel == -1)
					q.add(tempNode.children[0]);
				if (tempNode.children[1] != null && tempNode.children[1].predictedLabel == -1)
					q.add(tempNode.children[1]);
			}
		}
	}

	public Node copy(Node source, Node dest, Node parent)
	{
		dest = new Node(parent, source.lookUpTable, source.remainAttributes, source.predictedLabel);
		dest.attributeName = source.attributeName;
		if(source.children[0] != null)
		{
			dest.children[0] = copy(source.children[0], dest.children[0], dest);
		}
		if(source.children[1] != null)
		{
			dest.children[1] = copy(source.children[1], dest.children[1], dest);
		}	
		return dest;
	}
}










