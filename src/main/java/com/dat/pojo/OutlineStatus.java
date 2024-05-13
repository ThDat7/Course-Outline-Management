package com.dat.pojo;

public enum OutlineStatus {
    DOING {
        @Override
        public String toString() {
            return "Đang thực hiện";
        }
    },
    COMPLETED {
        @Override
        public String toString() {
            return "Đã hoàn thành";
        }
    }
}
