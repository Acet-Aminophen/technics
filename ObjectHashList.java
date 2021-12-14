// 특정 리스트 속의 무작위 값을 가져와야 하지만, 입력값이 존재하며 그 입력값이 변하지 않는 이상 반환값이 같아야 할 경우.
// get random index of list from any input value, but return value must be always the same if input value doesn't changed.

// 무조건 리스트의 값은 입력값 리스트보다 커야함
// always the list length is greater than input list length.

import java.util.*;

public class ObjectHashList<T>
{
    List<T> valueList;
    Integer valueListSize;
    List<T> valueListCopied = null;

    // 일단은 중첩도 가능하게끔
    public ObjectHashList(List<T> valueList)
    {
        this.valueList = valueList;
        this.valueListSize = valueList.size();
    }

    public T getValue(Object seedObject)
    {
        return getValue(1, seedObject);
    }

    private T getValue(int cycle, Object seedObject)
    {
        Random rnd = new Random(seedObject.hashCode());

        int cycleIs = 0;
        while(true)
        {
            cycleIs += 1;
            T tValue = valueList.get(rnd.nextInt(valueList.size()));
            if (cycleIs == cycle)
            {
                return tValue;
            }
        }
    }

    // seedObjects List는 그 순서로 반환 객체가 보장된다.
    // 만일 valueList가 Color의 리스트라고 하고 기존 A, B, C에 대해서 빨, 주, 노를 반환했다 하자.
    // D가 추가되는 상황에서 기존 A, B, C에 영향을 주고싶지 않다면, List 속 순서는 A, B, C, D 가 되어야 한다.
    // 먼저 오는 객체에 한하여 그 값을 보장한다.
    public List<T> getUnduplicatedValues(List<? extends Object> seedObjects)
    {
        if (seedObjects.size() > valueListSize) throw new RuntimeException("Pigeonhole principle Error");

        List<T> returner = new ArrayList<>();
        List<T> valueListCopied = new ArrayList<>(valueList);
        for(Object ob : seedObjects)
        {
            T tValue = new ObjectHashList<T>(valueListCopied).getValue(1, ob);
            returner.add(tValue);
            valueListCopied.remove(tValue);
        }

        return returner;
    }

    public T getUnduplicatedValue(Object seedObject)
    {
        if (valueListCopied == null) valueListCopied = new ArrayList<>(valueList);
        if (valueListCopied.size() == 0) throw new RuntimeException("Pigeonhole principle Error");

        T tValue = new ObjectHashList<T>(valueListCopied).getValue(1, seedObject);
        valueListCopied.remove(tValue);

        return tValue;
    }
}

