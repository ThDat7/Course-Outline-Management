package com.dat.pojo;

public enum UserRole {
    ADMIN {
        @Override
        public String toString() {
            return "Quản trị viên";
        }
    },
    TEACHER {
        @Override
        public String toString() {
            return "Giáo viên";
        }
    },
    STUDENT {
        @Override
        public String toString() {
            return "Học sinh";
        }
    }
}
