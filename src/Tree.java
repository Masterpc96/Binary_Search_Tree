/**
 * Created by Michał on 03.05.2017.
 */
public class Tree<T> {
    private Node<T> root;
    private MyComparator<T> comparator = new MyComparator<>();

    public Tree() {
        root = null;
    }

    /*********************************************/
    public boolean addKey(T key) {
        if (exist(key)) return false;
        else if (root == null) {
            root = new Node<>(key);
            return true;
        } else {
            Node<T> actual = root;
            Node<T> parent = null;
            while (actual != null) {
                parent = actual;
                if (comparator.compare(actual.key, key) > 0) {
                    actual = actual.left;
                } else {
                    actual = actual.right;
                }
            }
            if (comparator.compare(parent.key, key) > 0) {
                parent.left = new Node<>(key);
                parent.left.parent = parent;
            } else {
                parent.right = new Node<>(key);
                parent.right.parent = parent;
            }
            return true;
        }
    }

    /*********************************************/

    public T min(Node<T> node) {
        if (node == null) {
            node = root;
        }
        while (node.left != null)
            node = node.left;
        return node.key;
    }

    /*********************************************/

    public T max(Node<T> node) {
        if (node == null) {
            node = root;
        }
        while (node.right != null)
            node = node.right;
        return node.key;
    }

    /*********************************************/

    public Node<T> search(T key) {
        Node<T> actual = root;
        while (actual != null && comparator.compare(actual.key, key) != 0)
            actual = (comparator.compare(actual.key, key) > 0) ? actual.left : actual.right;
        if (actual == null)
            return null;
        return actual;
    }

    /*********************************************/

    private boolean exist(T key) {
        Node<T> actual = root;

        while (actual != null && comparator.compare(actual.key, key) != 0)
            actual = (comparator.compare(actual.key, key) > 0) ? actual.left : actual.right;
        if (actual == null) return false;
        return true;
    }

    /*********************************************/

    public Node<T> successor(T key) throws MyException {
        Node<T> node = this.search(key);
        if (node.right != null) {
            node = node.right;
            while (node.left != null)
                node = node.left;
            return node;
        } else if (node.right == null && node != root && comparator.compare(node.key, this.max(root)) != 0) {
            Node<T> parent = node.parent;
            while (parent != root && comparator.compare(parent.key, node.key) < 0)
                parent = parent.parent;
            return parent;
        } else
            throw new MyException("Not Found Successor");
    }

    /*********************************************/

    public Node<T> predecessor(T key) throws MyException {
        Node<T> node = this.search(key);
        if (node.left != null) {
            node = node.left;
            while (node.right != null)
                node = node.right;
            return node;

        } else if (node.left == null && node != root && comparator.compare(node.key, this.min(root)) != 0) {
            Node<T> parent = node.parent;
            while (parent != root && comparator.compare(parent.key, node.key) > 0)
                parent = parent.parent;
            return parent;
        } else
            throw new MyException("Not Found Predecessor");
    }

    /*********************************************/

    public void deleteKey(T key) {
        root = deleteRec(root, key);
    }

    private Node<T> deleteRec(Node<T> root, T key) {
        if (root == null) return root;


        if (comparator.compare(key, root.key) < 0)
            root.left = deleteRec(root.left, key);
        else if (comparator.compare(key, root.key) > 0)
            root.right = deleteRec(root.right, key);

        else {
            if (root.left == null)
                return root.right;
            else if (root.right == null)
                return root.left;

            root.key = min(root.right);

            root.right = deleteRec(root.right, root.key);
        }

        return root;
    }

    /*********************************************/

    public String inOrder(Node<T> node) {
        if (node == null) return "";

        return inOrder(node.left) + node.key.toString() + " " + inOrder(node.right);
    }

    /*********************************************/

    public String preOrder(Node<T> node) {
        if (node == null) return "";

        return node.key.toString() + " " + preOrder(node.left) + preOrder(node.right);
    }

    /*********************************************/

    public String postOrder(Node<T> node) {
        if (node == null) return "";

        return postOrder(node.left) + postOrder(node.right) + node.key.toString() + " ";
    }

    /*********************************************/


    public int getHeight(Node<T> node) {
        if (node == null) return -1;
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        return node.height;
    }

    /*********************************************/

    public int getOverload(Node<T> node) {
        int delta = Math.abs(getHeight(node.left) - getHeight(node.right));
        node.overload = delta;
        return delta;
    }

    /*********************************************/

    public int nodeCounts(Node<T> node) {
        if (node == null) return 0;
        node.nodes = 1 + nodeCounts(node.left) + nodeCounts(node.right);
        return node.nodes;
    }


    /*********************************************/

    public int leavesCount(Node<T> node) {
        if (node == null) return 0;

        else if (node.left == null && node.right == null) {
            node.leaves = 1;
            return node.leaves;
        }

        node.leaves = leavesCount(node.left) + leavesCount(node.right);

        return node.leaves;
    }

    /*********************************************/

    public Node<T> getRoot() {
        return root;
    }
}
