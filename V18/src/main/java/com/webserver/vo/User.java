package com.webserver.vo;

/**
 * vo:value object值对象。这种类的实例就是纯粹表示一组数据的
 */
public class User {
    private String username;
    private String password;
    private String nickname;
    private String email;
    private int age;

    public User(String username, String password, String nickname, String email, int age) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                '}';
    }
}



