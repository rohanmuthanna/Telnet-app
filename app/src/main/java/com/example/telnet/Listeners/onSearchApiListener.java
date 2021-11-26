package com.example.telnet.Listeners;

import com.example.telnet.Models.SearchApiResponse;

public interface onSearchApiListener {
    void onResponse(SearchApiResponse response);
    void onError(String message);
}
