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
            return "Đã biên soạn xong";
        }
    },
    PUBLISHED {
        @Override
        public String toString() {
            return "Đã công khai";
        }
    };

    public static OutlineStatus fromString(String status) {
        switch (status) {
            case "Đang thực hiện":
                return DOING;
            case "Đã biên soạn xong":
                return COMPLETED;
            case "Đã công khai":
                return PUBLISHED;
            default:
                return null;
        }
    }
}
