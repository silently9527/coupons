package com.huaan9527.mall.webapi.domain;


import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.domain.Persistable;
import org.springframework.data.mybatis.annotation.CreatedBy;
import org.springframework.data.mybatis.annotation.CreatedDate;
import org.springframework.data.mybatis.annotation.LastModifiedBy;
import org.springframework.data.mybatis.annotation.LastModifiedDate;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * 抽象实体基类，提供统一的ID，和相关的基本功能方法
 */
@Getter
@Setter
@MappedSuperclass
public abstract class AbstractDomain<T extends Serializable> implements Persistable<T> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected T id;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedDate;

    @CreatedBy
    protected Long createdBy;

    @LastModifiedBy
    protected Long lastUpdatedBy;

    // @Version
    // protected Integer version;

    @Override
    public T getId() {
        return id;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.springframework.data.domain.Persistable#isNew()
     * 
     */
    public boolean isNew() {

        return null == getId();
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

        if (null == obj) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (!getClass().equals(obj.getClass())) {
            return false;
        }

        AbstractDomain<?> that = (AbstractDomain<?>) obj;

        return null != this.getId() && this.getId().equals(that.getId());
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {

        int hashCode = 17;

        hashCode += null == getId() ? 0 : getId().hashCode() * 31;

        return hashCode;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
