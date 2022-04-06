package com.example.yonseitalk.common.dto;

import lombok.Getter;

import java.util.List;

public class Response {

    private static final String OK_MESSAGE = "정상적으로 처리되었습니다.";

    @Getter
    public static class Empty {
        private final boolean success;
        private final String message;

        public Empty() {
            success = true;
            message = OK_MESSAGE;
        }
    }

    @Getter
    public static class Item<T> {
        private final boolean success;
        private final String message;
        private final T data;

        public Item(T data) {
            success = true;
            message = OK_MESSAGE;
            this.data = data;
        }
    }

    @Getter
    public static class ItemList<T> {
        private final boolean success;
        private final String message;
        private final List<T> data;
        private final int totalElements;

        public ItemList(List<T> data) {
            success = true;
            message = OK_MESSAGE;
            this.data = data;
            totalElements = data.size();
        }
    }

    @Getter
    public static class Error {
        private final boolean success;
        private final String message;

        public Error(String message) {
            success = false;
            this.message = message;
        }
    }

}
