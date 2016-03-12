import java.util.Iterator;

@SuppressWarnings("unchecked")
public class GridNode<T> implements Iterable<GridNode<T>>
{
    private T element;
    private ArrayList<GridNode<T>> links;

    public GridNode(T element)
    {
        this.element = element;
        links = new ArrayList<GridNode<T>>();
    }

    public void addLink(GridNode<T> otherNode)
    {
        links.add(otherNode);
        otherNode.links.add(this);
    }

    public boolean hasLink(GridNode<T> otherNode)
    {
        return links.contains(otherNode);
    }

    public T getElement()
    {
        return element;
    }

    public Iterator<GridNode<T>> iterator()
    {
        return links.iterator();
    }

    public String toString()
    {
        return element.toString();
    }
}
