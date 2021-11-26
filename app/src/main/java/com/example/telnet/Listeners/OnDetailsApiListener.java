package com.example.telnet.Listeners;

import com.example.telnet.Models.DetailApiResponse;

public interface OnDetailsApiListener {
    void onResponse(DetailApiResponse response);
    void onError(String message);
}
