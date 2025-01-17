package com.ohgiraffers.jenkins_test_app.auth.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UsersDTO {

    private Integer id;                         // 사용자 고유 ID
    private String nickname;                    // 사용자 닉네임
    private String userId;                      // 사용자 고유 아이디(이메일 형식)
    private String password;                    // 사용자 비밀번호 (암호화 되기전 user에게 받은 값)
    private String refreshToken;
    // private UserRole userRole;                    // 사용자 역할 (예: 'user', 'admin')
    private String profilePic;                  // 사용자 프로필 사진 URL
    private String localArea;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd")
    private LocalDate localAreaAuthDate;
    private int ownBadge;
    private int status;                         // 사용자 계정 상태 (0: 비활성, 1: 활성)

    public UsersDTO() {
    }

    public UsersDTO(Integer id, String nickname, String userId, String password, String refreshToken, String profilePic, String localArea, LocalDate localAreaAuthDate, int ownBadge, int status) {
        this.id = id;
        this.nickname = nickname;
        this.userId = userId;
        this.password = password;
        this.refreshToken = refreshToken;
        this.profilePic = profilePic;
        this.localArea = localArea;
        this.localAreaAuthDate = localAreaAuthDate;
        this.ownBadge = ownBadge;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getLocalArea() {
        return localArea;
    }

    public void setLocalArea(String localArea) {
        this.localArea = localArea;
    }

    public LocalDate getLocalAreaAuthDate() {
        return localAreaAuthDate;
    }

    public void setLocalAreaAuthDate(LocalDate localAreaAuthDate) {
        this.localAreaAuthDate = localAreaAuthDate;
    }

    public int getOwnBadge() {
        return ownBadge;
    }

    public void setOwnBadge(int ownBadge) {
        this.ownBadge = ownBadge;
    }

    @Override
    public String toString() {
        return "UsersDTO{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", profilePic='" + profilePic + '\'' +
                ", localArea='" + localArea + '\'' +
                ", localAreaAuthDate=" + localAreaAuthDate +
                ", ownBadge=" + ownBadge +
                ", status=" + status +
                '}';
    }
}
