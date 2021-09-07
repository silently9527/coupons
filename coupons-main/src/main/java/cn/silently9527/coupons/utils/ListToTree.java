package cn.silently9527.coupons.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 抽象的list转换成树的算法
 * @author starBlues
 * @version 1.0
 */
public abstract class ListToTree<T> {

    /**
     * 获取节点唯一标识方法
     * @param node 节点
     * @return 节点id
     */
    protected abstract String getKey(T node);

    /**
     * 获取节点父节点唯一标识方法
     * @param node 节点
     * @return 父节点id
     */
    protected abstract String getParentId(T node);

    /**
     * 得到子节点
     * @param node 节点
     * @return 子节点集合
     */
    protected abstract List<T> getChildrenList(T node);

    /**
     * 设置子树
     * @param parentNode 父节点
     * @param childNodes 子节点集合
     */
    protected abstract void setChildrenList(T parentNode, List<T> childNodes);

    public List<T> listToTree(List<T> oldList) {
        if(oldList == null || oldList.isEmpty()){
            return Collections.emptyList();
        }
        Map<String, T> newMap = Maps.newHashMap();
        List<T> newList = Lists.newArrayList();
        for (T tree : oldList){
            newMap.put(getKey(tree),tree);
        }
        for (T tree : oldList){
            T parent = newMap.get(getParentId(tree));
            if (parent != null){
                if (getChildrenList(parent) == null){
                    List<T> child = Lists.newArrayList();
                    child.add(tree);
                    setChildrenList(parent, child);
                }else {
                    List<T> child = getChildrenList(parent);
                    child.add(tree);
                    setChildrenList(parent, child);
                }
            }else {
                newList.add(tree);
            }
        }
        return newList;
    }

}
