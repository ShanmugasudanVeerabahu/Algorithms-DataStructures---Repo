package assignment1;

public class LinkedList {

    public Node insert(Node start, int x){
        if(start == null || start.data > x){
           start = new Node(x,start);
            return start;
        }
        Node p = start;
        while(p.next != null){
            if(p.next.data > x)
                break;
            p = p.next;
        }
        p.next = new Node(x,p.next);
        return start;
    }
    
    public Node delete(Node start, int x){
        if(start == null || start.data > x){
            return start;
        }
            
        if(start.data == x){
            return start.next;
        }
        
        for(Node p = start; p.next != null; p = p.next){
            if(p.next.data > x)
            break;
            if(p.next.data == x){
                p.next = p.next.next;
                break;
            }
        }
        return start;
    }
    public class Node{
        int startAddress;
        int data;
        Node next;
        
        Node(int data){
            this.data = data;
            this.next=null;
        }
        
        Node(int data, Node next){
            this.data = data;
            
            this.next = next;
        }
    }
}
    
