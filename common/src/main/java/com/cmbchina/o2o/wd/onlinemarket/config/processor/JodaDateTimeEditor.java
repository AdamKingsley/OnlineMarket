package com.cmbchina.o2o.wd.onlinemarket.config.processor;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import java.beans.PropertyEditorSupport;

public class JodaDateTimeEditor extends PropertyEditorSupport {

    private String pattern;

    public JodaDateTimeEditor(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public String getAsText() {
        return super.getAsText();
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        try {
            DateTime dateValue = DateTimeFormat.forPattern(pattern).parseDateTime(text);
            setValue(dateValue);
        } catch (Exception e) {
            System.out.println("日期格式不对");
        }
    }
}
