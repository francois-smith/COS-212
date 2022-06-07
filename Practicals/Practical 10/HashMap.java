public class HashMap<T, U> {
    private Object[] keyArray;
    private Object[] valueArray;
    private Object[] keyCellar;
    private Object[] valueCellar;

    public HashMap(int ArraySize, int CellarSize){
    }

    public boolean put(T key, U value){
    }

    @SuppressWarnings("unchecked")
    public U get(T key){
    }

    @SuppressWarnings("unchecked")
    public HashMap<T,U> rehash(int ArraySize, int CellarSize){
        
    }

    public int arrayHash(T key){
    
    }

    public int cellarHash(T key){
    
    }

    public static int linearProbing(int i, int hashValue, int modVal){

    }

    public static int quadraticProbing(int i, int hashValue, int modVal){
        
    }

    public int count(){

    }

    public boolean isContained(T key, U value){
        
    }

    public Object[] getKeyArray(){
        
    }

    public Object[] getKeyCellar(){
        
    }

    public Object[] getValueArray(){
        
    }

    public Object[] getValueCellar(){
        
    }


    //Its not advised to change this *wink wink*
    private int convertTtoInt(T key){
        if(key == null)
            return -1;
        return key.hashCode();
    }
}
