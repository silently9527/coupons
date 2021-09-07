package cn.silently9527.coupons.repository.databases.model;

import cn.silently9527.coupons.repository.databases.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author starBlues
 * @version 1.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserHasRole extends User {

    private String roleNames;


}
