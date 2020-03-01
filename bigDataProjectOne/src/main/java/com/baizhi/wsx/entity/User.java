package com.baizhi.wsx.entity;


import lombok.*;

@Data                //提供Set  Get  ToString
@NoArgsConstructor   //提供无参构造
@AllArgsConstructor  //提供有参构造
@EqualsAndHashCode   //提供Equals和hashcode方法
@ToString            //提供toString方法
public class User {
    private Integer id;
    private String username;
    private String password;
    private String email;
}
