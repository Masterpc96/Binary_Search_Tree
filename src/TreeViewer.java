import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.Random;


/**
 * Created by Michał on 06.05.2017.
 */
public class TreeViewer extends JFrame implements ActionListener {

    static private int CANVAS_WIDTH = 2500;
    static private int BOUND = 100;
    private final Dimension DIM = getToolkit().getScreenSize();

    private int rootY = 10;
    private int NODE_SIZE = 25;
    private int ROW_HEIGHT = 50;

    private Random rand;
    private Font font = new Font("Comic Sans MS", Font.PLAIN, 18);

    private Tree<Integer> tree = new Tree<>();
    private NumberFormatter format;
    private NumberFormat form;
    private JFormattedTextField text;
    private mxGraph graph = new mxGraph();
    private Object parent = graph.getDefaultParent();
    private JButton add;
    private JButton remove;
    private JButton inOrder;
    private JButton preOrder;
    private JButton postOrder;
    private JButton leavesCounts;
    private JButton nodeCounts;
    private JButton height;
    private JButton max;
    private JButton min;
    private JButton random;
    private JButton clear;
    private JButton predecessor;
    private JButton successor;
    private JLabel key;

    private JScrollPane scrollPane;

    public TreeViewer() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(DIM.width, DIM.height - 80);
        setVisible(true);
        setResizable(false);

        setLayout(new FlowLayout(FlowLayout.CENTER));

        mxGraphComponent graphComponent = new mxGraphComponent(graph);

        graphComponent.setPreferredSize(new Dimension(1900, DIM.height - 280));
        scrollPane = new JScrollPane(graphComponent);
        add(scrollPane);

        form = NumberFormat.getInstance();
        form.setGroupingUsed(false);
        format = new NumberFormatter(form);
        format.setMinimum(0);
        format.setMaximum(Integer.MAX_VALUE);

        text = new JFormattedTextField(format);
        text.setPreferredSize(new Dimension(70, 35));
        text.setFont(font);

        add = new JButton("Add");
        random = new JButton("Rand");
        remove = new JButton("Delete");
        inOrder = new JButton("inOrder");
        preOrder = new JButton("preOrder");
        postOrder = new JButton("postOrder");
        leavesCounts = new JButton("Leaves count");
        nodeCounts = new JButton("Node counts");
        height = new JButton("Height");
        max = new JButton("Max");
        min = new JButton("Min");
        predecessor = new JButton("Predecessor");
        successor = new JButton("Successor");
        clear = new JButton("Clear");
        key = new JLabel("Key");


        add.setFont(font);
        random.setFont(font);
        remove.setFont(font);
        inOrder.setFont(font);
        preOrder.setFont(font);
        postOrder.setFont(font);
        leavesCounts.setFont(font);
        nodeCounts.setFont(font);
        height.setFont(font);
        max.setFont(font);
        min.setFont(font);
        predecessor.setFont(font);
        successor.setFont(font);
        clear.setFont(font);
        key.setFont(font);


        add.addActionListener(this);
        random.addActionListener(this);
        remove.addActionListener(this);
        inOrder.addActionListener(this);
        preOrder.addActionListener(this);
        postOrder.addActionListener(this);
        leavesCounts.addActionListener(this);
        nodeCounts.addActionListener(this);
        height.addActionListener(this);
        max.addActionListener(this);
        min.addActionListener(this);
        predecessor.addActionListener(this);
        successor.addActionListener(this);
        clear.addActionListener(this);

        add(key);
        add(text);
        add(add);
        add(random);
        add(remove);
        add(inOrder);
        add(preOrder);
        add(postOrder);
        add(leavesCounts);
        add(nodeCounts);
        add(height);
        add(max);
        add(min);
        add(predecessor);
        add(successor);
        add(clear);

        rand = new Random();
    }

    /*********************************************/

    public Object drawTree(Node<Integer> root, int depth, int index) {
        if (root == null) {
            return null;
        }

        int myX = (int) ((CANVAS_WIDTH * (index)) / (Math.pow(2, depth - 1) + 1));

        Object rootVertex = graph.insertVertex(parent, null, root.key,
                myX, depth * ROW_HEIGHT + rootY, NODE_SIZE, NODE_SIZE);


        Object rightChildVertex = drawTree(root.right, depth + 1,
                index * 2);

        if (rightChildVertex != null) {
            graph.insertEdge(parent, null, "", rootVertex, rightChildVertex,
                    "startArrow=none;endArrow=none;strokeWidth=1;strokeColor=green");
        }

        Object leftChildVertex = drawTree(root.left, depth + 1,
                index * 2 - 1);


        if (leftChildVertex != null) { // edge
            graph.insertEdge(parent, null, "", rootVertex, leftChildVertex,
                    "startArrow=none;endArrow=none;strokeWidth=1;strokeColor=green");
        }

        return rootVertex;

    }

    /*********************************************/

    public void update(Node<Integer> root) {

        graph.getModel().beginUpdate();

        try {

            Object[] cells = graph.getChildCells(parent, true, false);
            graph.removeCells(cells, true);
            drawTree(root, 1, 1);

        } finally {
            graph.getModel().endUpdate();
        }
    }

    /*********************************************/

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == random) {
            int a = rand.nextInt(BOUND);
            while (!tree.addKey(a)) {
                a = rand.nextInt(BOUND);
            }
            this.update(tree.getRoot());
        } else if (e.getSource() == clear) {
            tree = new Tree<>();
            this.update(tree.getRoot());

        } else if (!text.getText().toString().equals("")) {

            if (e.getSource() == add) {
                if (!tree.addKey(Integer.valueOf(text.getText().toString())))
                    JOptionPane.showMessageDialog(this, "This key already exists", "Error", JOptionPane.ERROR_MESSAGE);
                else this.update(tree.getRoot());
            } else {

                if (tree.search(Integer.valueOf(text.getText().toString())) == null) {
                    JOptionPane.showMessageDialog(this, "This key doesn't exist", "Error", JOptionPane.ERROR_MESSAGE);
                } else {

                    if (e.getSource() == remove) {
                        tree.deleteKey(Integer.valueOf(text.getText().toString()));
                        this.update(tree.getRoot());
                    } else if (e.getSource() == inOrder) {
                        String result;
                        result = tree.inOrder(tree.search(Integer.valueOf(text.getText().toString())));
                        JOptionPane.showMessageDialog(this, result, "inOrder", JOptionPane.INFORMATION_MESSAGE);
                    } else if (e.getSource() == preOrder) {
                        String result;
                        result = tree.preOrder(tree.search(Integer.valueOf(text.getText().toString())));
                        JOptionPane.showMessageDialog(this, result, "preOrder", JOptionPane.INFORMATION_MESSAGE);
                    } else if (e.getSource() == postOrder) {
                        String result;
                        result = tree.postOrder(tree.search(Integer.valueOf(text.getText().toString())));
                        JOptionPane.showMessageDialog(this, result, "postOrder", JOptionPane.INFORMATION_MESSAGE);
                    } else if (e.getSource() == leavesCounts) {
                        int count = tree.leavesCount(tree.search(Integer.valueOf(text.getText().toString())));
                        JOptionPane.showMessageDialog(this, count, "Leaves count", JOptionPane.INFORMATION_MESSAGE);
                    } else if (e.getSource() == nodeCounts) {
                        int count = tree.nodeCounts(tree.search(Integer.valueOf(text.getText().toString())));
                        JOptionPane.showMessageDialog(this, count, "Nodes count", JOptionPane.INFORMATION_MESSAGE);
                    } else if (e.getSource() == height) {
                        int tHeight = tree.getHeight(tree.search(Integer.valueOf(text.getText().toString())));
                        JOptionPane.showMessageDialog(this, tHeight, "Tree height", JOptionPane.INFORMATION_MESSAGE);
                    } else if (e.getSource() == max) {
                        int tMax = tree.max(tree.search(Integer.valueOf(text.getText().toString())));
                        JOptionPane.showMessageDialog(this, tMax, "Maximum value", JOptionPane.INFORMATION_MESSAGE);
                    } else if (e.getSource() == min) {
                        int tMin = tree.min(tree.search(Integer.valueOf(text.getText().toString())));
                        JOptionPane.showMessageDialog(this, tMin, "Minimum value", JOptionPane.INFORMATION_MESSAGE);
                    } else if (e.getSource() == predecessor) {
                        int pred = 0;
                        try {
                            pred = tree.predecessor(Integer.valueOf(text.getText().toString())).key;
                        } catch (MyException e1) {
                            e1.printStackTrace();
                        }
                        JOptionPane.showMessageDialog(this, pred, "Predecessor", JOptionPane.INFORMATION_MESSAGE);
                    } else if (e.getSource() == successor) {
                        int succ = 0;
                        try {
                            succ = tree.successor(Integer.valueOf(text.getText().toString())).key;
                        } catch (MyException e1) {
                            e1.printStackTrace();
                        }
                        JOptionPane.showMessageDialog(this, succ, "Successor", JOptionPane.INFORMATION_MESSAGE);
                    }
                }

            }
        }
    }
}
