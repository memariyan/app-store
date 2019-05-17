package ir.ac.iust.appstore.model.api;

/*simple structure for keep left right pairs*/
public class Pair<T,P>
{
    //-------------------------------------------------------variables and constructors------------------------------------------------------

    private T left;
    private P right;

    public Pair(T left, P right)
    {
        this.left = left;
        this.right = right;
    }

    //------------------------------------------------------getter and setter methods---------------------------------------------------------

    public T getLeft() {
        return left;
    }

    public void setLeft(T left) {
        this.left = left;
    }

    public P getRight() {
        return right;
    }

    public void setRight(P right) {
        this.right = right;
    }
}
