import java.util.Iterator;

@SuppressWarnings("unchecked")
public class ArrayList<T> implements Iterable<T>
{
    public static final int DEFAULT_SIZE = 10;

    T[] array;
    int size;

    public ArrayList(int initialSize)
    {
        array = (T[]) (new Object[DEFAULT_SIZE]);
        size = 0;
    }

    public ArrayList()
    {
        this(DEFAULT_SIZE);
    }

    public void add(T newElement)
    {
        if (size == array.length)
            expandCapacity();

        if (!contains(newElement))
        {
            if (newElement instanceof Comparable)
            {
                Comparable comparableElement = (Comparable) newElement;
                int i;
                for (i = size;
                    i > 0 && comparableElement.compareTo(array[i - 1]) < 0; i--)
                {
                    array[i] = array[i - 1];
                }
                array[i] = newElement;
            }
            else
            {
                array[size] = newElement;
            }
            size++;
        }
    }

    public int size()
    {
        return size;
    }

    public boolean contains(T element)
    {
        for (int i = 0; i < size; i++)
            if (array[i].equals(element))
                return true;
        return false;
    }

    private void expandCapacity()
    {
        T[] newArray = (T[]) (new Object[array.length * 2]);

        for (int i = 0; i < size; i++)
            newArray[i] = array[i];

        array = newArray;
    }

    public Iterator<T> iterator()
    {
        return new ArrayListIterator();
    }

    public T getLast()
    {
        return array[size - 1];
    }

    public ArrayList<T> clone()
    {
        ArrayList<T> newList = new ArrayList<T>(size);
        for (T element : this)
            newList.add(element);

        return newList;
    }

    private class ArrayListIterator implements Iterator<T>
    {
        private int index;

        public ArrayListIterator()
        {
            super();

            index = 0;
        }

        public boolean hasNext()
        {
            return index < size;
        }

        public T next()
        {
            index++;
            return array[index - 1];
        }

        public void remove()
        {
            throw new UnsupportedOperationException();
        }

        public String toString()
        {
            String result = "";
            for (int i = 0; i < size; i++)
            {
                result += array[i];
                if (i < size - 1)
                    result += "\n";
            }
            return result;
        }
    }

    public String toString()
    {
        return iterator().toString();
    }
}
