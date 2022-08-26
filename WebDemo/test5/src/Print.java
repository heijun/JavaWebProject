import com.sun.xml.internal.ws.util.xml.NodeListIterator;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Print {
    NodeListIterator nodeListIterator;


    public Print( NodeListIterator nodeListIterator) {
        this.nodeListIterator=nodeListIterator;
    }
    public void print(){
        while(nodeListIterator.hasNext()){
            Node childNodes =  (Node) nodeListIterator.next();
            for (int i = 0; i < childNodes.getChildNodes().getLength(); i++) {
                Node child = childNodes.getChildNodes().item(i);
                if(child.getNodeType() == Node.ELEMENT_NODE)  //过滤换行符之类的内容，因为它们都被认为是一个文本节点
                    System.out.println(child.getNodeName() + "：" +child.getFirstChild().getNodeValue());
                // 输出节点名称，也就是标签名称，以及标签内部的文本（内部的内容都是子节点，所以要获取内部的节点）
            }
        }

    }

}
