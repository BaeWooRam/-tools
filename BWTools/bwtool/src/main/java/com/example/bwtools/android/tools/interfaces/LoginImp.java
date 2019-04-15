package com.example.bwtools.android.tools.interfaces;

public interface LoginImp{
    void setupServerID();
    void LogOut();
    void RequestToken();
    void RefreshToken();
    void Disconnect();
    void LogOutAfterUI();
    void DisconnectAfterUI();
    void RequestTokenAfterUI(String account);
}
