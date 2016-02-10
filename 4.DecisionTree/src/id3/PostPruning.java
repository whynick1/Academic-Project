package id3;

import java.util.Random;

public class PostPruning {
	Node bestDTree;
	int L, K, N;
	public PostPruning(Node root, int attributeNumber, int L, int K)
	{
		this.L = L;
		this.K = K;
		this.N = attributeNumber;
		this.bestDTree = root;
	}
	
	public Node pruning(Node node)
	{
		//pruning process
		for (int i = 1; i < L; i++)
		{
			//copy a DTree
			Node nodeCopy = copyTree(node);
			Random rd = new Random();
			int M = 0;
			while (M == 0)
				M = rd.nextInt(K);
			for (int j = 1; j < M; j++)
			{
				int P = 0;
				while (P == 0)
					P = rd.nextInt(N);
				replace(nodeCopy, P);
			}
			if (betterAccuracy(nodeCopy))
				bestDTree = nodeCopy;
		}
		return bestDTree;
	}
	
	private boolean betterAccuracy(Node nodeCopy) {
		// TODO Auto-generated method stub
		return false;
	}

	private void replace(Node nodeCopy, int p) {
		// TODO Auto-generated method stub
		
	}

	public Node copyTree(Node node) {
		//copy tree
		Node copyTree = null;
		return copyTree; 
	}
}
