import java.util.Stack;

// Tree implements the binary search tree property

class Tree<T extends Comparable<T>> {
	public Tree(T v) {
		value = v;
		left = null;
		right = null;
	}

	public void insert(T v) {
		if (value.compareTo(v) == 0)
			return;
		if (value.compareTo(v) > 0)
			if (left == null)
				left = new Tree<T>(v);
			else
				left.insert(v);
		else if (value.compareTo(v) < 0)
			if (right == null)
				right = new Tree<T>(v);
			else
				right.insert(v);
	}

	protected T value;
	protected Tree<T> left;
	protected Tree<T> right;
}

class Iter<T extends Comparable<T>> {
	private Stack<Tree<T>> nodeStack;
	public Iter() {
		nodeStack = new Stack<Tree<T>>();
	}

	private void traverseLeft(Tree<T> tree){
		for (Tree<T> current = tree; current != null; current = current.left) {
			nodeStack.push(current);
		}
	}

	public boolean done() {	
		return nodeStack.isEmpty();
	}

	public T next(Tree<T> tree) {
		if (done()){
			traverseLeft(tree);
		}
		
		/* Pop the stack and traverse to the left*/
		if (!done()){
			Tree<T> current = nodeStack.pop();
			for (Tree<T> child = current.right; child != null; child = child.left) {
				nodeStack.push(child);
			}
			return current.value;
		}
		return null; // Stack is Empty
	}
}

public class GenericTreeEquality {
	static <T extends Comparable<T>> boolean equals(Tree<T> tree1, Tree<T> tree2) {
		Iter<T> iteratorOne = new Iter<T>();
		Iter<T> iteratorTwo = new Iter<T>();
		T nextOne = null;
		T nextTwo = null;
		do {
			nextOne = iteratorOne.next(tree1);
			nextTwo = iteratorTwo.next(tree2);	
			if (nextOne == null && nextTwo == null) // Both the trees are null
				return true;
			else if (nextOne == null || nextTwo == null) // Either of the trees is null
				return false;
			else if (nextOne.compareTo(nextTwo) != 0){
				return false;
			}
		} while (!iteratorOne.done() && !iteratorTwo.done());

		// Clean-up : Return false when traversal is complete for ONLY one tree
		boolean firstTreeDone = iteratorOne.done() && !iteratorTwo.done();
		boolean secondTreeDone = !iteratorOne.done() && iteratorTwo.done();
		if (firstTreeDone || secondTreeDone){
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		Tree<Integer> tree1 = new Tree<Integer>(50);
		tree1.insert(70);
		tree1.insert(80);	
		tree1.insert(90);
		tree1.insert(100);
		Tree<Integer> tree2 = new Tree<Integer>(100);
		tree2.insert(90);
		tree2.insert(80);	
		tree2.insert(70);
		tree2.insert(50);
		System.out.println(equals(tree1, tree2));
	}
}

