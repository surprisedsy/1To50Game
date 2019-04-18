package com.example.a1to50game.GameMain;

import android.widget.Button;

public class ButtonsNumInfo {

    private String buttonTxt;
    private Button button;

    public ButtonsNumInfo() {};

    public ButtonsNumInfo(String btnStr)
    {
        buttonTxt = btnStr;
    }

    public String getButtonTxt()
    {
        return buttonTxt;
    }

    public void setButtonTxt(String buttonTxt)
    {
        this.buttonTxt = buttonTxt;
    }
}
