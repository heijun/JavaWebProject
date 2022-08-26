import com.sun.xml.internal.ws.util.xml.NodeListIterator;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import com.BinaryTree.BinaryTreeNode;
import com.BinaryTree.BinaryTreeMethods;


public class MainTest5 {
    public static void main(String[] args) {
        // 创建DocumentBuilderFactory对象
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
// 创建DocumentBuilder对象
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document d = builder.parse("example.xml");
            // 每一个标签都作为一个节点
            NodeList nodeList = d.getElementsByTagName("inner");  // 可能有很多个名字为inner的标签
            NodeListIterator nodeListIterator=new NodeListIterator(nodeList);
             // 一个节点下可能会有很多个节点，比如根节点下就囊括了所有的节点
            //节点可以是一个带有内容的标签（它内部就还有子节点），也可以是一段文本内容
            Print print=new Print(nodeListIterator);
            print.print();



        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
