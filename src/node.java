public class node {
    int prev;// для Дейкстры
    int sum;// для Дейкстры (для Прима и Крускала тут хранится вес ребра)

    int next;// для Крускала

    public node(int prev, int sum) {
        this.prev = prev;
        this.sum = sum;
    }
    public node(int prev, int next, int sum){
        this.prev=prev;
        this.next=next;
        this.sum=sum;
    }
}